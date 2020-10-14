package com.faleknatalia.cinemaBookingSystem.movie

case class ScheduledMovieWithSeat(scheduledMovieId: Long,
                                  cinemaHallId: Long,
                                  isFree: Boolean,
                                  seatId: Long,
                                  id: Long = 0L)
