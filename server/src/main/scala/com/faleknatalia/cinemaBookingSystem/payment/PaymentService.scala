package com.faleknatalia.cinemaBookingSystem.payment

import java.util.UUID

import akka.actor.typed.ActorSystem
import akka.http.javadsl.model.headers.HttpCredentials
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.Authorization
import akka.http.scaladsl.unmarshalling.{Unmarshal, Unmarshaller}
import akka.stream.Materializer
import com.faleknatalia.cinemaBookingSystem.JsonSupport
import com.faleknatalia.cinemaBookingSystem.JsonSupport._
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.{ExecutionContext, Future}

class PaymentService()(implicit ec: ExecutionContext, system: ActorSystem[Nothing]) extends LazyLogging {

  def performPayment(): Future[OrderResponse] = {
    val accessToken = generateAccessToken(PaymentConfig.clientId, PaymentConfig.clientSecret)
    accessToken.flatMap { token =>
      sendOrder(token, PaymentConfig.clientId)
    }
  }

  private def sendOrder(accessToken: AccessToken, clientId: String): Future[OrderResponse] = {
    val orderRequest = OrderRequest.testOrderRequest(clientId, UUID.randomUUID().toString)
    performPayOrder(accessToken, orderRequest)
  }

  private def generateAccessToken(clientId: String, clientSecret: String): Future[AccessToken] = {
    val contentType = ContentType(MediaTypes.`application/x-www-form-urlencoded`.withParams(Map("charset" -> "utf-8")))
    val request = s"grant_type=client_credentials&client_id=${clientId}&client_secret=${clientSecret}"

    val httpRequest = HttpRequest(
      method = POST,
      uri = PaymentConfig.paymentAuthorizationUrl,
      entity = HttpEntity(contentType, request)
    )

    invoke[AccessToken](httpRequest)
  }

  private def invoke[A](httpRequest: HttpRequest)
                       (implicit um: Unmarshaller[HttpResponse, A], ec: ExecutionContext, mat: Materializer): Future[A] = {
    Http().singleRequest(httpRequest).flatMap { response =>
      Unmarshal(response).to[A]
    }
  }

  private def performPayOrder(accessToken: AccessToken, orderRequest: OrderRequest): Future[OrderResponse] = {
    val contentType = ContentType(MediaTypes.`application/json`)
    val httpHeader = Authorization(HttpCredentials.createOAuth2BearerToken(accessToken.access_token))

    val httpRequest = HttpRequest(
      method = POST,
      uri = PaymentConfig.createOrderUrl,
      entity = HttpEntity(contentType, JsonSupport.OrderRequestFormat.write(orderRequest).compactPrint),
      headers = List(httpHeader)
    )
    invoke[OrderResponse](httpRequest)
  }
}

case class OrderRequest(extOrderId: String,
                         notifyUrl: String,
                         customerIp: String,
                         merchantPosId: String,
                         description: String,
                         currencyCode: String,
                         totalAmount: String,
                         buyer: Buyer,
                         products: List[Product],
                         continueUrl: String)

object OrderRequest {
  def testOrderRequest(clientId: String, reservationId: String): OrderRequest = {
    OrderRequest(
      reservationId,
      PaymentConfig.notifyUrl,
      PaymentConfig.paymentCustomerIp,
      clientId,
      "Cinema ticket",
      "PLN",
      "100",
      Buyer("faleknatalia@gmail.com", "123456789", "Nati", "Falek"),
      List(Product("ticket", "10", "1")),
      PaymentConfig.redirectUrl
    )
  }
}

case class OrderResponse(orderId: String,
                         extOrderId: String,
                         statusCode: String,
                         redirectUri: String)

case class Product(name: String,
                    unitPrice: String,
                    quantity: String)

