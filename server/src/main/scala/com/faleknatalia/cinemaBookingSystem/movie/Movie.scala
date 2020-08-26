package com.faleknatalia.cinemaBookingSystem.movie

case class Movie(
                  id: Long,
                  title: String,
                  description: String,
                  durationInSeconds: Long,
                  imageUrl: String
                )
