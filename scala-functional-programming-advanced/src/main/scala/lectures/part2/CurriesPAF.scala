package lectures.part2

object CurriesPAF extends App {
  // curried functions
  val superAdder: Int => Int => Int =
    x => y => x + y

  val add3 = superAdder(3)
  println(add3(5))
  println(superAdder(3)(5))

  // METHOD!
  def curriedAdder(x: Int)(y: Int): Int = x + y // curried method
  val add4: Int => Int = curriedAdder(4)
  println(add4(5))
  println(curriedAdder(4)(5))

  // lifting = ETA-EXPANSION
  // transforming a method into a function
  // functions != methods (JVM limitation)
  def inc(x: Int): Int = x + 1
  List(1, 2, 3).map(inc) // ETA-expansion

  // Partial function applications
  val add5 = curriedAdder(5) //not needed in scala 3_ // Int => Int

  // Exercise
  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simpleAddMethod(x: Int, y: Int): Int = x + y
  def curriedAddMethod(x: Int)(y: Int): Int = x + y

  // add7: Int => Int = y => 7 + y
  // as many different implementations of add7 using the above
  val add7_1 = (x: Int) => simpleAddFunction(7, x)
  val add7_2 = simpleAddFunction.curried(7)
  val add7_3 = curriedAddMethod(7) //_ // PAF
  val add7_4 = simpleAddMethod(7, _: Int) // alternative syntax for turning methods into function values
  val add7_5 = simpleAddFunction(7, _: Int) // alternative syntax for turning methods into function values

  // underscores are powerful
  def concatenator(a: String, b: String, c: String): String = a + b + c
  val insertName = concatenator("Hello, I'm ", _: String, ", how are you?") // x: String => concatenator("Hello, I'm ", x, ", how are you?")
  println(insertName("Daniel"))

  val fillInTheBlanks = concatenator("Hello, ", _: String, _: String) // (x, y) => concatenator("Hello, ", x, y)
  println(fillInTheBlanks("Daniel", " Scala is awesome!"))

  // Exercise
  /*
    1. Process a list of numbers and return their string representations with different formats
       Use the %4.2f, %8.6f and %14.12f with a curried formatter function.
   */
  def curriedFormatter(s: String)(number: Double): String = s.format(number)

  val numbers = List(Math.PI, Math.E, 1, 9.8, 1.3e-12)
  val simpleFormat = curriedFormatter("%4.2f") //_ // lift
  val seriousFormat = curriedFormatter("%8.6f")// _ // lift
  val preciseFormat = curriedFormatter("%14.12f") //_ // lift

  println(numbers.map(simpleFormat))
  println(numbers.map(seriousFormat))
  println(numbers.map(preciseFormat))

  /*
    2. difference between
       - functions vs methods
       - parameters: by-name vs 0-lambda
   */
  def byName(n: => Int): Int = n + 1
  def byFunction(f: () => Int): Int = f() + 1

  def method: Int = 42
  def parenMethod(): Int = 42

  /*
    calling byName and byFunction
    - int
    - method
    - parenMethod
    - lambda
    - PAF
   */
  byName(23) // ok
  byName(method) // ok
  byName(parenMethod()) // ok
  //byName(parenMethod) // ok but beware ==> byName(parenMethod())
  // byName(() => 42) // not ok
  byName((() => 42)()) // ok
  // byName(parenMethod _) // not ok

  // byFunction(45) // not ok
  // byFunction(method) // not ok!!!!!! does not do ETA-expansion!
  byFunction(parenMethod) // compiler does ETA-expansion
  byFunction(() => 46) // works
  //byFunction(parenMethod _) // also works, but warning- unnecessary


}
