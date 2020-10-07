package com.faleknatalia.cinemaBookingSystem

import java.net.URI
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.faleknatalia.cinemaBookingSystem.cinemahall.{CinemaHall, CinemaHallWithSeats, Seat}
import com.faleknatalia.cinemaBookingSystem.movie.{AddMovie, Movie, ScheduledMovie, ScheduledMovieDto}
import spray.json.{DefaultJsonProtocol, JsString, JsValue, JsonFormat, RootJsonFormat}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val ZonedDateTimeFormat: JsonFormat[ZonedDateTime] = new JsonFormat[ZonedDateTime] {
    override def read(json: JsValue): ZonedDateTime = ZonedDateTime.parse(json.convertTo[String], DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    override def write(obj: ZonedDateTime): JsValue = JsString(obj.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
  }
  implicit val uriFormat: JsonFormat[URI] = new JsonFormat[URI] {
    override def read(json: JsValue): URI = URI.create(json.toString())
    override def write(obj: URI): JsValue = JsString(obj.toString)
  }

  implicit val scheduledMovieFormat: RootJsonFormat[ScheduledMovie] = jsonFormat3(ScheduledMovie)
  implicit val addMovieFormat: RootJsonFormat[AddMovie] = jsonFormat4(AddMovie)
  implicit val movieFormat: RootJsonFormat[Movie] = jsonFormat5(Movie)
  implicit val ScheduledMovieDtoFormat: RootJsonFormat[ScheduledMovieDto] = jsonFormat4(ScheduledMovieDto)
  implicit val SeatFormat: RootJsonFormat[Seat] = jsonFormat4(Seat)
  implicit val CinemaHallFormat: RootJsonFormat[CinemaHall] = jsonFormat2(CinemaHall)
  implicit val CinemaHallWithSeatsFormat: RootJsonFormat[CinemaHallWithSeats] = jsonFormat2(CinemaHallWithSeats)
}
