package com.faleknatalia.cinemaBookingSystem.cinemahall

case class SeatDetails(seatNumber: Int,
                       rowNumber: Int,
                       columnNumber: Int,
                       seatId: Long,
                       cinemaHallId: Long,
                       isFree: Boolean)
