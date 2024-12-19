package faulttolerance

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Kill, PoisonPill, Props, Terminated}

object StartingStoppingActors extends App {
  val system = ActorSystem("StoppingActorsDemo")

  object Parent {
    case class StartChild(name: String)
    case class StopChild(name: String)
    case object Stop
  }
  class Parent extends Actor with ActorLogging {
    import Parent._
    override def receive: Receive = withChildren(Map())

    def withChildren(children: Map[String, ActorRef]): Receive = {
      case StartChild(name) =>
        log.info(s"Parent starting child with name $name")
        val child = context.actorOf(Props[Child](), name)
        context.become(withChildren(children + (name -> child)))
      case StopChild(name) =>
        log.info(s"Parent stopping child with name $name")
        val childOption = children.get(name)
        childOption.foreach(childRef => context.stop(childRef))
      case Stop =>
        log.info("Parent stopping itself")
        context.stop(self)
    }
  }

  class Child extends Actor with ActorLogging {
    override def receive: Receive = {
      case message => log.info(message.toString)
    }
  }

  import Parent._

  /**
    * method #1 - using context.stop
    */
  val parent = system.actorOf(Props[Parent](), "parent")

  parent ! StartChild("child1")
  val child1 = system.actorSelection("/user/parent/child1")
  child1 ! "Hi kid!"

  parent ! StopChild("child1")
  //for (_ <- 1 to 50) child1 ! "Are you still there?"

  parent ! StartChild("child2")
  val child2 = system.actorSelection("/user/parent/child2")
  child2 ! "Hi, second child!"

  parent ! Stop

  //for (_ <- 1 to 10) parent ! "Parent, are you still there?"  
  //for (_ <- 1 to 100) parent ! "Child2, are you still there?"  

  /**
    * method #2 - using special messages
    */

  val looseActor = system.actorOf(Props[Child](), "looseActor")
  looseActor ! "hello, loose actor"
  looseActor ! PoisonPill
  looseActor ! "loose actor, are you still there?"

  val abruptlyTerminatedActor = system.actorOf(Props[Child](), "abruptlyTerminatedActor")
  abruptlyTerminatedActor ! "you are about to be terminated"
  abruptlyTerminatedActor ! Kill


  /**
    * method #3 - death watch
    */

  class Watcher extends Actor with ActorLogging {
    import Parent._
    
    override def preStart(): Unit = {
      log.info("Watcher starting")
      context.watch(looseActor)
      context.watch(abruptlyTerminatedActor)
    }

    override def receive: Receive = {
      case Terminated(ref) =>
        log.info(s"the reference that I'm watching $ref has been stopped")
    }
  }

  val watcher = system.actorOf(Props[Watcher](), "watcher")
  abruptlyTerminatedActor ! "you are about to be terminated"
  abruptlyTerminatedActor ! Kill
}
