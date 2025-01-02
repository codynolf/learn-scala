package faulttolerance

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import org.scalatest.wordspec.{AnyWordSpecLike}
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfterAll

class SupervisionSpec extends TestKit(ActorSystem("SupervisionSpec"))
  with ImplicitSender
  with AnyWordSpecLike
  with Matchers
  with BeforeAndAfterAll {

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  import SupervisionSpec._

  "A supervisor" should {
    "resume the child actor in case of a minor fault" in {
      val supervisor = system.actorOf(Props[Supervisor](), "supervisor")
      supervisor ! Props[FussyWordCounter]()
      val child = expectMsgType[ActorRef]

      child ! "I love Akka"
      child ! "This is amazing"
      child ! ""
      child ! "This is a very long sentence that will make the child actor fail"

      val state = child ! "state"
      expectMsg(3)
    }
  }
  
}

object SupervisionSpec {
  class FussyWordCounter extends Actor {
    var words = 0

    override def receive: Receive = {
      case "" => throw new NullPointerException("Empty string")
      case sentence: String =>
        if (sentence.length > 20) throw new RuntimeException("Sentence too big")
        else if (!Character.isUpperCase(sentence(0))) throw new IllegalArgumentException("Sentence must start with uppercase")
        else words += sentence.split(" ").length
      case _ => throw new Exception("Can only receive strings")
    }
  }

  class Supervisor extends Actor {
    override def receive: Receive = ???
  }
}
