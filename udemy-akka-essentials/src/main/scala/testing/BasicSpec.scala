package testing

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll}
import org.scalatest.wordspec.AnyWordSpecLike

class  BasicSpec extends TestKit(ActorSystem("BasicSpec")) 
  with ImplicitSender 
  with AnyWordSpecLike 
  with BeforeAndAfterAll {

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "The thing being tested" should {
    // Test Suite
    "do this" in {
      // testing scenario
      true === true
    }
  }

  object BasicSpec {
    
  }
  
}
