package com.faleknatalia.cinemaBookingSystem.payment

import com.typesafe.config.ConfigFactory

object PaymentConfig {
  private val config = ConfigFactory.load()
  private lazy val emailConfig = config.getConfig("payU")

  lazy val paymentAuthorizationUrl = emailConfig.getString("paymentAuthorizationUrl")
  lazy val clientId = emailConfig.getString("clientId")
  lazy val clientSecret = emailConfig.getString("clientSecret")
  lazy val notifyUrl = emailConfig.getString("notifyUrl")
  lazy val paymentCustomerIp = emailConfig.getString("paymentCustomerIp")
  lazy val redirectUrl = emailConfig.getString("redirectUrl")
  lazy val createOrderUrl = emailConfig.getString("createOrderUrl")
}
