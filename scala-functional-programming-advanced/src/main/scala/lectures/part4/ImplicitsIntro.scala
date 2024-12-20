package lectures.part4

object ImplicitsIntro extends App {
  
  val pair = "Daniel" -> "555"
  val intPair = 1 -> 2

  case class Person(name: String) {
    def greet = s"Hi, my name is $name"
  }

  implicit def fromStringToPerson(string: String): Person = Person(string)
  println("Peter".greet) // println(fromStringToPerson("Peter").greet)

  // implicit parameters
  def increment(x: Int)(implicit amount: Int) = x + amount
  implicit val defaultAmount: Int = 10

  println(increment(2)) // println(increment(2)(defaultAmount))
}
