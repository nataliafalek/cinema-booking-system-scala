package com.faleknatalia.cinemaBookingSystem.dbutils

import slick.jdbc
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.{ExecutionContext, Future}

class DatabaseUtils(db: jdbc.JdbcBackend.Database)(implicit ec: ExecutionContext) {
   def insertDataIfNotExists[A, B <: Table[A]](table: TableQuery[B], dataToInsert: Iterable[A]): Future[Unit] = {
    val query = table.result.flatMap { data =>
      if (data.isEmpty) {
        (table ++= dataToInsert).map(_ => ())
      } else {
        DBIO.successful(())
      }
    }
    db.run(query)
  }

  def schemaCreate(ddl: PostgresProfile.DDL): Future[Unit] = {
    db.run(ddl.createIfNotExists)
  }
}
