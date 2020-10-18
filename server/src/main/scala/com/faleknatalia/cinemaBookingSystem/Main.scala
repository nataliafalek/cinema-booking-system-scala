package com.faleknatalia.cinemaBookingSystem

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import com.faleknatalia.cinemaBookingSystem.checkout.{CheckoutService, PersonalDataDto}
import com.faleknatalia.cinemaBookingSystem.cinemahall.CinemaHallService
import com.faleknatalia.cinemaBookingSystem.dbutils.{DatabaseUtils, Tables}
import com.faleknatalia.cinemaBookingSystem.movie.{MovieService, _}
import com.faleknatalia.cinemaBookingSystem.reservation.{ReservationDto, ReservationService}
import com.typesafe.scalalogging.LazyLogging
import slick.jdbc.JdbcBackend.Database

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.io.StdIn

object Main extends JsonSupport with LazyLogging {

  //TODO ERROR HANDLING, TRY, EITHER, SCALA TEST, USE HIGHER ORDER FUNCTION, TYPE CLASSES ITD., LOGGING

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem(Behaviors.empty, "my-system")
    implicit val executionContext = system.executionContext
    val db = Database.forConfig("db")
    val movieService = new MovieService(db)(executionContext)
    val cinemaHallService = new CinemaHallService(db)(executionContext)
    val checkoutService = new CheckoutService(db)(executionContext)
    val reservationService = new ReservationService(db)(executionContext)
    val dbUtils = new DatabaseUtils(db)(executionContext)

    val bootstrapDB = for {
      _ <- dbUtils.schemaCreate(Tables.allTablesSchema())
      _ <- cinemaHallService.saveCinemaHalls()
      _ <- cinemaHallService.saveSeats()
      _ <- checkoutService.saveTicketData()
    } yield ()
    Await.result(bootstrapDB, 30.seconds)

    val route = cors() {
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
          entity(as[AddScheduledMovieDto]) { scheduledMovie =>
            complete {
              movieService.addNewScheduledMovie(scheduledMovie).map { _ =>
                StatusCodes.OK
              }
            }
          }
        }
      } ~ path("schedule" / "list") {
        get {
          complete(movieService.findAllMoviesByDayOfTheWeek())
        }
      } ~ path("cinemahall" / "list") {
        get {
          complete(cinemaHallService.findAllCinemaHalls())
        }
      } ~ path("cinemahall" / "seats" / "list") {
        get {
          complete(cinemaHallService.findAllCinemaHallsWithSeats())
        }
      } ~ pathPrefix("cinemahall" / "seats" / "list" / LongNumber) { scheduledMovieId =>
        get {
          complete(cinemaHallService.findCinemaHallWithSeatsByScheduledMovieId(scheduledMovieId))
        }
      } ~ path("ticket" / "prices" / "list") {
        get {
          complete(checkoutService.listTicketPrices())
        }
      } ~ path("personaldata" / "add") {
        post {
          entity(as[PersonalDataDto]) { personalData =>
            complete {
              checkoutService.savePersonalData(personalData).map { id =>
                StatusCodes.OK -> id.toString
              }
            }
          }
        }
      } ~ pathPrefix("personaldata" / "get" / LongNumber) { personalDataId =>
        get {
          complete(checkoutService.getPersonalDataById(personalDataId))
        }
      } ~ path("reservation" / "make") {
        post {
          entity(as[ReservationDto]) { reservationDto =>
            complete {
              reservationService.makeReservation(reservationDto).map { _ =>
                StatusCodes.OK
              }
            }
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
