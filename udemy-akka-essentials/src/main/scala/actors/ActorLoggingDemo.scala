package actors

import akka.actor._
import akka.event.Logging

object ActorLoggingDemo extends App {
  
  class SimpleActorWithExplicitLogger extends Actor {
    val logger = Logging(context.system, this)

    override def receive: Receive = {
      case message => logger.info(message.toString)
    }
  }

  val system = ActorSystem("LoggingDemo")
  val actor = system.actorOf(Props[SimpleActorWithExplicitLogger](), "logDemoActor")

  actor ! "Logging a simple message"

  // ActorLogging
  class ActorWithLogging extends Actor with ActorLogging {
    override def receive: Receive = {
      case (a, b) => log.info("Two things: {} and {}", a, b)
      case message => log.info(message.toString)
    }
  }

  val actorWithLogging = system.actorOf(Props[ActorWithLogging](), "actorWithLogging")
  actorWithLogging ! "Logging a simple message with ActorLogging"

  actorWithLogging ! (42, 65)
}
