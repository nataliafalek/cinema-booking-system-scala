package com.faleknatalia.cinemaBookingSystem.movie

case class MovieCardDto(title: String, id: Long, scheduledMovieIdWithStartTime: Seq[(Long, String)], imageUrl: String)
