object Main extends App {
  val person = new Person("Cody", 34)
  println(person.age)
  println(person.x)
  person.greet("Joe")
  person.greet();
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