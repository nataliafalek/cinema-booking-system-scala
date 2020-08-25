package com.faleknatalia.cinemaBookingSystem

import java.time.LocalDateTime

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.faleknatalia.cinemaBookingSystem.model.ScheduledMovie
import spray.json.{DefaultJsonProtocol, JsString, JsValue, JsonFormat}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val localDateTimeFormat: JsonFormat[LocalDateTime] = new JsonFormat[LocalDateTime] {
    override def read(json: JsValue): LocalDateTime = ???

    override def write(obj: LocalDateTime): JsValue = JsString(obj.toString)
  }
  implicit val scheduledMovieFormat = jsonFormat3(ScheduledMovie)
}
