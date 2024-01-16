import java.time.LocalDate
object Main extends App {
  /* val person = new Person("Cody", 34)
  println(person.age)
  println(person.x)
  person.greet("Joe")
  person.greet(); */

  // Exercise 1
  val author1 = new Writer("cody", "nolf", 1989)
  println(author1.fullName())

  val author2 = new Writer("Test", "Dummy", 2000)
  println(author2.fullName())

  val novel1 = new Novel("The great novel", 2015, author = author1)
  println(novel1.authorAge())
  println(novel1.isWrittenBy(author1))
  println(novel1.isWrittenBy(author2))
  println(novel1.year)
  val newCopy = novel1.copy(2023)
  println(newCopy.year)
}

// constructor
class Person(name: String, val age: Int) {
  // defines implementation of this class
  // can have fields(val), methods, expressions
  // value of code block is ignored, becuase this is the implementation of the class
  // all expressions will be evaluated, any side effects will execute

  val x = 2

  println(1+3)

  // if name in greet and constuctor are same variable name, they will be same, need to use this.
  def greet(name: String): Unit = println(s"${this.name} says: Hi, $name")

  // in this greet, this. is implied
  // overloading function
  def greet(): Unit = println(s"Hi, I am $name")


  // multiple consturctors (overloading constructor)
  // calls primary constructor with both parameters
  // these constructors can ONLY call another constructor
  def this(name: String) = this(name, 0)
  def this() = this("John Doe")
}

// class parameters (name: String) are not class fields. Need to add val (val name: String) to make it a class field.

// Exercises
// 1) Novel and a Writer

class Writer(firstname: String, surname: String, val year: Int){
  def fullName(): String = s"$firstname $surname"
}

class Novel(name: String, val year: Int, author: Writer){
  def authorAge(): Int = year - author.year
  def isWrittenBy(author: Writer): Boolean = author == this.author
  def copy(year: Int): Novel = new Novel(name, year, author)
}

// 2) Counter class
class Counter(count: Int){
  def current(): Int = count
  def increment(): Counter = new Counter(count+1) // immunitability, IMPORTANT FOR FUNCTIONAL PROGRAMMING
                                                  // when needing to update values in an instance, its important to return a new object instead of mutating existing
  def decrement(): Counter = new Counter(count-1)
  def increment(count: Int): Counter = new Counter(this.count + count)
  def decrement(count: Int): Counter = new Counter(this.count - count)
}