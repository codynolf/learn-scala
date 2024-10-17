package lectures.part3

import scala.concurrent.Future
import scala.util.{Failure, Success}

// important for futures
import scala.concurrent.ExecutionContext.Implicits.global

object FuturesAndPromises extends App {
  
  def calculateMeaningOfLife: Int = {
    Thread.sleep(2000)
    42
  }

  val aFuture = Future {
    calculateMeaningOfLife // calculates the meaning of life on ANOTHER thread
  } // (global) which is passed by the compiler

  aFuture.onComplete(t => t match {
    case Success(meaningOfLife) => println(s"the meaning of life is $meaningOfLife")
    case Failure(e) => println(s"I have failed with $e")
  }) // SOME thread

  println(aFuture.value) // Option[Try[Int]]

  Thread.sleep(3000)
}
