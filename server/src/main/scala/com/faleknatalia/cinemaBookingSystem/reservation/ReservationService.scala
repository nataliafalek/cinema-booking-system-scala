package com.faleknatalia.cinemaBookingSystem.reservation

import java.time.format.DateTimeFormatter

import com.faleknatalia.cinemaBookingSystem.dbutils.Tables
import com.faleknatalia.cinemaBookingSystem.mail.{EmailSender, SeatAndPriceDetails, TicketData, TicketGenerator}
import slick.jdbc
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{ExecutionContext, Future}

class ReservationService(db: jdbc.JdbcBackend.Database)(implicit ec: ExecutionContext) {
  private lazy val reservationTable = Tables.reservationTable
  private lazy val scheduledMovieWithSeatTable = Tables.scheduledMovieWithSeatsTable
  private lazy val scheduledMoviesTable = Tables.scheduledMoviesTable
  private lazy val cinemaHallsTable = Tables.cinemaHallsTable
  private lazy val seatsTable = Tables.seatsTable
  private lazy val ticketPricesTable = Tables.ticketPriceTable
  private lazy val moviesTable = Tables.moviesTable
  private lazy val personalDataTable = Tables.personalDataTable

  def makeReservation(reservationDto: ReservationDto): Future[Unit] = {
    val reserve = Future.sequence {
      reservationDto.chosenSeatsAndPrices.map { seatAndPrice =>
        reserveScheduledMovieWithSeat(reservationDto.scheduledMovieId, seatAndPrice.seatId)
      }
    }.map(_ => ())

    reserve.flatMap { _ =>
      val reservationsPerSeat = reservationDto.chosenSeatsAndPrices.map { seatAndPrice =>
        Reservation(reservationDto.personalDataId, reservationDto.scheduledMovieId, seatAndPrice.seatId, seatAndPrice.priceId)
      }
      val insertQuery = reservationTable ++= reservationsPerSeat
      db.run(insertQuery).flatMap(_ => sendEmailWithTicket(reservationDto))
    }
  }

  def reserveScheduledMovieWithSeat(scheduledMovieId: Long, seatId: Long): Future[Unit] = {
    val updateQuery = scheduledMovieWithSeatTable.filter { scheduledMovieWithSeat =>
      scheduledMovieWithSeat.seatId === seatId && scheduledMovieWithSeat.scheduledMovieId === scheduledMovieId
    }.map { scheduledMovieWithSeat => scheduledMovieWithSeat.isFree }
    db.run(updateQuery.update(false)).map(_ => ())
  }

  def sendEmailWithTicket(reservationDto: ReservationDto): Future[Unit] = {
    val movieDetailsQuery = reservationTable.filter(reservation =>
      reservation.personalDataId === reservationDto.personalDataId && reservation.scheduledMovieId === reservationDto.scheduledMovieId)
      .join(seatsTable).on(_.seatId === _.id).join(ticketPricesTable).on(_._1.ticketPriceId === _.id)
      .join(scheduledMoviesTable).on(_._1._1.scheduledMovieId === _.id)
      .join(moviesTable).on(_._2.movieId === _.id)
      .join(cinemaHallsTable).on(_._1._2.cinemaHallId === _.id)
      .join(personalDataTable).on(_._1._1._1._1._1.personalDataId === _.id)

    val maybeTicketData = db.run(movieDetailsQuery.result).map { allData =>
      allData.toList match {
        case Nil => None
        case allData@first :: _ => first match {
          case ((((_, scheduledMovie), movie), cinemaHall), personalData) =>
            val seatAndPriceDetails = allData.map { case ((((((_, seat), ticketPrice), _), _), _), _) =>
              SeatAndPriceDetails(seat, ticketPrice)
            }
            Some(TicketData(movie.title,
              scheduledMovie.dateOfProjection.format(DateTimeFormatter.ISO_LOCAL_DATE),
              scheduledMovie.dateOfProjection.format(DateTimeFormatter.ISO_LOCAL_TIME),
              cinemaHall.name,
              seatAndPriceDetails), personalData)
        }
      }
    }

    maybeTicketData.flatMap {
      case None => Future.successful(())
      case Some(data) =>
        EmailSender.sendEmailWithTicket(
          data._2.email,
          "PaprykCinema ticket",
          "",
          TicketGenerator.generateTicket(data._1)
        )
    }
  }
}