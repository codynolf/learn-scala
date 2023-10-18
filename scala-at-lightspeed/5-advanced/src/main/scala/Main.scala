import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Main extends App {
  // lazy evaulation
  lazy val two = 2;
  lazy val sideeffect = {
    println("I am lazy")
    44
  }

  val lazyValTest = sideeffect + 1;

  //pseudo-collection: options, try
  def methodCanReturnNull(): String = "Hello, scala"
  val option = Option(methodCanReturnNull())
  // option is a "collection" which contains at most one element
  // Some(None) or Some(value)

  val x = option match{
    case Some(value) => s"I found $value"
    case None => "I found nothing"
  }

  println(x)

  // Future, async programming
  val future = Future {
    println("I am running in parallel")
    Thread.sleep(2222)
    println("I am done")
    42
  }

  val f = future

  Thread.sleep(3333)
}