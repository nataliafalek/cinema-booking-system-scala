package com.faleknatalia.cinemaBookingSystem.reservation

case class Reservation(personalDataId: Long, scheduledMovieId: Long, seatId: Long, ticketPriceId: Long, id: Long = 0L)