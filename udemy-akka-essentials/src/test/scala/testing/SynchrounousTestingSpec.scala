package testing

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.testkit.{TestActorRef, TestProbe}
import org.scalatest.wordspec.{AnyWordSpecLike}
import org.scalatest.BeforeAndAfterAll
import scala.xml.dtd.impl.Inclusion
import akka.testkit.CallingThreadDispatcher
import scala.concurrent.duration.Duration

class SynchrounousTestingSpec extends AnyWordSpecLike
  with BeforeAndAfterAll {
  
  import SynchrounousTestingSpec._

  implicit val system: ActorSystem = ActorSystem("SynchrounousTestingSpec")

  override def afterAll(): Unit = {
    system.terminate()
  }

  "A counter" should {
    "synchronously increase its counter" in {
      val counter = TestActorRef[Counter](Props[Counter]())
      counter ! Inc
      assert(counter.underlyingActor.count == 1)
    }

    "synchronously increase its counter at the call of the recieve funciton" in {
      val counter = TestActorRef[Counter](Props[Counter]())
      counter.receive(Inc)
      assert(counter.underlyingActor.count == 1)
    }

    "work on the calling thread dispatcher" in {
      val counter = TestActorRef[Counter](Props[Counter]().withDispatcher(CallingThreadDispatcher.Id))
      val probe = TestProbe()

      probe.send(counter, Read)
      probe.expectMsg(Duration.Zero, 0)
    }
  }
}

object SynchrounousTestingSpec {

  case object Inc
  case object Read

  class Counter extends Actor {
    var count = 0

    override def receive: Receive = {
      case Inc => count += 1
      case Read => sender() ! count
    }
  }
  
}
