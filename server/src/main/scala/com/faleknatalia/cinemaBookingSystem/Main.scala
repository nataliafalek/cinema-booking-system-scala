package com.faleknatalia.cinemaBookingSystem

import java.time.LocalDateTime

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import com.faleknatalia.cinemaBookingSystem.model.ScheduledMovie
import com.faleknatalia.cinemaBookingSystem.repository.{MovieDao, ScheduledMovieDao}
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import scala.io.StdIn

object Main extends JsonSupport {

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem(Behaviors.empty, "my-system")
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.executionContext

    val route =
      path("hello") {
        get {
          complete(ScheduledMovie(1L, LocalDateTime.now(), 2L))
        }
      }

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)

    //TODO to refactor
    val db = Database.forConfig("db")
    val movies = TableQuery[MovieDao]
    val scheduledMovie = TableQuery[ScheduledMovieDao]
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
