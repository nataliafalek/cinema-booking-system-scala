package com.faleknatalia.cinemaBookingSystem.movie

case class ScheduledMovieWithSeats(scheduledMovieId: Long,
                                   cinemaHallId: Long,
                                   isFree: Boolean,
                                   ticketPriceId: Long,
                                   seatId: Long,
                                   id: Long)
