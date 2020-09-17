package com.faleknatalia.cinemaBookingSystem.movie

import java.net.URI

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class MovieTable(tag: Tag) extends Table[Movie](tag, "movie") {
  implicit val uriType = MappedColumnType.base[URI, String](uri => uri.toString, s => URI.create(s))

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def title = column[String]("title")
  def description = column[String]("description")
  def durationInSeconds = column[Long]("duration_in_seconds")
  def imageUrl = column[URI]("image_url")
  def * = (title, description, durationInSeconds, imageUrl, id).mapTo[Movie]
}
