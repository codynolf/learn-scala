package lectures

import java.util.Random

object Sequences extends App {
  // Seqences
  // well defined orded
  // can be indexed
  
  val seq1 = Seq(1,2,3,4)
  println(seq1)
  println(seq1.reverse)
  println(seq1(2))
  val seq2 = seq1 ++ Seq(7,5,6)
  println(seq2)
  println(seq2.sorted)

  // Range
  val aRange: Seq[Int] = 1 to 10
  //aRange.foreach(println)

  // List
  val list = List(1,2,3,4)
  val prepend = 42 +: list
  val append = list :+ 42

  // arrays
  val numbers = Array(1,2,3,4)
  val threeElements = Array.ofDim[String](3) // empty
  //println(threeElements)

  // mutation
  numbers(2) = 0
  println(numbers.mkString(" "))

  // arrays and seq
  val numbersSeq: Seq[Int] = numbers // implicit conversion
  println(numbersSeq) // ArraySeq

  // Vector
  val vectInt: Vector[Int] = Vector(1,2,3)
  
  // vector vs list
  val maxRuns = 10000
  val maxCapacity = 1000000
  def getWriteTime(collection: Seq[Int]): Double = {
    val r = new Random

    val times = for{
      it <- 1 to maxRuns
    } yield {
      val currentTime = System.nanoTime()
      // operation
      collection.updated(r.nextInt(maxCapacity), r.nextInt())
      System.nanoTime() - currentTime
    }

    times.sum * 1.0 / maxRuns
  }

  val numbersList = (1 to maxCapacity).toList
  val numbersVector = (1 to maxCapacity).toVector

  println(getWriteTime(numbersList))
  println(getWriteTime(numbersVector))
}
