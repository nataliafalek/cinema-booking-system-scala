package com.faleknatalia.cinemaBookingSystem.movie

import java.net.URI

case class Movie(
                  id: Long = 1L,
                  title: String,
                  description: String,
                  durationInSeconds: Long,
                  imageUrl: URI
                )
