package lectures.part3

import scala.concurrent.{Future, ExecutionContext}
import scala.util.{Failure, Success, Try}
import scodec.bits.Bases
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.Promise



object FuturesAndPromises extends App {
  import scala.concurrent.ExecutionContext.Implicits.global

  def calculateMeaningOfLife: Int = {
    Thread.sleep(2000)
    42
  }

  val aFuture = Future {
    calculateMeaningOfLife // calculates the meaning of life on ANOTHER thread
  } // (global) which is passed by the compiler

  aFuture.onComplete {
    case Success(meaningOfLife) => println(s"the meaning of life is $meaningOfLife")
    case Failure(e) => println(s"I have failed with $e")
  } // SOME thread

  //println(aFuture.value) // Option[Try[Int]]

  Thread.sleep(3000)
 // println("End of the program")
  println("=====================================")

  // client: fetch mark's profile
  val mark = SocialNetwork.fetchProfile("fb.id.1-zuck")
  mark.onComplete {
    case Success(markProfile) => {
      val bill = SocialNetwork.fetchBestFriend(markProfile)
      bill.onComplete {
        case Success(billProfile) => markProfile.poke(billProfile)
        case Failure(e) => println("help")
      }
    }
    case Failure(e) => println("help")
  }

  Thread.sleep(1000)
  
  // functional composition of futures
  // map, flatMap, filter
  val nameOnTheWall = mark.map(profile => profile.name)
  val marksBestFriend = mark.flatMap(profile => SocialNetwork.fetchBestFriend(profile))
  val zucksBestFriendRestricted = marksBestFriend.filter(profile => profile.name.startsWith("Z"))

  // for-comprehensions
  for {
    mark <- SocialNetwork.fetchProfile("fb.id.1-zuck")
    bill <- SocialNetwork.fetchBestFriend(mark)
  } mark.poke(bill)

  Thread.sleep(1000)

  // fallbacks
  val aProfileNoMatterWhat = SocialNetwork.fetchProfile("unknown id").recover {
    case e: Throwable => Profile("fb.id.3-dummy", "Forever Alone")
  }

  val aFetchedProfileNoMatterWhat = SocialNetwork.fetchProfile("unknown id").recoverWith {
    case e: Throwable => SocialNetwork.fetchProfile("fb.id.3-dummy")
  }

  // fallback to
  val fallbackResult = SocialNetwork.fetchProfile("unknown id").fallbackTo(SocialNetwork.fetchProfile("fb.id.3-dummy"))

}

case class Profile(id: String, name: String) {
  def poke(other: Profile): Unit = println(s"${this.name} poking ${other.name}")
}

object SocialNetwork {
  import scala.concurrent.ExecutionContext.Implicits.global

  // "database"
  val names = Map(
    "fb.id.1-zuck" -> "Mark",
    "fb.id.2-bill" -> "Bill",
    "fb.id.3-dummy" -> "Dummy"
  )

  val friends = Map(
    "fb.id.1-zuck" -> "fb.id.2-bill"
  )

  val random = new scala.util.Random

  // client: fetch profile from the "database"
  def fetchProfile(id: String): Future[Profile] = Future {
    // simulate fetching from the DB
    Thread.sleep(300)
    Profile(id, names(id))
  }

  // client: fetch friends from the "database"
  def fetchBestFriend(profile: Profile): Future[Profile] = Future {
    // simulate fetching from the DB
    Thread.sleep(400)
    val bestFriendId = friends(profile.id)
    Profile(bestFriendId, names(bestFriendId))
  }
}

case class User(name: String)
case class Transaction(sender: String, receiver: String, amount: Double, status: String)

object BankingApp extends App {
  import scala.concurrent.ExecutionContext.Implicits.global

  def fetchUser(name: String): Future[User] = Future {
    // simulate fetching from the DB
    Thread.sleep(500)
    User(name)
  }

  def createTransaction(user: User, merchantName: String, amount: Double): Future[Transaction] = Future {
    // simulate some process
    Thread.sleep(1000)
    Transaction(user.name, merchantName, amount, "success")
  }

  def purchase(username: String, item: String, merchantName: String, cost: Double): String = {
    // fetch the user from the DB
    // create a transaction
    // wait for the transaction to finish
    val transactionStatusFuture = for {
      user <- fetchUser(username)
      transaction <- createTransaction(user, merchantName, cost)
    } yield transaction.status

    Await.result(transactionStatusFuture, 2.seconds) // implicit conversion -> pimp my library

    transactionStatusFuture.value.get match {
      case Success(status) => s"Payment $status"
      case Failure(e) => s"Payment failed with $e"
    }
  }

  println(purchase("Daniel", "iPhone 12", "rock the jvm store", 3000))

  // promises
  val promise = Promise[Int]() // "controller" over a future
  val future = promise.future

  // thread 1 - "consumer"
  future.onComplete {
    case Success(r) => println("[consumer] I've received " + r)
    case Failure(exception) => println("[consumer] I've failed " + exception)
  }

  // thread 2 - "producer"
  val producer = new Thread(() => {
    println("[producer] crunching numbers...")
    Thread.sleep(500)
    // "fulfilling" the promise
    promise.success(42)
    println("[producer] done")
  })
}

object Exercises extends App{
  import scala.concurrent.ExecutionContext.Implicits.global
  /* 
  1. Fulfill a future IMMEDIATELY with a value
  2. inSequence(fa, fb) => return a future with the value of fa and fb
  3. first(fa, fb) => return a future with the first value of the two futures
  4. last(fa, fb) => return a future with the last value of the two futures
  5. retryUntil[T](action: () => Future[T], condition: T => Boolean): Future[T]
   */

  // 1
  def fulfillImmediately[T](value: T): Future[T] = Future(value)

  // 2
  def inSequence[A, B](fa: Future[A], fb: Future[B]): Future[B] = fa.flatMap(_ => fb)

  // 3
  def first[A](fa: Future[A], fb: Future[A]): Future[A] = {
    val promise = Promise[A]

    fa.onComplete(promise.tryComplete)
    fb.onComplete(promise.tryComplete)

    promise.future
  }

  // 4
  def last[A](fa: Future[A], fb: Future[A]): Future[A] = {
    val bothPromise = Promise[A]
    val lastPromise = Promise[A]
    val checkAndComplete = (result: Try[A]) =>
      if (!bothPromise.tryComplete(result))
        lastPromise.complete(result)

    fa.onComplete(checkAndComplete)
    fb.onComplete(checkAndComplete)

    lastPromise.future
  }

  // 5
  def retryUntil[T](action: () => Future[T], condition: T => Boolean): Future[T] =
    action()
      .filter(condition)
      .recoverWith {
        case _ => retryUntil(action, condition)
      }

  // test
  val random = new scala.util.Random
  val action = () => Future {
    Thread.sleep(100)
    val nextValue = random.nextInt(100)
    println(s"generated $nextValue")
    nextValue
  }

  retryUntil(action, (x: Int) => x < 10).foreach(result => println(s"settled at $result"))
}
