package com.faleknatalia.cinemaBookingSystem.mail

import com.typesafe.config.ConfigFactory

object EmailConfig {
  private val config = ConfigFactory.load()
  private lazy val emailConfig = config.getConfig("email")

  lazy val emailStarttlsEnabled = emailConfig.getBoolean("mail.smtp.starttls.enable")
  lazy val emailStarttlsRequired = emailConfig.getBoolean("mail.smtp.starttls.required")
  lazy val emailProtocol= emailConfig.getString("mail.transport.protocol")
  lazy val emailTimeout = emailConfig.getInt("mail.smtp.timeout")
  lazy val emailConnectiontimeout = emailConfig.getInt("mail.smtp.connectiontimeout")
  lazy val emailHost = emailConfig.getString("mail.smtp.host")
  lazy val emailPort = emailConfig.getInt("mail.smtp.port")
  lazy val emailAuth = emailConfig.getBoolean("mail.smtp.auth")
  lazy val emailUserName = emailConfig.getString("username")
  lazy val emailPassword = emailConfig.getString("password")
  lazy val emailFrom = emailConfig.getString("from")
  lazy val emailEncoding = emailConfig.getString("encoding")
}
