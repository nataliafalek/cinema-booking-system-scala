package com.faleknatalia.cinemaBookingSystem

import java.net.URI
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.faleknatalia.cinemaBookingSystem.cinemahall.{CinemaHall, Seat}
import com.faleknatalia.cinemaBookingSystem.movie.{AddMovie, Movie, ScheduledMovie, ScheduledMovieDto}
import spray.json.{DefaultJsonProtocol, JsString, JsValue, JsonFormat}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val ZonedDateTimeFormat: JsonFormat[ZonedDateTime] = new JsonFormat[ZonedDateTime] {
    override def read(json: JsValue): ZonedDateTime = ZonedDateTime.parse(json.convertTo[String], DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    override def write(obj: ZonedDateTime): JsValue = JsString(obj.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
  }
  implicit val uriFormat: JsonFormat[URI] = new JsonFormat[URI] {
    override def read(json: JsValue): URI = URI.create(json.toString())
    override def write(obj: URI): JsValue = JsString(obj.toString)
  }

  implicit val scheduledMovieFormat = jsonFormat3(ScheduledMovie)
  implicit val addMovieFormat = jsonFormat4(AddMovie)
  implicit val movieFormat = jsonFormat5(Movie)
  implicit val ScheduledMovieDtoFormat = jsonFormat3(ScheduledMovieDto)
  implicit val SeatFormat = jsonFormat4(Seat)
  implicit val CinemaHallFormat = jsonFormat2(CinemaHall)
}
