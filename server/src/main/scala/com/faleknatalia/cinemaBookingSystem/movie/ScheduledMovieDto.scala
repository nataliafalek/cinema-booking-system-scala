package com.faleknatalia.cinemaBookingSystem.movie

import java.time.ZonedDateTime

case class ScheduledMovieDto(title: String, start: ZonedDateTime, end: ZonedDateTime)
