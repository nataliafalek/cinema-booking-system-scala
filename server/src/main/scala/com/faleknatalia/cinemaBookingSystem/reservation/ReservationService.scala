package com.faleknatalia.cinemaBookingSystem.reservation

import com.faleknatalia.cinemaBookingSystem.dbutils.Tables
import slick.jdbc
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{ExecutionContext, Future}

class ReservationService(db: jdbc.JdbcBackend.Database)(implicit ec: ExecutionContext) {
  private lazy val reservationTable = Tables.reservationTable
  private lazy val scheduledMovieWithSeatTable = Tables.scheduledMovieWithSeatsTable

  def makeReservation(reservationDto: ReservationDto): Future[Unit] = {
    reservationDto.chosenSeatsAndPrices.foreach { seatAndPrice =>
      reserveScheduledMovieWithSeat(reservationDto.scheduledMovieId, seatAndPrice.seatId)
    }

    val reservationsPerSeat = reservationDto.chosenSeatsAndPrices.map { seatAndPrice =>
      Reservation(reservationDto.personalDataId, reservationDto.scheduledMovieId, seatAndPrice.seatId, seatAndPrice.priceId)
    }
    val insertQuery = reservationTable ++= reservationsPerSeat

    db.run(insertQuery).map(_ => ())
  }

  def reserveScheduledMovieWithSeat(scheduledMovieId: Long, seatId: Long): Future[Unit] = {
    val updateQuery = scheduledMovieWithSeatTable.filter { scheduledMovieWithSeat =>
      scheduledMovieWithSeat.seatId === seatId && scheduledMovieWithSeat.scheduledMovieId === scheduledMovieId
    }.map { scheduledMovieWithSeat => scheduledMovieWithSeat.isFree }
    db.run(updateQuery.update(false)).map(_ => ())
  }
}