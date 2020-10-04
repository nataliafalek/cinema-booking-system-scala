package com.faleknatalia.cinemaBookingSystem.movie

import java.net.URI

case class Movie(title: String, description: String, durationInSeconds: Long, imageUrl: URI, id: Long = 0L)
