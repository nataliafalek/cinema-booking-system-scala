package com.faleknatalia.cinemaBookingSystem.movie

import java.time.ZonedDateTime

case class AddScheduledMovieDto(movieId: Long, dateOfProjection: ZonedDateTime, cinemaHallId: Long)

