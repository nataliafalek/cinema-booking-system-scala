import com.faleknatalia.cinemaBookingSystem.mail.{TicketData, TicketGenerator}
import org.scalatest.flatspec.AnyFlatSpec

class TicketGeneratorSpec extends AnyFlatSpec {
  "Pdf Generator" should "generate non empty document" in {
    val ticket = TicketGenerator.generateTicket(TicketData("Test", "12", "12", "Test", List.empty))
    assert(ticket.length > 0)
  }
}
