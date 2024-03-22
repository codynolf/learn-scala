package lectures

import scala.util.{Try, Failure, Success}
import lectures.Options.Connection.random
import java.util.Random
import lectures.Options.connection

object HandlingFailure extends App{
  val success = Success(3)
  val fail = Failure(new RuntimeException("fail"))

  println(success)
  println(fail)

  def unsafeMethod(): String = throw new RuntimeException("no string for you")

  val potentialFailure = Try(unsafeMethod())
  println(potentialFailure)

  val potentialFailure2 = Try {
    unsafeMethod()
  }

  println(potentialFailure.isSuccess)

  // orElse
  def backupMethod(): String = "A valid result"
  val fallbackTry = Try(unsafeMethod()).orElse(Try(backupMethod()))
  println(fallbackTry)
  
  // IF you design the api, and you know code could throw do the following
  def betterUnsafeMethod(): Try[String] = Failure(new RuntimeException)
  def betterBackupMethod(): Try[String] = Success("A valid result")
  val betterfallback = betterUnsafeMethod() orElse betterBackupMethod()

  // map, flatMap, filter
  println(success.map(_ * 2))
  println(success.flatMap(x => Success(x * 3)))
  println(success.filter(_ > 10))


  /* 
    Exercise
   */

   val host = "localhost"
   val port = "8080"

   def renderHtml(page: String) = println(page)

   class Connection {
    def get(url: String): String = {
      val random = new Random(System.nanoTime())
      if(random.nextBoolean()) "html"
      else throw new RuntimeException
    }
   }

   object HttpService {
    val random = new Random(System.nanoTime())

    def apply(host: String, port: String): Connection = {
      if(random.nextBoolean()) new Connection
      else throw new RuntimeException
    }    
   }

   val html = for {
    connection <- Try(HttpService.apply(host, port))
   } yield Try(connection.get(host))

   println(html)
}