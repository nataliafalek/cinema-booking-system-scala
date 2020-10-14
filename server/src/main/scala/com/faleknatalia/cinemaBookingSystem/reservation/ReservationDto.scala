package com.faleknatalia.cinemaBookingSystem.reservation

case class ReservationDto(personalDataId: Long, scheduledMovieId: Long, chosenSeatsAndPrices: List[SeatAndPrice])
