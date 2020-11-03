package com.faleknatalia.cinemaBookingSystem.payment

import com.faleknatalia.cinemaBookingSystem.reservation.Reservation

case class NotificationResponse(order: OrderResponseNotification,
                                localReceiptDateTime: String,
                                properties: List[PropertyNotification])

object NotificationResponse {
  def testNotificationResponse(reservation: Reservation): NotificationResponse = {
    val buyer = Buyer("naticinema@gmail.com", "123456789", "Michał", "Abcd")
    val payMethod = PayMethod("PBL")
    val orderResponseNotification = OrderResponseNotification(
      reservation.id.toString,
      reservation.id.toString,
      PaymentConfig.notifyUrl,
      "127.0.0.1",
      PaymentConfig.clientId,
      "bilecik test",
      "PLN",
      "100",
      buyer,
      payMethod,
      List.empty,
      "COMPLETED")
    val propertyNotificationList = List(PropertyNotification("PAYMENT_ID", "12345"))
    NotificationResponse(orderResponseNotification, "2016-03-02T12:58:14.828+01:00", propertyNotificationList)
  }
}

case class OrderResponseNotification(orderId: String,
                                     extOrderId: String,
                                     notifyUrl: String,
                                     customerIp: String,
                                     merchantPosId: String,
                                     description: String,
                                     currencyCode: String,
                                     totalAmount: String,
                                     buyer: Buyer,
                                     payMethod: PayMethod,
                                     products: List[Product] = List.empty,
                                     status: String)

case class PropertyNotification(name: String, value: String)

case class Buyer(email: String,
                 phone: String,
                 firstName: String,
                 lastName: String)

case class PayMethod(payType: String)