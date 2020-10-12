package com.faleknatalia.cinemaBookingSystem.checkout

import com.faleknatalia.cinemaBookingSystem.dbutils.{DatabaseUtils, Tables}
import slick.jdbc
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{ExecutionContext, Future}

class CheckoutService(db: jdbc.JdbcBackend.Database)(implicit ec: ExecutionContext) {
  private lazy val ticketPriceTable = Tables.ticketPriceTable
  private val databaseUtils = new DatabaseUtils(db)(ec)

  def listTicketPrices(): Future[Seq[TicketPrice]] = {
    db.run(ticketPriceTable.result)
  }

  def saveTicketData(): Future[Unit] = {
    val ticketData = List(TicketPrice("student", 20), TicketPrice("normal", 30), TicketPrice("senior", 20))
    databaseUtils.insertDataIfNotExists(ticketPriceTable, ticketData)
  }
}
