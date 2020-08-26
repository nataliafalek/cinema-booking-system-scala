package com.faleknatalia.cinemaBookingSystem

import java.time.LocalDateTime

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import com.faleknatalia.cinemaBookingSystem.movie.{AddMovie, MovieDao, ScheduledMovieDao}
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import scala.io.StdIn

object Main extends JsonSupport {

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem(Behaviors.empty, "my-system")
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.executionContext
    val db = Database.forConfig("db")
    val movies = TableQuery[MovieDao]
    val scheduledMovie = TableQuery[ScheduledMovieDao]

    val route = {
      path("hello") {
        get {
          complete(movie.ScheduledMovie(1L, LocalDateTime.now(), 2L))
        }
      } ~ path("movie" / "add") {
        cors() {
          post {
            entity(as[AddMovie]) { movieForm =>
              //TODO add aoutoincrementing primary key and used slick's `into`
              val saveNewMovie =
                sql"""
                     insert into movie
                     values (1, ${movieForm.title}, ${movieForm.description}, ${movieForm.durationInSeconds}, ${movieForm.imageUrl})
                """.as[(Long, String, String, Long, String)]
              db.run(saveNewMovie)
              complete(movieForm)
            }
          }
        }
      }
    }

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)

    db.run(movies.result).map(_.foreach { case (id, title, description, durationInSeconds, imageUrl) =>
      println(s"${id} -- ${title} -- ${description}")
    })
    db.run(scheduledMovie.result).map(_.foreach { case (movieId, durationInSeconds, cinemaHallId) =>
      println(s"${movieId} -- ${durationInSeconds} -- ${cinemaHallId}")
    })
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}
