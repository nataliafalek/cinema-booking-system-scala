package com.faleknatalia.cinemaBookingSystem.movie

import java.time.ZonedDateTime

case class ScheduledMovieDto(movieId: Long, title: String, start: ZonedDateTime, end: ZonedDateTime)
