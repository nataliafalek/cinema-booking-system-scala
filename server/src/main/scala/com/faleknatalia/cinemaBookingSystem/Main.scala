package com.faleknatalia.cinemaBookingSystem

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import com.faleknatalia.cinemaBookingSystem.cinemahall.CinemaHallService
import com.faleknatalia.cinemaBookingSystem.movie.{MovieService, _}
import slick.jdbc.JdbcBackend.Database

import scala.io.StdIn

object Main extends JsonSupport {

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem(Behaviors.empty, "my-system")
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.executionContext
    val db = Database.forConfig("db")
    val movieService = new MovieService(db)(executionContext)
    val cinemaHallService = new CinemaHallService(db)(executionContext)

    movieService.movieSchemaCreate()
    cinemaHallService.cinemaHallSchemaCreate()
    cinemaHallService.saveCinemaHalls()
    cinemaHallService.saveSeats()

    val route = {
      cors() {
        path("movie" / "add") {
          post {
            entity(as[AddMovie]) { movieForm =>
              complete {
                movieService.addNewMovie(movieForm).map { _ =>
                  StatusCodes.OK
                }
              }
            }
          }
        } ~ path("movie" / "list") {
          get {
            complete(movieService.findAllMovies())
          }
        } ~ path("schedule" / "movie" / "list") {
          get {
            complete(movieService.findAllScheduledMovies())
          }
        } ~ path("schedule" / "movie" / "add") {
          post {
            entity(as[ScheduledMovie]) { scheduledMovie =>
              complete {
                movieService.addNewScheduledMovie(scheduledMovie).map { _ =>
                  StatusCodes.OK
                }
              }
            }
          }
        } ~ path("cinemahall" / "list") {
          get {
            complete(cinemaHallService.findAllCinemaHalls())
          }
        } ~ path("cinemahall" / "seats" / "list") {
          get {
            complete(cinemaHallService.findAllCinemaHallsWithSeats())
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
}
