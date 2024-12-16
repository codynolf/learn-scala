package exercises

import exercises.ActorExercises.Counter._
import akka.actor.{Actor,ActorSystem,Props, ActorRef}
import akka.actor.AbstractActor.Receive

object ActorExercises extends App {
  /**
   * Exercises
   * 1. a Counter actor
   *  - Increment
   * - Decrement
   * - Print
   * 2. a Bank account as an actor
   * - Deposit an amount
   * - Withdraw an amount
   * - Statement
   * replies with
   * - Success/Failure
   * interact with some other kind of actor
   */

  // 1. Counter actor
  object Counter {
    case object Increment
    case object Decrement
    case object Print
  }

  class Counter extends Actor{
    var count = 0
    override def receive: Receive = {
      case Increment => count += 1
      case Decrement => count -= 1
      case Print => println(s"[counter] My current count is $count")
    }
  }

  val systems = ActorSystem("ActorExercises")

  val counter = systems.actorOf(Props[Counter](), "counter")
  counter ! Increment
  counter ! Increment
  counter ! Print
  counter ! Decrement
  counter ! Print
  

  // 2. Bank account as an actor
  object BankAccount {
    case class Deposit(amount: Int)
    case class Withdraw(amount: Int)
    case object Statement
    case class Transaction()
  }

  class BankAccount extends Actor {
    import BankAccount._
    var balance = 0
    override def receive: Receive = {
      case Deposit(amount) =>
        if (amount < 0) sender() ! Transaction
        else {
          balance += amount
          sender() ! Transaction
        }
      case Withdraw(amount) =>
        if (amount < 0) sender() ! Transaction
        else if (balance < amount) sender() ! Transaction
        else {
          balance -= amount
          sender() ! Transaction
        }
      case Statement => sender() ! s"[bank account] My balance is $balance"
    }
  }

  object Person {
    case class LiveTheLife(account: ActorRef)
  }

  class Person extends Actor {
    import BankAccount._
    import Person._
    override def receive: Receive = {
      case LiveTheLife(account) =>
        account ! Deposit(10000)
        account ! Withdraw(90000)
        account ! Withdraw(500)
        account ! Statement
      case Transaction => println("[person] Transaction completed")
    }
  }

  val account = systems.actorOf(Props[BankAccount](), "bankAccount")
  val person = systems.actorOf(Props[Person](), "billionaire")

  import Person._
  person ! LiveTheLife(account)

}
