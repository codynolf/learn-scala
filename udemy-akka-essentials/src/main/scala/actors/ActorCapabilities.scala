package actors

import akka.actor.{Actor,ActorSystem,Props, ActorRef}
import akka.actor.AbstractActor.Receive

object ActorCapabilities extends App{
  class SimpleActor extends Actor{
    override def receive: Receive = {
      case "Hi" => sender() ! "Hello there!"
      case message: String => println(s"$self I have received: $message")
      case number: Int => println(s"[simple actor] I have received a number: $number")
      case SpecialMessage(contents) => println(s"[simple actor] I have received something special: $contents")
      case SendMessageToYourself(contents) => self ! contents
      case SayHiTo(ref) => ref ! "Hi"
      case ForwardMessage(contents, ref) => ref forward (contents + "s") // keep the original sender of the message
    }
  }

  val systems = ActorSystem("actorCapabilitiesDemo")
  val simpleActor = systems.actorOf(Props[SimpleActor](), "simpleActor")
  simpleActor ! "hello, actor"

  // 1 - messages can be of any type
  // a) messages must be immutable
  // b) messages must be serializable
  // in practice use case classes and case objects
  simpleActor ! 42

  case class SpecialMessage(contents: String)
  simpleActor ! SpecialMessage("some special content")

  // 2 - actors have information about their context and about themselves
  // context.self === `this` in OOP

  case class SendMessageToYourself(contents: String)
  simpleActor ! SendMessageToYourself("I am an actor and I am proud of it")

  // 3 - actors can reply to messages
  val alice = systems.actorOf(Props[SimpleActor](), "alice")
  val bob = systems.actorOf(Props[SimpleActor](), "bob")

  case class SayHiTo(ref: ActorRef)
  alice ! SayHiTo(bob)

  // 4 - dead letters
  alice ! "Hi"

  // 5 - forwarding messages
  // D -> A -> B
  // forwarding = sending a message with the original sender

  case class ForwardMessage(contents: String, ref: ActorRef)
  alice ! ForwardMessage("Hi", bob)
}

