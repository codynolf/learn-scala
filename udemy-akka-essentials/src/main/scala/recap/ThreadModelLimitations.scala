package recap

import scala.concurrent.ExecutionContext.Implicits.global

object ThreadModelLimitations extends App {
  // Daniels Rants
  /**
   * DR #1: OOP encapsulation is only valid in the single threaded model
   */

  class BankAccount(private var amount: Int) {
    override def toString: String = "" + amount

    def withdraw(money: Int) = this.synchronized {
      this.amount -= money
    }

    def deposit(money: Int) = this.synchronized {
      this.amount += money
    }

    def getAmount = amount
  }

  //  val account = new BankAccount(2000)
  //  for (_ <- 1 to 1000) {
  //    new Thread(() => account.withdraw(1)).start()
  //    new Thread(() => account.deposit(1)).start()
  //  }

  //  println(account.getAmount)

  // OOP encapsulation is broken in a multithreaded environment

  // synchronization! Locks to the rescue

  // deadlocks, livelocks

  /**
   * DR #2: delegating something to a thread is a pain
   */

  // you have a running thread and you want to pass a runnable to that thread

  var task: Runnable = null

  val runningThread: Thread = new Thread(() => {
    while (true) {
      while (task == null) {
        runningThread.synchronized {
          println("[background] waiting for a task...")
          runningThread.wait()
        }
      }

      task.synchronized {
        println("[background] I have a task!")
        task.run()
        task = null
      }
    }
  })

  def delegateToBackgroundThread(r: Runnable) = {
    if (task == null) task = r
    runningThread.synchronized {
      runningThread.notify()
    }
  }

  runningThread.start()
  Thread.sleep(500)
  delegateToBackgroundThread(() => println(42))
  Thread.sleep(1000)
  delegateToBackgroundThread(() => println("this should run in the background"))

  /**
   * DR #3: tracing and dealing with errors in a multithreaded environment is a pain
   */

  // 1M numbers in between 10 threads
  
  val futures = (0 to 9).map(i => 100000 * i until 100000 * (i + 1))
    .map(range => scala.concurrent.Future {
      if (range.contains(546735)) throw new RuntimeException("invalid number")
      range.sum
    })

  val sumFuture = scala.concurrent.Future.sequence(futures)

  sumFuture.onComplete {
    case scala.util.Success(value) => println(s"[sumFuture] the sum of all the numbers is: $value")
    case scala.util.Failure(e) => println(s"[sumFuture] error: $e")
  }


}
