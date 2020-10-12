package com.faleknatalia.cinemaBookingSystem.checkout

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class PersonalDataTable(tag: Tag) extends Table[PersonalData](tag, "personal_data") {
  def firstName = column[String]("first_name")
  def lastName = column[String]("last_name")
  def phoneNumber = column[String]("phone_number")
  def email = column[String]("email")
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def * = (firstName, lastName, phoneNumber, email, id).mapTo[PersonalData]
}