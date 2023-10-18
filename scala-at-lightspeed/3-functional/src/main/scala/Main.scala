object Main extends App {
  // scala is still object oriented

  class Person(name: String){
    def apply(age: Int) = println(s"I am $age.")
  }

  val bob = new Person("Bob")
  bob.apply(43) // is the same as below
  bob(43)

  // Precense of apply method allows class to be invoked like a function.
  // This is important....

  // Scala runs on the JVM
  // Functional Programming
  // - compose functions
  // - pass functions as args
  // - return fucntion as result
  // Conclusion: JVM has functionX

  val simpleIncrementer = new Function1[Int, Int] {
    override def apply(v1: Int): Int = v1 + 1
  }

  simpleIncrementer.apply(23)
  simpleIncrementer(23)
   //defined a function
   // All scala functions are instances of functionX type

  // syntax sugars
  val doubler: Function[Int, Int]=(x: Int) => 2*x
  val doubler2: Int => Int = (x: Int) => 2*x
  val doubler3 = (x: Int) => 2*x

  doubler(1)
  doubler2(1)
  doubler3(1)

  // Higer-order functions take functions as args or functions as results
  val mappedList = List(1,2,3).map(x => x + 1); // map method is a higer-order function
  val flatMappedList = List(1, 2, 3).flatMap((x => List(x, x*2)))

  val allPairs = List(1,2,3).flatMap(number => List('a','b','c').map(letter => s"$number-$letter"))

  // for comphrehension, this is same result as line above
  val alternativePairs = for {
    number <- List(1, 2, 3)
    letter <- List('a','b','c')
  } yield s"$number-$letter"

  /*
  * Collections
  * 
  */

  

}