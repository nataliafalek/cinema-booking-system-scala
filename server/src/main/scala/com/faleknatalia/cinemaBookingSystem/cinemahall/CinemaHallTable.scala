package com.faleknatalia.cinemaBookingSystem.cinemahall

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class CinemaHallTable(tag: Tag)  extends Table[CinemaHall](tag, "cinema_hall") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def * = (id, name).mapTo[CinemaHall]
}