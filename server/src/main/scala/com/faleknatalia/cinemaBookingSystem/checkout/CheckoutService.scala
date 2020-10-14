package com.faleknatalia.cinemaBookingSystem.checkout

import com.faleknatalia.cinemaBookingSystem.dbutils.{DatabaseUtils, Tables}
import slick.jdbc
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{ExecutionContext, Future}

class CheckoutService(db: jdbc.JdbcBackend.Database)(implicit ec: ExecutionContext) {
  private lazy val ticketPriceTable = Tables.ticketPriceTable
  private lazy val personalDataTable = Tables.personalDataTable
  private val databaseUtils = new DatabaseUtils(db)(ec)

  def listTicketPrices(): Future[Seq[TicketPrice]] = {
    db.run(ticketPriceTable.result)
  }

  def saveTicketData(): Future[Unit] = {
    val ticketData = List(TicketPrice("student", 20), TicketPrice("normal", 30), TicketPrice("senior", 20))
    databaseUtils.insertDataIfNotExists(ticketPriceTable, ticketData)
  }

  def savePersonalData(personalDataDto: PersonalDataDto): Future[Long] = {
    val personalData = PersonalData(personalDataDto.firstName, personalDataDto.lastName, personalDataDto.phoneNumber, personalDataDto.email)
    val addPersonalData = personalDataTable returning personalDataTable.map(_.id) += personalData
    db.run(addPersonalData)
  }

  def getPersonalDataById(personalDataId: Long): Future[Option[PersonalData]] = {
    val personalData = personalDataTable.filter(personalData => personalData.id === personalDataId).take(1).result.headOption
    db.run(personalData)
  }
}
