package lectures.part1

import scala.annotation.tailrec

object Recap extends App{
  val aCondition: Boolean = false
  val aConditionedVal = if(aCondition) 42 else 65
  // instructions vs expressions

  // compiler infers types for us
  val aCodeBlock = {
    if(aCondition) 74
    else 56
  }

  // Unit = void
  val theUnit = println("Hello, Scala")

  // functions
  def aFunction(x: Int): Int = x + 1

  // recursion: stack and tail
  @tailrec
  def factorial(n: Int, acc: Int): Int = {
    if(n <= 0) acc
    else factorial(n - 1, n * acc)
  }

  // object-oriented programming
  class Animal
  class Dog extends Animal
  val aDog: Animal = new Dog // subtyping polymorphism

  // abstract data types
  trait Carnivore {
    def eat(a: Animal): Unit
  }

  class Crocodile extends Animal with Carnivore {
    override def eat(a: Animal): Unit = println("Crunch!")
  }

  // method notations
  val aCroc = new Crocodile
  aCroc.eat(aDog)
  //aCroc eat aDog // natural language

  // anonymous classes
  val aCarnivore = new Carnivore {
    override def eat(a: Animal): Unit = println("Roar!")
  }

  println(aCarnivore) // lectures.part1.Recap$$anon$1@3b6eb2ec

  // generics
  abstract class MyList[+A] // variance and variance problems in THIS course
  // singletons and companions
  object MyList

  // case classes, lightweight data structures with some boilerplate
  case class Person(name: String, age: Int)

  // exceptions and try/catch/finally
  val throwsException = throw new RuntimeException // Nothing
  val aPotentialFailure = try {
    throw new RuntimeException
  } catch {
    case e: Exception => "I caught an exception!"
  } finally {
    println("Some logs")
  }

  // packaging and imports
  // do not care too much about this in this course

  // functional programming
  // functions are actually instances of classes with apply method
  val incrementer = new Function1[Int, Int] {
    override def apply(v1: Int): Int = v1 + 1
  }

  incrementer(1)

  val anonymousIncrementer = (x: Int) => x + 1
  List(1,2,3).map(anonymousIncrementer) // HOF

  // map, flatMap, filter

  // for-comprehensions
  val pairs = for { // chains of map, flatMap
    num <- List(1,2,3) // if condition (filters)
    char <- List('a', 'b', 'c')
  } yield num + "-" + char

  // Scala collections: Seqs, Arrays, Lists, Vectors, Maps, Tuples
  val aMap = Map(
    "Daniel" -> 789,
    "Jess" -> 555
  )

  // "collections": Options, Try
  val anOption = Some(2)

  // pattern matching
  val x = 2
  val order = x match {
    case 1 => "first"
    case 2 => "second"
    case _ => x + "th"
  }

  val bob = Person("Bob", 22)
  val greeting = bob match {
    case Person(n, a) => s"Hi, my name is $n and I am $a years old."
    case null => "I don't know my name."
  }

  // all the patterns
  
}
