package com.faleknatalia.cinemaBookingSystem

import java.net.URI
import java.time.{DayOfWeek, ZonedDateTime}
import java.time.format.DateTimeFormatter

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.faleknatalia.cinemaBookingSystem.checkout.{PersonalData, PersonalDataDto, TicketPrice}
import com.faleknatalia.cinemaBookingSystem.cinemahall.{CinemaHall, CinemaHallWithSeats, Seat, SeatDetails}
import com.faleknatalia.cinemaBookingSystem.movie.{AddMovie, AddScheduledMovieDto, Movie, MovieCardDto, ScheduledMovie, ScheduledMovieDto, TitleByScheduleMovies}
import com.faleknatalia.cinemaBookingSystem.payment.{AccessToken, Buyer, OrderRequest, OrderResponse, Product, RedirectUri, Status}
import com.faleknatalia.cinemaBookingSystem.reservation.{ReservationDto, SeatAndPrice}
import spray.json.{DefaultJsonProtocol, JsString, JsValue, JsonFormat, RootJsonFormat}

object JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit lazy val ZonedDateTimeFormat: JsonFormat[ZonedDateTime] = new JsonFormat[ZonedDateTime] {
    override def read(json: JsValue): ZonedDateTime = ZonedDateTime.parse(json.convertTo[String], DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    override def write(obj: ZonedDateTime): JsValue = JsString(obj.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
  }
  implicit lazy val uriFormat: JsonFormat[URI] = new JsonFormat[URI] {
    override def read(json: JsValue): URI = URI.create(json.toString())
    override def write(obj: URI): JsValue = JsString(obj.toString)
  }

  implicit lazy val dayOfWeekFormat: JsonFormat[DayOfWeek] = new JsonFormat[DayOfWeek] {
    override def read(json: JsValue): DayOfWeek = DayOfWeek.valueOf(json.toString())
    override def write(obj: DayOfWeek): JsValue = JsString(obj.name())
  }

  implicit lazy val scheduledMovieFormat: RootJsonFormat[ScheduledMovie] = jsonFormat4(ScheduledMovie)
  implicit lazy val scheduledMovie2Format: RootJsonFormat[AddScheduledMovieDto] = jsonFormat3(AddScheduledMovieDto)
  implicit lazy val addMovieFormat: RootJsonFormat[AddMovie] = jsonFormat4(AddMovie)
  implicit lazy val movieFormat: RootJsonFormat[Movie] = jsonFormat5(Movie)
  implicit lazy val ScheduledMovieDtoFormat: RootJsonFormat[ScheduledMovieDto] = jsonFormat4(ScheduledMovieDto)
  implicit lazy val SeatFormat: RootJsonFormat[Seat] = jsonFormat5(Seat)
  implicit lazy val CinemaHallFormat: RootJsonFormat[CinemaHall] = jsonFormat2(CinemaHall)
  implicit lazy val CinemaHallWithSeatsFormat: RootJsonFormat[CinemaHallWithSeats] = jsonFormat2(CinemaHallWithSeats)
  implicit lazy val TitleByScheduleMoviesFormat: RootJsonFormat[TitleByScheduleMovies] = jsonFormat1(TitleByScheduleMovies)
  implicit lazy val MovieCardDtoFormat: RootJsonFormat[MovieCardDto] = jsonFormat4(MovieCardDto)
  implicit lazy val TicketPriceFormat: RootJsonFormat[TicketPrice] = jsonFormat3(TicketPrice)
  implicit lazy val PersonalDataDtoFormat: RootJsonFormat[PersonalDataDto] = jsonFormat4(PersonalDataDto)
  implicit lazy val PersonalDataFormat: RootJsonFormat[PersonalData] = jsonFormat5(PersonalData)
  implicit lazy val SeatDetailsFormat: RootJsonFormat[SeatDetails] = jsonFormat6(SeatDetails)
  implicit lazy val SeatAndPriceFormat: RootJsonFormat[SeatAndPrice] = jsonFormat2(SeatAndPrice)
  implicit lazy val ReservationDtoFormat: RootJsonFormat[ReservationDto] = jsonFormat3(ReservationDto)
  implicit lazy val AccessTokenFormat: RootJsonFormat[AccessToken] = jsonFormat4(AccessToken)
  implicit lazy val ProductFormat: RootJsonFormat[Product] = jsonFormat3(Product)
  implicit lazy val BuyerFormat: RootJsonFormat[Buyer] = jsonFormat4(Buyer)
  implicit lazy val OrderRequestFormat: RootJsonFormat[OrderRequest] = jsonFormat10(OrderRequest.apply)
  implicit lazy val StatusFormat: RootJsonFormat[Status] = jsonFormat1(Status)
  implicit lazy val OrderResponseFormat: RootJsonFormat[OrderResponse] = jsonFormat4(OrderResponse)
  implicit lazy val RedirectUriFormat: RootJsonFormat[RedirectUri] = jsonFormat1(RedirectUri)
}