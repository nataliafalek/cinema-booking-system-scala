package com.faleknatalia.cinemaBookingSystem.cinemahall

import com.faleknatalia.cinemaBookingSystem.dbutils.{DatabaseUtils, Tables}
import slick.jdbc
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{ExecutionContext, Future}

class CinemaHallService(db: jdbc.JdbcBackend.Database)(implicit ec: ExecutionContext) {
  private lazy val cinemaHallsTable = Tables.cinemaHallsTable
  private lazy val seatsTable = Tables.seatsTable
  private lazy val scheduledMoviesTable = Tables.scheduledMoviesTable
  private lazy val scheduledMovieWithSeatsTable = Tables.scheduledMovieWithSeatsTable
  private val databaseUtils = new DatabaseUtils(db)(ec)

  def findAllCinemaHalls(): Future[Seq[CinemaHall]] = db.run(cinemaHallsTable.result)

  def findAllCinemaHallsWithSeats(): Future[Seq[CinemaHallWithSeats]] = {
    val query = seatsTable.result.map { seat =>
      seat.groupBy(_.cinemaHallId).map { groupedSeat =>
        CinemaHallWithSeats(groupedSeat._1, groupedSeat._2.toList)
      }.toSeq
    }
    db.run(query)
  }

  def saveCinemaHalls(): Future[Unit] = {
    val cinemaHalls = Seq(CinemaHall(name = "Big"), CinemaHall(name = "Small"))
    databaseUtils.insertDataIfNotExists(cinemaHallsTable, cinemaHalls)
  }

  def saveSeats(): Future[Unit] = {
    val generatedSeats = List(
      CinemaHallGenerator.generateSeats(10, 10, 1L),
      CinemaHallGenerator.generateSeats(8, 8, 2L)
    ).flatten
    databaseUtils.insertDataIfNotExists(seatsTable, generatedSeats)
  }

  def findCinemaHallWithSeatsByScheduledMovieId(scheduledMovieId: Long): Future[Map[RowNumber, Seq[SeatDetails]]] = {
    val selectSeatsAndSeatsDetails = for {
      scheduledMovie <- scheduledMoviesTable if scheduledMovie.id === scheduledMovieId
      seat <- seatsTable if seat.cinemaHallId === scheduledMovie.cinemaHallId
      seatDetails <- scheduledMovieWithSeatsTable if seatDetails.scheduledMovieId === scheduledMovieId && seatDetails.seatId === seat.id
    } yield (seat, seatDetails)

    val groupByRowQuery = selectSeatsAndSeatsDetails.result.map { seats =>
      val seatsDetails = seats.map { case (seat, scheduledMovieWithSeat) =>
        SeatDetails(seat.seatNumber, seat.rowNumber, seat.columnNumber, seat.id, seat.cinemaHallId, scheduledMovieWithSeat.isFree)
      }
      seatsDetails.groupBy(seat => seat.rowNumber.toString)
    }
    db.run(groupByRowQuery)
  }

  type RowNumber = String
}

