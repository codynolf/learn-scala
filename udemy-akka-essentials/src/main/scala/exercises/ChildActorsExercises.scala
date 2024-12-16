package exercises

import akka.actor._

object ChildActorsExercises extends App {
  
  // Distributed Word counting

  /**
      * 
      * create WordCounterMaster
      * send Initialize(10) to WordCounterMaster
      * send "Akka is awesome" to WordCounterMaster
      * WordCounterMaster will send a WordCountTask("Akka is awesome") to one of its children
      * the child will reply with a WordCountReply(3) to the master
      * master replies with 3
      * 
      * Requester -> Master -> Worker
    *         r   <- m       <-

    * Round robin logic
    * 
      */

  object WordCounterMaster{
    case class Initialize(nChildren: Int)
    case class WordCountTask(id: Int, text: String)
    case class WordCountReply(id: Int, count: Int)
  }
  class WordCounterMaster extends Actor{
    import WordCounterMaster._
    def receive: Receive = {
      case Initialize(nChildren) => 
        println("[master] initializing...")
        val childRefs = for (i <- 1 to nChildren) yield context.actorOf(Props[WordCounterWorker](), s"worker_$i")
        context.become(withChildren(childRefs, 0, 0, Map()))

    }

    def withChildren(children: Seq[ActorRef], currentChildIndex: Int, currentTaskId: Int, requestMap: Map[Int, ActorRef]): Receive = {
      case text: String =>
        println(s"[master] I have received: $text - I will send it to child $currentChildIndex")
        val originalSender = sender()
        val task = WordCountTask(currentTaskId, text)
        val childRef = children(currentChildIndex)
        childRef ! task
        val nextChildIndex = (currentChildIndex + 1) % children.length
        val newTaskId = currentTaskId + 1
        val newRequestMap = requestMap + (currentTaskId -> originalSender)
        context.become(withChildren(children, nextChildIndex, newTaskId, newRequestMap))
      case WordCountReply(id, count) => 
        println(s"[master] I have received a reply for task $id with $count")
        val originalSender = requestMap(id)
        originalSender ! count
        context.become(withChildren(children, currentChildIndex, currentTaskId, requestMap - id))
}
  }

  class WordCounterWorker extends Actor{
    import WordCounterMaster._
    def receive: Receive = {
      case WordCountTask(id, text) => 
        println(s"${self.path} I have received a task $id with $text")
        sender() ! WordCountReply(id, text.split(" ").length)
    }
  }

  class TestActor extends Actor{
    import WordCounterMaster._
    def receive: Receive = {
      case "go" => 
        val master = context.actorOf(Props[WordCounterMaster](), "master")
        master ! Initialize(3)
        val texts = List("I love Akka", "Scala is super dope", "yes", "me too", "my text I am learning but really slow duh")
        texts.foreach(text => master ! text)      
      case count: Int => println(s"[test actor] I have received a reply: $count")
    }
  }

  val system = ActorSystem("roundRobinWordCount")
  val testActor = system.actorOf(Props[TestActor](), "testActor")
  testActor ! "go"


}
