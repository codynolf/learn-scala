package faulttolerance

import akka.actor.{Actor, ActorLogging, ActorSystem, PoisonPill, Props}

object ActorLifecycle extends App {
  case object StartChild

  class LifecycleActor extends Actor with ActorLogging {
    override def preStart(): Unit = log.info("I am starting")
    override def postStop(): Unit = log.info("I have stopped")

    override def receive: Receive = {
      case StartChild =>
        context.actorOf(Props[LifecycleActor](), "child")
    }
  }

  val system = akka.actor.ActorSystem("LifecycleActor")
  //val parent = system.actorOf(Props[LifecycleActor](), "parent")

  //parent ! StartChild
  //parent ! PoisonPill

  /**
    * restart
    */

  case object Fail
  case object FailChild
  case object Check
  case object CheckChild

  class Parent extends Actor with ActorLogging {
    private val child = context.actorOf(Props[Child](), "child")

    override def receive: Receive = {
      case FailChild =>
        log.warning("Parent fails")
        child ! Fail
      case CheckChild =>
        child ! Check
    }
  }

  class Child extends Actor with ActorLogging {
    override def preStart(): Unit = log.info("I am starting")
    override def postStop(): Unit = log.info("I have stopped")
    override def preRestart(reason: Throwable, message: Option[Any]): Unit = log.info("I am restarting")
    override def postRestart(reason: Throwable): Unit = log.info("I have restarted")

    override def receive: Receive = {
      case Fail =>
        log.warning("I am failing")
        throw new RuntimeException("I failed")
      case Check => 
        log.info("Alive and kicking")
    }
  }

  val parent = system.actorOf(Props[Parent](), "parent")
  parent ! FailChild
  parent ! CheckChild
}
