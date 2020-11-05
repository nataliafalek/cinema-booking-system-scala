package com.faleknatalia.cinemaBookingSystem.mail

import java.io.ByteArrayOutputStream
import java.nio.file.{Files, Paths}

import com.typesafe.scalalogging.LazyLogging
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import org.apache.pdfbox.pdmodel.{PDDocument, PDPage, PDPageContentStream}

//TODO POLSKIE ZNAKI:????
object TicketGenerator extends LazyLogging {
  def generateTicket(ticketData: TicketData): Array[Byte] = {
    logger.info("Preparing ticket...")
    val document = new PDDocument()
    try {
      val page = new PDPage()
      document.addPage(page)
      val contentStream = new PDPageContentStream(document, page)
      val image =  Files.readAllBytes(Paths.get("server/src/main/resources/static/logo-papryk-simple-white.png"))
      val pdImage = PDImageXObject.createFromByteArray(document, image, null)
      contentStream.drawImage(pdImage, 160, 700, 200, 70)
      contentStream.beginText()
      contentStream.setLeading(14.5f)
      contentStream.newLineAtOffset(50, 670)
      contentStream.setFont(PDType1Font.TIMES_ROMAN, 16)
      contentStream.showText("Title: " + ticketData.movieTitle)
      contentStream.newLine()
      contentStream.newLine()
      contentStream.showText("Date: " + ticketData.projectionDate + "       ")
      contentStream.showText("Hour: " + ticketData.projectionHour)
      contentStream.newLine()
      contentStream.newLine()
      contentStream.showText("Cinema hall: " + ticketData.cinemaHallName + "  ")
      contentStream.newLine()
      ticketData.seatAndPriceDetails.foreach { seatAndPrice =>
        contentStream.newLine()
        val ticketDetails = "Seat number: " + seatAndPrice.seat.seatNumber.toString +
          ", row: " + seatAndPrice.seat.rowNumber.toString + ", " + seatAndPrice.ticketPrice.ticketType +
          " " + seatAndPrice.ticketPrice.ticketPrice.toString + " $"
        contentStream.showText(ticketDetails);

      }
      contentStream.endText()
      contentStream.close()
      val byteArrayOutputStream = new ByteArrayOutputStream()
      document.save(byteArrayOutputStream)
      logger.info("Generated ticket")
      byteArrayOutputStream.toByteArray
    } finally {
      document.close()
    }
  }
}