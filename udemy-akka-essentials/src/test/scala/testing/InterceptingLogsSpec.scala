package testing

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, EventFilter, TestKit, TestProbe}
import org.scalatest.wordspec.{AnyWordSpecLike}
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfterAll
import akka.actor.ActorLogging
import com.typesafe.config.ConfigFactory

class InterceptingLogsSpec extends TestKit(ActorSystem("InterceptingLogsSpec", ConfigFactory.load().getConfig("interceptingTests")))
  with ImplicitSender
  with AnyWordSpecLike
  with Matchers
  with BeforeAndAfterAll {

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  import InterceptingLogsSpec._

  "A checkout flow" should {
    val item = "Rock the JVM Akka course"
    val creditCard = "1234-1234-1234-1234"
    val checkoutActor = system.actorOf(Props[CheckoutActor]())

    "correctly log the dispatch of an order" in {
      EventFilter.info(pattern = s"Order [0-9]+ for $item has been fulfilled", occurrences = 1) intercept {
        checkoutActor ! Checkout(item, creditCard)
      }
    }

    "freak out if the payment is denied" in {
      EventFilter.error("Payment denied for credit card 0-0-0-0", occurrences = 1) intercept {
        checkoutActor ! Checkout(item, "0-0-0-0")
      }
    }
  }
  
}


object InterceptingLogsSpec {

  case class Checkout(item: String, creditCard: String)
  case class AuthorizeCard(creditCard: String)
  case object PaymentAccepted
  case object PaymentDenied
  case class Fulfill(item: String)
  case object OrderFulfilled

  class CheckoutActor extends Actor {
    private val payment = context.actorOf(Props[PaymentActor]())
    private val fulfillment = context.actorOf(Props[FulfillmentActor]())  
    override def receive: Receive = awaitingCheckout

    def awaitingCheckout: Receive = {
      case Checkout(item, card) =>
        payment ! AuthorizeCard(card)
        context.become(pendingPayment(item, card))
    }

    def pendingPayment(item: String, card: String): Receive = {
      case PaymentAccepted =>
        fulfillment ! Fulfill(item)
        context.become(pendingFullfillment(item, card))
      case PaymentDenied =>
        throw new RuntimeException(s"Payment denied for credit card $card")
    }

    def pendingFullfillment(item: String, card: String): Receive = {
      case OrderFulfilled => context.become(awaitingCheckout)
    }
  }

  class PaymentActor extends Actor {
    override def receive: Receive = {
      case AuthorizeCard(creditCard) =>
        if (creditCard.startsWith("0")) sender() ! PaymentDenied
        else sender() ! PaymentAccepted
    }
  }

  class FulfillmentActor extends Actor with ActorLogging {

    val orderId = 43
    override def receive: Receive = {
      case Fulfill(item) => 
        orderId + 1
        log.info(s"Order $orderId for $item has been fulfilled")
        sender() ! OrderFulfilled
    }
  }

}