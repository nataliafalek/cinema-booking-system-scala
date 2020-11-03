package com.faleknatalia.cinemaBookingSystem.payment

case class AccessToken(access_token: String, token_type: String, expires_in: Int, grant_type: String)