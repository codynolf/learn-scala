package actors

import akka.actor._


object ChangeActorBehavior extends App{

  object FussyKid {
    case object KidAccept
    case object KidReject
    val HAPPY = "happy"
    val SAD = "sad"
  }
  class FussyKid extends Actor {
    import FussyKid._
    import Mom._
    var state = HAPPY
    override def receive: Receive = {
      case Food(VEGETABLE) => state = SAD
      case Food(CHOCOLATE) => state = HAPPY
      case Ask(_) =>
        if(state == HAPPY) sender() ! KidAccept
        else sender() ! KidReject
    }

  }

  class StatelessFussyKid extends Actor {
      import FussyKid._
      import Mom._
      override def receive: Receive = happyReceive

      def happyReceive: Receive = {
        case Food(VEGETABLE) => context.become(sadReceive, false)
        case Food(CHOCOLATE) =>
        case Ask(_) => sender() ! KidAccept
      }

      def sadReceive: Receive = {
        case Food(VEGETABLE) => context.become(sadReceive, false)
        case Food(CHOCOLATE) => context.unbecome()
        case Ask(_) => sender() ! KidReject
      }
    }

  object Mom {
    case class MomStart(kidREf: ActorRef)
    case class Food(food: String)
    case class Ask(message: String)

    val VEGETABLE = "vegetable"
    val CHOCOLATE = "chocolate"
  }

  import Mom._
  class Mom extends Actor {
    import FussyKid._
    
    override def receive: Receive = {
      case MomStart(kidREf) =>
        kidREf ! Food(VEGETABLE)
        kidREf ! Ask("Do you want to play?")
      case KidReject => println("Mom: Nope.")
      case KidAccept => println("Mom: He is happy.")
    }
  }

  val system = ActorSystem("changeActorBehavior")
  val kid = system.actorOf(Props[FussyKid](), "fussyKid")
  val statelessKid = system.actorOf(Props[StatelessFussyKid](), "statelessFussyKid")
  val mom = system.actorOf(Props[Mom](), "mom")

  mom ! MomStart(kid)
  mom ! MomStart(statelessKid)

  
  /* 

  Context.become
    Food(VEGETABLE) => sad (stack.push(sad)
    Food(CHOCOLATE) => happy

    Stack:
    1. happyReceive
    2. sadReceive
    3. happyReceive

   */

   /* 
   Context.unbecome
    Food(veg)
    Food(veg)
    Food(chocolate)

    stack:
      - sadReceive (then it gets popped with unbecome)
      - sadReceive
      - happyReceive
   
   */

}
