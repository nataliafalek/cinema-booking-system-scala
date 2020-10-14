package com.faleknatalia.cinemaBookingSystem.movie

import java.time.ZonedDateTime

case class ScheduledMovie(movieId: Long, dateOfProjection: ZonedDateTime, cinemaHallId: Long, id: Long = 0L)
