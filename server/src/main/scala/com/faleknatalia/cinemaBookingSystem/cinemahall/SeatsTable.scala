package com.faleknatalia.cinemaBookingSystem.cinemahall

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class SeatsTable(tag: Tag) extends Table[Seat](tag, "seats") {
  def seatNumber = column[Int]("seat_number")
  def rowNumber = column[Int]("row_number")
  def columnNumber = column[Int]("column_number")
  def cinemaHallId = column[Long]("cinemaHall_id")
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def * = (seatNumber, rowNumber, columnNumber, cinemaHallId, id).mapTo[Seat]
}