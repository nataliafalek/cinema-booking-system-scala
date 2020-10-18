package com.faleknatalia.cinemaBookingSystem.mail

import java.util.Properties

import com.typesafe.scalalogging.LazyLogging
import javax.activation.DataHandler
import javax.mail.Session
import javax.mail.internet.{InternetAddress, MimeBodyPart, MimeMessage, MimeMultipart}
import javax.mail.util.ByteArrayDataSource

import scala.concurrent.{ExecutionContext, Future}

object EmailSender extends LazyLogging {
  def sendEmailWithTicket(to: String, subject: String, content: String, document: Array[Byte])
                         (implicit ec: ExecutionContext): Future[Unit] = Future {
    logger.info(s"Preparing email to send to ${to}...")
    val props = getProperties
    val session = Session.getInstance(props)

    val mimeMessage = new MimeMessage(session)
    mimeMessage.setFrom(new InternetAddress(EmailConfig.emailFrom))

    mimeMessage.setRecipients(javax.mail.Message.RecipientType.TO, to)
    mimeMessage.setSubject(subject, EmailConfig.emailEncoding)

    if (document.length > 0) {
      mimeMessage.setText(content, EmailConfig.emailEncoding, "plain")
      addTicket(mimeMessage, document)
    }

    connectToEmailServer(session, mimeMessage, to)
  }

  private def connectToEmailServer(session: Session, m: MimeMessage, to: String): Unit = {
    val transport = session.getTransport(EmailConfig.emailProtocol)
    try {
      transport.connect(EmailConfig.emailUserName, EmailConfig.emailPassword)
      transport.sendMessage(m, m.getAllRecipients)
      logger.info(s"Email sent to ${to}...")
    } catch {
      case ex: Exception =>
        logger.error("Exception occurred", ex)
        throw ex
    } finally {
      transport.close()
    }
  }

  private def getProperties: Properties = {
    val props = new Properties()
    props.put("mail.transport.protocol", EmailConfig.emailProtocol)
    props.put("mail.smtp.host", EmailConfig.emailHost)
    props.put("mail.smtp.auth", EmailConfig.emailAuth)
    props.put("mail.smtp.starttls.enable", EmailConfig.emailStarttlsEnabled)
    props.put("mail.smtp.starttls.required", EmailConfig.emailStarttlsRequired)
    props.put("mail.smtp.port", EmailConfig.emailPort)
    props.put("mail.smtp.timeout", EmailConfig.emailTimeout);
    props.put("mail.smtp.connectiontimeout", EmailConfig.emailConnectiontimeout);
    props
  }

  private def addTicket(mimeMessage: MimeMessage, document: Array[Byte]): Unit = {
    val multiPart = new MimeMultipart()
    val bodyPart = new MimeBodyPart()
    val ds = new ByteArrayDataSource(document, "application/pdf")

    bodyPart.setDataHandler(new DataHandler(ds))
    bodyPart.setFileName("ticket.pdf")
    bodyPart.setDescription("LOL2")

    multiPart.addBodyPart(bodyPart)
    mimeMessage.setContent(multiPart)
  }
}
