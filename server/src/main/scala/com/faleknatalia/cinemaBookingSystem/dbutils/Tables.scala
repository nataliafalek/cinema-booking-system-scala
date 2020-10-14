package com.faleknatalia.cinemaBookingSystem.dbutils

import com.faleknatalia.cinemaBookingSystem.checkout.{PersonalDataTable, TicketPriceTable}
import com.faleknatalia.cinemaBookingSystem.cinemahall.{CinemaHallTable, SeatsTable}
import com.faleknatalia.cinemaBookingSystem.movie.{MovieTable, ScheduledMovieTable, ScheduledMovieWithSeatTable}
import com.faleknatalia.cinemaBookingSystem.reservation.ReservationTable
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._

object Tables {
   lazy val moviesTable = TableQuery[MovieTable]
   lazy val scheduledMoviesTable = TableQuery[ScheduledMovieTable]
   lazy val ticketPriceTable = TableQuery[TicketPriceTable]
   lazy val personalDataTable = TableQuery[PersonalDataTable]
   lazy val cinemaHallsTable = TableQuery[CinemaHallTable]
   lazy val seatsTable = TableQuery[SeatsTable]
   lazy val scheduledMovieWithSeatsTable = TableQuery[ScheduledMovieWithSeatTable]
   lazy val reservationTable = TableQuery[ReservationTable]

   def allTablesSchema(): PostgresProfile.DDL = {
      moviesTable.schema ++ scheduledMoviesTable.schema ++ ticketPriceTable.schema ++ personalDataTable.schema ++ cinemaHallsTable.schema ++ seatsTable.schema ++ scheduledMovieWithSeatsTable.schema ++ reservationTable.schema
   }
}
