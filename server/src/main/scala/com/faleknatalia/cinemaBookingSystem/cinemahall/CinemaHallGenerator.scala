package com.faleknatalia.cinemaBookingSystem.cinemahall

object CinemaHallGenerator {
  def generateSeats(numberOfRows: Int, numberOfColumns: Int, cinemaHallId: Long): List[Seat] = {
    (1 to numberOfRows).flatMap { row =>
      (1 to numberOfColumns).map { column =>
        Seat(column, row, column, cinemaHallId)
      }
    }.toList
  }
}
