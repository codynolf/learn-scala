package actors

import akka.actor._

object ChildActors extends App {
  // Actors can create other actors

  object Parent{
    case class CreateChild(name: String)
    case class TellChild(message: String)
  }
  class Parent extends Actor{
    import Parent._
    override def receive: Receive = {
      case CreateChild(name) =>
        println(s"${self.path} creating child")
        val childRef = context.actorOf(Props[Child](), name)
        context.become(withChild(childRef))
    }

    def withChild(childRef: ActorRef): Receive = {
      case TellChild(message) => childRef ! message
    }
  }

  class Child extends Actor{
    override def receive: Receive = {
      case message => println(s"${self.path} I got: $message")
    }
  }

  import Parent._
  val system = ActorSystem("ParentChildDemo")
  val parent = system.actorOf(Props[Parent](), "parent")

  parent ! CreateChild("child")
  parent ! TellChild("hey kid!")
  Thread.sleep(100)
  /* 
    Guardian actors (top-level)
    - /system = system guardian
    - /user = user-level guardian
    - / = root guardian
   */

  // Actor selection
  val childSelection = system.actorSelection("/user/parent/child")
  childSelection ! "I found you!"

  /* 
    Danger!
    NEVER PASS MUTABLE ACTOR STATE, OR THE `THIS` REFERENCE, TO CHILD ACTORS.
    NEVER IN YOUR LIFE.
   */

  object NaiveBankAccount {
    case class Deposit(amount: Int)
    case class Withdraw(amount: Int)
    case object InitializeAccount
  }

  class NaiveBankAccount extends Actor {
    import NaiveBankAccount._
    var amount = 0
    override def receive: Receive = {
      case InitializeAccount => 
        val creditCardRef = context.actorOf(Props[CreditCard](), "card")
        creditCardRef ! CreditCard.AttachToAccount(this) // !!
      case Deposit(funds) => deposit(funds)
      case Withdraw(funds) => withdraw(funds)
    }

    def deposit(funds: Int): Unit = {
      println(s"${self.path} depositing $funds on top of $amount")
      amount += funds
    }

    def withdraw(funds: Int): Unit = {
      println(s"${self.path} withdrawing $funds from $amount")
      amount -= funds
    }
  }

  object CreditCard {
    case class AttachToAccount(bankAccount: NaiveBankAccount) // This should be a ActorRef
    case object CheckStatus
  }
  class CreditCard extends Actor {

    import CreditCard._
    override def receive: Receive = {
      case AttachToAccount(account) => context.become(attachTo(account))
    }

    def attachTo(account: NaiveBankAccount): Receive = {
      case CheckStatus => 
        println(s"${self.path} your message has been processed.")
        // benign
        account.withdraw(1) // because I can
    }
  }

  import NaiveBankAccount._
  import CreditCard._
  val bankAccountRef = system.actorOf(Props[NaiveBankAccount](), "account")
  bankAccountRef ! InitializeAccount
  bankAccountRef ! Deposit(100)

  Thread.sleep(500)
  val ccSelection = system.actorSelection("/user/account/card")
  ccSelection ! CheckStatus

}
