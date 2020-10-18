package com.faleknatalia.cinemaBookingSystem.mail

import com.faleknatalia.cinemaBookingSystem.checkout.TicketPrice
import com.faleknatalia.cinemaBookingSystem.cinemahall.Seat

case class SeatAndPriceDetails(seat: Seat, ticketPrice: TicketPrice)