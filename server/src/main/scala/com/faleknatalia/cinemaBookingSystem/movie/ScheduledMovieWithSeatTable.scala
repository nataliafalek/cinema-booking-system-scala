package com.faleknatalia.cinemaBookingSystem.movie

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class ScheduledMovieWithSeatTable(tag: Tag) extends Table[ScheduledMovieWithSeat](tag, "scheduled_movie_with_seats") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def scheduledMovieId = column[Long]("scheduledMovie_id")
  def cinemaHallId = column[Long]("cinema_hall_id")
  def isFree = column[Boolean]("is_free")
  def seatId = column[Long]("seat_id")
  def * = (scheduledMovieId, cinemaHallId, isFree, seatId, id).mapTo[ScheduledMovieWithSeat]
}
