package lectures.part3

import lectures.part1.Recap.x
import lectures.part1.AdvancedPatternMatching.d

object Problems {
  
  def runInParallel: Unit = {
    var x = 0
    val thread1 = new Thread(() => {
      x = 1
    })
    val thread2 = new Thread(() => {
      x = 2
    })
    thread1.start()
    thread2.start()
    println(x)
  }

  case class BankAccount(var amount: Int)

  def buy(account: BankAccount, thing: String, price: Int): Unit = {
    account.amount -= price
  }

  def demoBankingProblem() ={
    (1 to 10000).foreach { _ => 
      val account = BankAccount(50000)
      val thread1 = new Thread(() => buy(account, "shoes", 3000))
      val thread2 = new Thread(() => buy(account, "iphone12", 4000))
      thread1.start()
      thread2.start()
      thread1.join()
      thread2.join()
      if (account.amount != 43000) println("AHA: " + account.amount)
    }
  }

  /**
    * Exercise
    *  1 - create inception threads
    *    Thread1 -> Thread2 -> Thread3
    *   println("hello from thread #3")
    * 
    *  2 - whats the max/min value of x?
    *  
    * 3 - sleep fallacy, whats the value of message
    */

  def demoSleepFallacy = {
    var message = ""
    val awesomeThread = new Thread(() => {
      Thread.sleep(1000)
      message = "Scala is awesome"
    })
    message = "Scala sucks"
    awesomeThread.start()
    Thread.sleep(2000)
    println(message)
  }

  def minMaxX() = {
    var x = 0
    val threads = (1 to 100).map { _ =>
      new Thread(() => x += 1)
    }
    threads.foreach(_.start())
  }


  def inceptionThreads(depth: Int, max: Int): Unit = {
    println(s"depth: $depth, max: $max")
    if (depth < max) {
      val newThread = new Thread(() => inceptionThreads(depth, max))
      newThread.start()
    }
  }

  def inceptionThreads2(maxThreads: Int, i: Int = 1): Thread = new Thread(() => {
    if (i < maxThreads) {
      val newThread = inceptionThreads2(maxThreads, i + 1)
      newThread.start()
      newThread.join()
    }
    println(s"Hello from thread $i")
  })

  def main(args: Array[String]): Unit = {
    val inceptionThread = inceptionThreads2(50)
    inceptionThread.start()
    inceptionThread.join()
  }
}
