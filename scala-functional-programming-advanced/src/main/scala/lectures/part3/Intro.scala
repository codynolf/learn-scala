package lectures.part3

import java.util.concurrent.Executors

object Intro extends App {
  // JVM Threads
  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("Running in parallel")
  })

  aThread.start() // gives the signal to the JVM to start a JVM thread
  // creates a JVM thread => OS thread
  aThread.join() // blocks until aThread finishes running

  val threadHello = new Thread(() => (1 to 5).foreach(_ => println("Hello")))
  val threadGoodbye = new Thread(() => (1 to 5).foreach(_ => println("Goodbye")))

  threadHello.start()
  threadGoodbye.start()
  // different runs produce different results!

  // executors
  val pool = Executors.newFixedThreadPool(10)

  pool.execute(() => println("Something in the thread pool"))

  pool.execute(() => {
    Thread.sleep(1700)
    println("Done after 1 second")
  })

  pool.execute(() => {
    Thread.sleep(1800)
    println("Almost done")
    Thread.sleep(2000)
    println("Done after 2 seconds")
  })

  // pool.shutdown()
  // pool.execute(() => println("Should not appear")) // throws an exception in the calling thread

  // pool.shutdownNow()
}
