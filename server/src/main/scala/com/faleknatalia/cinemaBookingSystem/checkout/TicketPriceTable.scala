package com.faleknatalia.cinemaBookingSystem.checkout
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class TicketPriceTable(tag: Tag) extends Table[TicketPrice](tag, "ticket_prices") {
  def ticketType = column[String]("type")
  def ticketPrice = column[Int]("price")
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def * = (ticketType, ticketPrice, id).mapTo[TicketPrice]
}