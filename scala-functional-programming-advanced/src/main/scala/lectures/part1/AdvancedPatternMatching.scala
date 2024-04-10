package lectures.part1

object AdvancedPatternMatching extends App {
  
  val numbers = List(1)
  val description = numbers match {
    case head :: Nil => s"The only element is $head"
    case _ =>
  }

  println(description)

  /*
    - constants
    - wildcards
    - case classes
    - tuples
    - some special magic like above
   */

  class Person(val name: String, val age: Int)

  object Person {
    def unapply(person: Person): Option[(String, Int)] = {
      if (person.age < 21) None// probably not a good idea to return in practice
      else Some((person.name, person.age))
    }

    def unapply(age: Int): Option[String] = {
      Some(if (age < 21) "minor" else "major")
    }
  }

  val bob = new Person("Bob", 25)
  val greeting = bob match {
    case Person(n, a) => s"Hi, my name is $n and I am $a years old"
  }

  println(greeting)

  val legalStatus = bob.age match {
    case Person(status) => s"My legal status is $status"
  }

  println(legalStatus)

  /*
    Exercise.
   */

   val n: Int = 40
   val d: Int = 8
   val mathProperty = n match {
     case x if x < 10 => "single digit"
     case x if x % 2 == 0 => "an even number"
     case _ => "no property"
   }

   object even {
     def unapply(arg: Int): Boolean = arg % 2 == 0
   }

   object singleDigit {
     def unapply(arg: Int): Boolean = arg < 10
   }

   object even2 {
     def unapply(arg: Int): Option[Boolean] = 
      if (arg % 2 == 0) Some(true)
      else None
   }

   object singleDigit2 {
     def unapply(arg: Int): Option[Boolean] = 
      if (arg < 10) Some(true)
      else None
   }

    val mathProperty2 = n match {
      case singleDigit() => "single digit"
      case even() => "an even number"
      case _ => "no property"
    }

    val mathProperty3 = d match {
      case singleDigit2(_) => "single digit"
      case even2(_) => "an even number"
      case _ => "no property"
    }

    println(mathProperty2)
    println(mathProperty3)

}
