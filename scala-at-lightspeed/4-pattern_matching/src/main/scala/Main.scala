object Main extends App {
  // pattern matching is some kind of switch expression

  val integer = 55
  val order = integer match {
    case 1 => "first"
    case 2 => "second"
    case 3 => "third"
    case _: Int => 
  }

  // pattern match is an expression
  // equivelant to switch in other languages
  // pattern match is more powerful, 
  // able to deconstuct data structures into its constituent parts


  case class Person(name: String, age: Int)

  val bob = Person("Bob", 43)

  val personGreeting = bob match {
    case Person(n, a) => s"Hi, my name is $n, and I am $a"
    case _ => "Something else"
  }

  // pattern matching is mostly used for case class deconstuction
}