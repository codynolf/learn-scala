package testing

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import org.scalatest.wordspec.{AnyWordSpecLike}
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfterAll
import scala.concurrent.duration._

class TimedAssertionsSpec extends TestKit(ActorSystem("TestPobeSpec"))
  with ImplicitSender
  with AnyWordSpecLike
  with Matchers
  with BeforeAndAfterAll {

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  import TimedAssertionsSpec._

  "A worker actor" should {
    val workerActor = system.actorOf(Props[WorkerActor]())

    "reply with the meaning of life in a timely manner" in {
      within(500.millis, 1.second) {
        workerActor ! "work"
        expectMsg(WorkResult(42))
      }
    }

    "reply with valid work at a reasonable cadence" in {
      within(1.second) {
        workerActor ! "workSequence"
        val results: Seq[Int] = receiveWhile[Int](max = 2.seconds, idle = 500.millis, messages = 10) {
          case WorkResult(result) => result
        }
        assert(results.sum > 5)
      }
    }

    "reply to a test probe in a timely manner" in {
      within(1.second) {
        val probe = TestProbe()
        probe.send(workerActor, "work")
        probe.expectMsg(WorkResult(42))
      }
    }
  }
  
}

object TimedAssertionsSpec {

  case class WorkResult(result: Int)

  class WorkerActor extends Actor {
    override def receive: Receive = {
      case "work" =>
        Thread.sleep(500)
        sender() ! WorkResult(42)
      case "workSequence" =>
        val r = new scala.util.Random()
        for (i <- 1 to 10) {
          Thread.sleep(r.nextInt(50))
          println(s"Sending $i")
          sender() ! WorkResult(1)
        }
    }
  }

}
