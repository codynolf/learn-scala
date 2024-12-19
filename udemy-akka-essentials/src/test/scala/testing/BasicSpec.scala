package testing

import akka.actor.{Actor, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.wordspec.{AnyWordSpecLike}
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfterAll

class BasicSpec extends TestKit(ActorSystem("BasicSpec"))
  with ImplicitSender
  with Matchers
  with AnyWordSpecLike
  with BeforeAndAfterAll {

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  import BasicSpec._
  "A simple actor" should {
    "send back the same message" in {
      val echoActor = system.actorOf(Props[SimpleActor]())
      val message = "hello test"
      echoActor ! message
      expectMsg(message)
    }
  }

  "A blackhole actor" should {
    "send back some message" in {
      val blackhole = system.actorOf(Props[Blackhole]())
      val message = "hello test"
      blackhole ! message
      expectNoMessage()
    }
  }

  // message assertions
  "A lab test actor" should {
    val labTestActor = system.actorOf(Props[LabTestActor]())
    "send back the same message in upper case" in {
      val message = "I love Akka"
      labTestActor ! message
      val reply = expectMsgType[String]
      reply should equal("I LOVE AKKA")
    }

    "reply to a greeting" in {
      labTestActor ! "greeting"
      expectMsgAnyOf("hi", "hello")
    }

    "reply with favorite tech" in {
      labTestActor ! "favoriteTech"
      expectMsgAllOf("Scala", "Akka")
    }

    "reply with cool tech in a different way" in {
      labTestActor ! "favoriteTech"
      val messages = receiveN(2) // Seq[Any]
      // free to do more complicated assertions
      messages.size should equal(2)
    }

    "reply with cool tech in a fancy way" in {
      labTestActor ! "favoriteTech"
      expectMsgPF() {
        case "Scala" => 
        case "Akka" => 
      }
    }
  }

}

object BasicSpec {
  class SimpleActor extends Actor {
    override def receive: Receive ={
      case message => sender() ! message
    }
  }

  class Blackhole extends Actor {
    override def receive: Receive = Actor.emptyBehavior
  }

  class LabTestActor extends Actor {
    val random = new scala.util.Random()
    override def receive: Receive = {
      case "greeting" => if(random.nextBoolean()) sender() ! "hi" else sender() ! "hello"
      case "favoriteTech" => 
        sender() ! "Scala"
        sender() ! "Akka"
      case message: String => sender() ! message.toUpperCase()
    }
  }
}