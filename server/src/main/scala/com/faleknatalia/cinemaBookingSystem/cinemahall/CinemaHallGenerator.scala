package com.faleknatalia.cinemaBookingSystem.cinemahall

object CinemaHallGenerator {
  def generateCinemaHalls(): List[CinemaHall] = {
    List(CinemaHall(1, List.empty), CinemaHall(2, List.empty))
  }
}
