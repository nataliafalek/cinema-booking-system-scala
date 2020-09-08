package com.faleknatalia.cinemaBookingSystem.movie

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class MovieTable(tag: Tag) extends Table[Movie](tag, "movie") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def title = column[String]("title")
  def description = column[String]("description")
  def durationInSeconds = column[Long]("duration_in_seconds")
  def imageUrl = column[String]("image_url")
  def * = (id, title, description, durationInSeconds, imageUrl).mapTo[Movie]
}
