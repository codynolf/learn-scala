package lectures

object HofCurries extends App {
  val superfunction: (Int, (String, (Int => Boolean)) => (Int, Int))= null
  // higher order fucntion (HOF), either takes or returns a function
  // examples: map, flatMap, filter in MyList

  // function that applies another function N times over a given value
  // nTime(f, n, x)

  def nTimes(f: Int => Int, n: Int, x: Int): Int =
    if(n <= 0) x
    else
      nTimes(f, n-1, f(x))
  
  val plus1 = (x: Int) => x + 1

  println(nTimes(plus1, 10, 1))

  def nTimesV2(f: Int => Int, n: Int): (Int => Int) =
    if(n <= 0) (x: Int) => x
    else (x: Int) => nTimesV2(f, n-1)(f(x))

  val plus10 = nTimesV2(plus1, 10)
  println(plus10(1))


  // curried functions
  val superAdder = (x: Int) => (y: Int) => x + y;
  val add3 = superAdder(3)
  println(add3(10))
  println(superAdder(3)(10)) // same as above

  // functions with multiple parameter lists
  def curriedFormatter(f: String)(n: Double): String = f.format(n)

  val standardFormat: (Double => String) = curriedFormatter("%4.2f")
  val preciseFormat: (Double => String) = curriedFormatter("%10.8f")

  println(standardFormat(Math.PI))
  println(preciseFormat(Math.PI))

  /* 
    1. Expand MyList
        - foreach method A => Unit
          [1,2,3].foreach(x => println(x))

        - sort function ((A, A) => Int) => MyList
          [1,2,3].sort((x, y) => y-x) => [3,2,1]

        - zipWith function (list, (A, A) => B) => MyList[B]
          [1,2,3].zipWith([4,5,6], x * y) => [4,10,18]

        - fold function (start)(function) => a value
          [1,2,3].fold(0)(x+y) = 6

    2. toCurry(f: (Int, Int) => Int) => (Int => Int => Int)
       fromCurry(f: Int => Int => Int) => (Int, Int) => Int



    3. compose (f, g) => x => f(g(x))
       andThem (f, g) => x => g(f(x))
   */

   // Exercise 2
   def toCurry(f: (Int, Int) => Int): (Int => Int => Int) =
    x => y => f(x, y)
    
  def fromCurry(f: Int => Int => Int): (Int, Int) => Int =
    (x, y) => f(x)(y)

  // exercise 3
  /* def compose(f: Int => Int, g: Int => Int): Int => Int =
    x => f(g(x))
  def andThen(f: Int => Int, g: Int => Int): Int => Int =
    x => g(f(x)) */

  
  def compose[A,B,T](f: A => B, g: T => A): T => B =
    x => f(g(x))
  def andThen[A,B,C](f: A => B, g: B => C): A => C =
    x => g(f(x))


  def superAdderv2: (Int => Int => Int) = toCurry(_+_)
  def add4 = superAdderv2(4)
  println(add4(17))

  def simpleAdder: (Int, Int) => Int = fromCurry(superAdder)
  println(simpleAdder(4,17))

  val add2 = (x: Int) => x + 2
  val mult3 = (x: Int) => x * 3

  val composed = compose(add2, mult3)
  val andThened = andThen(add2, mult3)

  println(composed(4))
  println(andThened(4))
}
