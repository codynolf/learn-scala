package actors

import akka.actor.{Actor, ActorSystem, Props}
import scala.concurrent.java8.FuturesConvertersImpl.P

object ActorsIntro extends App{
  // part1 - actor systems
  val actorSystem = ActorSystem("firstActorSystem")
  println(actorSystem.name)

  // part2 - create actors
  // word count actor

  class WordCountActor extends Actor {
    // internal data
    var totalWords = 0
    def receive: PartialFunction[Any, Unit] = {
      case message: String =>
        println(s"[word counter] I have received: $message")
        val wordsInMessage = message.split(" ").length
        println(s"[word counter] I have $wordsInMessage words in this message")
        totalWords += wordsInMessage
        println(s"[word counter] I have received $totalWords words")
      case msg => println(s"[word counter] I cannot understand ${msg.toString}")
    }
  }

  // part3 - instantiate our actor
  val wordCounter = actorSystem.actorOf(Props[WordCountActor](), "wordCounter")

  // part4 - communicate
  wordCounter ! "I am learning Akka and it's pretty damn cool!"
  wordCounter ! "A different message"

  object Person {
    def props(name: String) = Props(new Person(name))
  }
  class Person(name: String) extends Actor {
    override def receive: Receive = {
      case "hi" => println(s"Hi, my name is $name")
      case _ =>
    }
  }

  val person = actorSystem.actorOf(Props(new Person("Bob")))
  val person2 = actorSystem.actorOf(Person.props("Alice"))

}
