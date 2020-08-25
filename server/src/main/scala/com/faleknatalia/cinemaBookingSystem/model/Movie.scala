package com.faleknatalia.cinemaBookingSystem.model


case class Movie(
                  id: Long,
                  title: String,
                  description: String,
                  durationInSeconds: Long,
                  imageUrl: String
                )

