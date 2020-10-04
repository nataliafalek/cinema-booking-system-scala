package com.faleknatalia.cinemaBookingSystem.cinemahall

object CinemaHallGenerator {
  def generateSeats(numberOfRows: Int, numberOfColumns: Int, cinemaHallId: Long): List[Seat] = {
    (0 to numberOfRows).flatMap { row =>
      (0 to numberOfColumns).map { column =>
        Seat(row, row, column, cinemaHallId)
      }
    }.toList
  }
}
