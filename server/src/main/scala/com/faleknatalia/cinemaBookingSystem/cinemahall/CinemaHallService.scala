package com.faleknatalia.cinemaBookingSystem.cinemahall


import slick.jdbc
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.{ExecutionContext, Future}

class CinemaHallService(db: jdbc.JdbcBackend.Database)(implicit ec: ExecutionContext)  {
  private lazy val cinemaHall = TableQuery[CinemaHallTable]
  private lazy val seatTable = TableQuery[SeatTable]

  def cinemaHallSchemaCreate(): Future[Unit] = {
    val ddl = cinemaHall.schema ++ seatTable.schema
    db.run(ddl create)
  }

  def findAllCinemaHalls(): Future[Seq[CinemaHall]] = db.run(cinemaHall.result)

  def findAllCinemaHallsWithSeats(): Future[Seq[CinemaHallWithSeats]] = {
    val query = seatTable.result.map { seat =>
      seat.groupBy(_.cinemaHallId).map { groupedSeat =>
        CinemaHallWithSeats(groupedSeat._1, groupedSeat._2.toList)
      }.toSeq
    }
    db.run(query)
  }

  def saveCinemaHalls(): Unit = {
    val cinemaHalls = List(CinemaHall(name = "Big"), CinemaHall(name = "Small"))
    db.run(cinemaHall ++= cinemaHalls)
  }

  def saveSeats(): Unit = {
    val generatedSeats = List(
      CinemaHallGenerator.generateSeats(10, 10, 1L),
      CinemaHallGenerator.generateSeats(8, 8, 2L)
    ).flatten
    db.run(seatTable ++= generatedSeats)
  }
}
