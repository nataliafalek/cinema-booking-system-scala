package com.faleknatalia.cinemaBookingSystem

import java.time.LocalDateTime

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import com.faleknatalia.cinemaBookingSystem.movie.{AddMovie, Movie, MovieDao, ScheduledMovieDao}
import slick.jdbc
import slick.jdbc.JdbcBackend
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.{ExecutionContext, Future}
import scala.io.StdIn

object Main extends JsonSupport {

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem(Behaviors.empty, "my-system")
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.executionContext
    val db = Database.forConfig("db")
    val movies = TableQuery[MovieDao]

    val route = {
      cors() {
        path("hello") {
          get {
            complete(movie.ScheduledMovie(1L, LocalDateTime.now(), 2L))
          }
        } ~ path("movie" / "add") {
          post {
            entity(as[AddMovie]) { movieForm =>
              addNewMovie(db, movieForm)
            }
          }
        } ~ path("movie" / "list") {
          get {
            complete(findAllMovies(db, movies))
          }
        }
      }
    }

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

  private def addNewMovie(db: jdbc.JdbcBackend.Database, movieForm: AddMovie) = {
    //TODO add aoutoincrementing primary key and used slick's `into`
    val saveNewMovie =
      sql"""
                     insert into movie
                     values (1, ${movieForm.title}, ${movieForm.description}, ${movieForm.durationInSeconds}, ${movieForm.imageUrl})
                """.as[(Long, String, String, Long, String)]
    db.run(saveNewMovie)
    complete(movieForm)
  }

  private def findAllMovies(db: JdbcBackend.Database, movies: TableQuery[MovieDao])
                           (implicit ec: ExecutionContext): Future[Seq[Movie]] = {
    db.run(movies.result).map(_.map { case (id, title, description, durationInSeconds, imageUrl) =>
      Movie(id, title, description, durationInSeconds, imageUrl)
    })
  }
}
