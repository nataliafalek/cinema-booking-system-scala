package com.faleknatalia.cinemaBookingSystem.checkout

import com.faleknatalia.cinemaBookingSystem.cinemahall.Seat

case class SeatWithPrice(seat: Seat, price: TicketPrice)

case class Reservation(scheduledMovieId: Long, personalDataId: Long, chosenSeatsAndPrices: List[SeatWithPrice])
