package com.faleknatalia.cinemaBookingSystem.cinemahall

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class SeatTable(tag: Tag) extends Table[Seat](tag, "seat") {
  def seatNumber = column[Int]("seat_number")
  def rowNumber = column[Int]("row_number")
  def columnNumber = column[Int]("column_number")
  def cinemaHallId = column[Long]("cinemaHall_id")
  def * = (seatNumber, rowNumber, columnNumber, cinemaHallId).mapTo[Seat]
}