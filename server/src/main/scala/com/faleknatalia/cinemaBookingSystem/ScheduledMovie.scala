package com.faleknatalia.cinemaBookingSystem

import java.time.LocalDateTime

case class ScheduledMovie(
                         movieId: Long,
                         dateOfProjection: LocalDateTime,
                         cinemaHallId: Long
                         )
