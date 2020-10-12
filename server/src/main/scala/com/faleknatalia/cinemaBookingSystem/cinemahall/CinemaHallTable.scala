package com.faleknatalia.cinemaBookingSystem.cinemahall

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class CinemaHallTable(tag: Tag) extends Table[CinemaHall](tag, "cinema_halls") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def * = (name, id).mapTo[CinemaHall]
}