package lectures.part4

import lectures.part1.AdvancedPatternMatching.Person

object Givens extends App {
  
  val aList = List(2, 4, 3, 1)
  val anOrderedList = aList.sorted

 /*  object Implicits {
    implicit val reverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
  } */

  // Scala 3 style
  object  Givens {
    given descendingOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
  }
  
  object GivenAnonymousClassNaive {
    given descendingOrdering_v2: Ordering[Int] = new Ordering[Int] {
      def compare(x: Int, y: Int): Int = y - x
    }
  }

  object GivenWith {
    given descendingOrdering_v3: Ordering[Int] with
      def compare(x: Int, y: Int): Int = y - x
  }

  import GivenWith._ // does not import givens
  import GivenWith.given // imports ALL givens
  import GivenWith.descendingOrdering_v3 // imports a specific given

  // implicit argument
  def extremes[T](list: List[T])(implicit ordering: Ordering[T]): (T, T) = {
    val sortedList = list.sorted
    (sortedList.head, sortedList.last)
  }

  // using replaces implicit for this meaning of implicit
  def extremes_v2[T: Ordering](using list: List[T]): (T, T) = {
    val sortedList = list.sorted
    (sortedList.head, sortedList.last)
  }

  // implicit defs (synthesizes new implicit values)
  trait Combinator[A] {
    def combine(x: A, y: A): A
  }
  // List(1, 2, 3) < List(4, 5, 6)

  implicit def listOrdering[A](implicit simpleOrdering: Ordering[A], combinator: Combinator[A]): Ordering[List[A]] = 
    new Ordering[List[A]] {
      def compare(x: List[A], y: List[A]): Int = {
        val sumX = x.reduce(combinator.combine)
        val sumY = y.reduce(combinator.combine)
        simpleOrdering.compare(sumX, sumY)
      }
  }

  // equivalent to the above in scala 3
  given listOrdering_v2[A](using simpleOrdering: Ordering[A], combinator: Combinator[A]): Ordering[List[A]] with
    def compare(x: List[A], y: List[A]): Int = {
      val sumX = x.reduce(combinator.combine)
      val sumY = y.reduce(combinator.combine)
      simpleOrdering.compare(sumX, sumY)
    }

  // implicit conversions
  case class Person(name: String) {
    def greet: String = s"Hi, my name is $name"
  }

  implicit def stringToPerson(name: String): Person = Person(name)

  println("Peter".greet)

  // given instances
  import scala.language.implicitConversions
  given stringToPerson_v2: Conversion[String, Person] with
    def apply(name: String): Person = Person(name)

  println(aList.sorted)
}
