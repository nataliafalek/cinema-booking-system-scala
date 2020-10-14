package com.faleknatalia.cinemaBookingSystem

import java.net.URI
import java.time.{DayOfWeek, ZonedDateTime}
import java.time.format.DateTimeFormatter

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.faleknatalia.cinemaBookingSystem.checkout.{PersonalData, PersonalDataDto, TicketPrice}
import com.faleknatalia.cinemaBookingSystem.cinemahall.{CinemaHall, CinemaHallWithSeats, Seat, SeatDetails}
import com.faleknatalia.cinemaBookingSystem.movie.{AddMovie, AddScheduledMovieDto, Movie, MovieCardDto, ScheduledMovie, ScheduledMovieDto, TitleByScheduleMovies}
import com.faleknatalia.cinemaBookingSystem.reservation.{ReservationDto, SeatAndPrice}
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

  implicit val dayOfWeekFormat: JsonFormat[DayOfWeek] = new JsonFormat[DayOfWeek] {
    override def read(json: JsValue): DayOfWeek = DayOfWeek.valueOf(json.toString())
    override def write(obj: DayOfWeek): JsValue = JsString(obj.name())
  }


  implicit val scheduledMovieFormat: RootJsonFormat[ScheduledMovie] = jsonFormat4(ScheduledMovie)
  implicit val scheduledMovie2Format: RootJsonFormat[AddScheduledMovieDto] = jsonFormat3(AddScheduledMovieDto)
  implicit val addMovieFormat: RootJsonFormat[AddMovie] = jsonFormat4(AddMovie)
  implicit val movieFormat: RootJsonFormat[Movie] = jsonFormat5(Movie)
  implicit val ScheduledMovieDtoFormat: RootJsonFormat[ScheduledMovieDto] = jsonFormat4(ScheduledMovieDto)
  implicit val SeatFormat: RootJsonFormat[Seat] = jsonFormat5(Seat)
  implicit val CinemaHallFormat: RootJsonFormat[CinemaHall] = jsonFormat2(CinemaHall)
  implicit val CinemaHallWithSeatsFormat: RootJsonFormat[CinemaHallWithSeats] = jsonFormat2(CinemaHallWithSeats)
  implicit val TitleByScheduleMoviesFormat: RootJsonFormat[TitleByScheduleMovies] = jsonFormat1(TitleByScheduleMovies)
  implicit val MovieCardDtoFormat: RootJsonFormat[MovieCardDto] = jsonFormat4(MovieCardDto)
  implicit val TicketPriceFormat: RootJsonFormat[TicketPrice] = jsonFormat3(TicketPrice)
  implicit val PersonalDataDtoFormat: RootJsonFormat[PersonalDataDto] = jsonFormat4(PersonalDataDto)
  implicit val PersonalDataFormat: RootJsonFormat[PersonalData] = jsonFormat5(PersonalData)
  implicit val SeatDetailsFormat: RootJsonFormat[SeatDetails] = jsonFormat6(SeatDetails)
  implicit val ReservationDtoFormat: RootJsonFormat[ReservationDto] = jsonFormat3(ReservationDto)
  implicit val SeatAndPriceFormat: RootJsonFormat[SeatAndPrice] = jsonFormat2(SeatAndPrice)
}
