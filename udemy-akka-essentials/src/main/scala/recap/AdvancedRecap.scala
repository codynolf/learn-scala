package recap

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object AdvancedRecap extends App {
  
  // partial functions
  val partialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 65
    case 5 => 999
  }

  val pf = (x: Int) => x match {
    case 1 => 42
    case 2 => 65
    case 5 => 999
  }

  val function: (Int => Int) = partialFunction

  val modifiedList = List(1, 2, 3).map {
    case 1 => 42
    case _ => 0
  }

  // lifting
  val lifted = partialFunction.lift // total function Int => Option[Int]
  println(lifted(2)) // Some(65)
  println(lifted(5000)) // None

  // orElse
  val pfChain = partialFunction.orElse[Int, Int] {
    case 60 => 9000
  }

  println(pfChain(5)) // 999
  println(pfChain(60)) // 9000
  println(pfChain(457)) // match error

  // type aliases
  // actors in akka use this type alias.
  type ReceiveFunction = PartialFunction[Any, Unit]

  def receive: ReceiveFunction = {
    case 1 => println("Hello")
    case _ => println("confused")
  }

  // implicits
  implicit val timeout: Int = 3000
  def setTimeout(f: () => Unit)(implicit timeout: Int) = f()

  setTimeout(() => println("timeout")) // extra parameter list is not needed.

  // implicit conversions
  // 1. implicit defs
  case class Person(name: String) {
    def greet = s"Hi, my name is $name"
  }

  implicit def fromStringToPerson(name: String): Person = Person(name)

  println("Peter".greet) // fromStringToPerson("Peter").greet

  // 2. implicit classes
  implicit class Dog(name: String) {
    def bark = println("bark!")
  }

  "Lassie".bark
  // new Dog("Lassie").bark automatically done by the compiler.

  // organize
  implicit val inverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
  List(1, 2, 3).sorted // List(3, 2, 1)

  // imported scope
  val future = Future {
    println("hello, future")
  }

  // companion objects of the types included in the call
  object Person {
    implicit val personOrdering: Ordering[Person] = Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)
  }

  List(Person("Bob"), Person("Alice")).sorted // List(Person("Alice"), Person("Bob"))


}
