package com.faleknatalia.cinemaBookingSystem.reservation

//TODO add reservation date
case class Reservation(personalDataId: Long, scheduledMovieId: Long, seatId: Long, ticketPriceId: Long, id: Long = 0L)