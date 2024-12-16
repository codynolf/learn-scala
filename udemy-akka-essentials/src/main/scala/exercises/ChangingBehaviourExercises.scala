package exercises

import akka.actor._

object ChangingBehaviourExercises extends App {
  // 1. Recreate the Counter Actor with context.become and NO MUTABLE STATE
  // 2. Simplified voting system
  //  - 2 buttons: +1, -1
  //  - 5 votes and the total count is shown
  //  - the system should be able to vote in a different actor
  //  - the system should be able to print the vote status in a different actor
  //  - the system should be able to receive the votes in a different actor
  //  - the system should be able to vote in a different actor
  //  - the system should be able to print the vote status

  object Counter {
    case object Increment
    case object Decrement
    case object Print
  }
  import Counter._

  class Counter extends Actor {
  
    override def receive: Receive = countReceive(0)

    def countReceive(count: Int): Receive = {
      case Increment => context.become(countReceive(count + 1))
      case Decrement => context.become(countReceive(count - 1))
      case Print => println(s"[counter] My current count is $count")
    }

  }

  val counter = ActorSystem("ActorExercises").actorOf(Props[Counter](), "counter")

  counter ! Increment
  counter ! Increment
  counter ! Print
  counter ! Decrement
  counter ! Print

  object VotingSystem {
    case object VoteStatusRequest
    case class Vote(candidate: String)
    case class VoteStatus(candidate: String, count: Int)
  }

  class Citizen extends Actor {
    import VotingSystem._

    var votes: Map[String, Int] = Map()

    override def receive: Receive = {
      case Vote(candidate) =>
        val currentVotes = votes.getOrElse(candidate, 0)
        votes = votes + (candidate -> (currentVotes + 1))
      case VoteStatusRequest =>
        sender() ! votes
    }
  }

  class VoteAggregator extends Actor {
    import VotingSystem._

    var totalVotes: Map[String, Int] = Map()

    override def receive: Receive = {
      case VoteStatus(candidate, count) =>
        totalVotes = totalVotes + (candidate -> count)
      case VoteStatusRequest =>
        println(s"[aggregator] Total votes: $totalVotes")
    }
  }

  val votingSystem = ActorSystem("VotingSystem")
  val voteAggregator = votingSystem.actorOf(Props[VoteAggregator](), "voteAggregator")
  val votingActor = votingSystem.actorOf(Props[Citizen](), "votingActor")

}
