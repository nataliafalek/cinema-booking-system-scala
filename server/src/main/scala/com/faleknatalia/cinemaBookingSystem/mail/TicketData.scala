package com.faleknatalia.cinemaBookingSystem.mail

case class TicketData(movieTitle: String,
                      projectionDate: String,
                      projectionHour: String,
                      cinemaHallName: String,
                      seatAndPriceDetails: List[SeatAndPriceDetails])
