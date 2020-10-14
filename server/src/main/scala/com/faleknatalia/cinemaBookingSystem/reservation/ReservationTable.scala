package com.faleknatalia.cinemaBookingSystem.reservation

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

case class ReservationTable(tag: Tag) extends Table[Reservation](tag, "reservations") {
  def personalDataId = column[Long]("personal_data_id")
  def scheduledMovieId = column[Long]("scheduled_movie_id")
  def seatId = column[Long]("seat_id")
  def ticketPriceId = column[Long]("ticket_price_id")
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def * = (personalDataId, scheduledMovieId, seatId, ticketPriceId, id).mapTo[Reservation]
}
