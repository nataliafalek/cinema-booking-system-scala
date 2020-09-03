package com.faleknatalia.cinemaBookingSystem.movie

case class Movie(
                  id: Long = 1L,
                  title: String,
                  description: String,
                  durationInSeconds: Long,
                  imageUrl: String
                )
