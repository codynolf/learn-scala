import scala.annotation.tailrec
object Main extends App {
  //valuesVariablesAndTypes
  //expressions
  //functions
  recursion

  def recursion(): Unit = {

    def factorial(n: Int):Int = {
      if(n <= 0) 1
      else {
        println("computing factorial of " + n + "- I first need factorial of " + (n-1))
        val result = n * factorial(n-1)
        println("computed factorial of " + n)

        result
      }
    }
    //println(factorial(5000))
    def factorial2(n: Int) : Int = {
      @tailrec
      def factorialHelper(x: Int, accumulator: Int): Int = {
        if(x<=1) accumulator
        else factorialHelper(x -1, x * accumulator)
      }

      factorialHelper(n, 1)
    }
    //println(factorial(10) == factorial2(10))

    // Tail Recursion practice
    // 1. Concatenate a string n times
    @tailrec
    def repeatString(aString: String, n: Int, accumulator: String = ""): String = {
      if(n<=0) accumulator
      else repeatString(aString, n -1, aString + accumulator)
    }

    println(repeatString("cody ", 20)) 
    // 2. IsPrime function
    def isPrime(n: Int): Boolean = {
      @tailrec
      def isPrimeUntil(t: Int, isStillPrime: Boolean): Boolean = {
        if(!isStillPrime) false
        else if(t <= 1) true
        else  isPrimeUntil(t-1, n % t != 0)
      }

      isPrimeUntil(n / 2, true)
    }

    println(isPrime(2003))
    println(isPrime(629))
    // 3. Fibonacci function
    def fibonacci(n: Int):Int = {
      @tailrec
      def fiboTailRec(i: Int, last: Int, nextLast: Int):Int = {
        if(i >= n) last
        else fiboTailRec(i+1, last + nextLast, last) 
      }
      if(n<=2) 1
      else fiboTailRec(2, 1, 1)
    }
    println(fibonacci(8))
  }
  def functions(): Unit = {
    def aFunction(a: String, b: Int): String = {
      a + " " + b
    }

    // function is also an expression
    println(aFunction("cody is", 34))

    def aRepeatedFunction(aString: String, n: Int): String = {
      if(n==1) aString
      else aString + aRepeatedFunction(aString, n-1)
    }

    println(aRepeatedFunction("cody ", 3))

    // WHEN YOU NEED LOOPS, USE RECURSION

    // Functions practice
    // 1. A greeting function (name, age) => "Hi, my name is name and I am age years old"
    def greeting(name: String, age: Int): String = {
      s"Hi, my name is $name and I am $age years old."
    }
    println(greeting("cody", 34))
    // 2. A Factorial Function 1 * 2 * 3 * ... * n
    def factorial(n: Int):Int = {
      if(n <= 0) 1
      else n * factorial(n-1)
    }
    println(factorial(5))
    // 3. A fibonacci function f(1) = 1, f(2) = 1, f(n) = f(n-1) = f(n-2)
    def fibonacci(n: Int):Int = {
      if(n <= 2) 1
      else fibonacci(n-1) + fibonacci(n-2)
    }
    println(fibonacci(8))
    // 4. Tests if number is prime
    def isPrime(n: Int): Boolean = {
      def isPrimeUntil(t: Int): Boolean = {
        if(t <= 1) true
        else n % t != 0 && isPrimeUntil(t-1)
      }

      isPrimeUntil(n / 2)
    }

    println(isPrime(37))
    println(isPrime(2003))
    println(isPrime(4000*3423423))
  }
  def expressions(): Unit = {
    val x = 1+2  // This is an expression
    println(x)

    // Many expressions that we are used to in other languages
    // Math + - * 
    // Logical && ||
    // Relational == < !=

    // INSTRUCTIONS (DO) vs. EXPRESSION (VALUE/TYPE)
    // In function programming we are going to focus on expressions vs instructions.

    // IF Expression vs. in other languages performing a instruction
    // aConditionedValue is set from and expression vs. other languages setting a value inside the if/else
    val aCondition = true
    val aConditionedValue = if(aCondition) 5 else 3

    // Next up Loops!, however we do not want to use loops in scala/functional programming
    // Avoid while loops and all loops in functional programming

    // Everything is scala is an expressions
    var aVariable = 4
    val aWeirdValue = (aVariable = 3) // Unit = void
    println(aWeirdValue) // ()
    // above is a side effect, side effects are instuctions that are part of imperative programming

    // code blocks
    // code blocks are expressions. Value of the last line in block is the value of aCodeBlock
    // code blocks are scoped
    val aCodeBlock = {
      val y = 2
      val z = y + 1
      if(z>2) "hello" else "goodbye"
    }


    // Quiz questions
    // 1) what is the difference between "hello world" vs. println("hello world")
    //  Answer: The first is a value of "hello world". The second is an expression that is a side-effect that prints a string "hello world"
    // 2) what is the value of 
    val someValue = {
      2 < 3
    }
    //  Answer: true
    // 3) what is the value of
    val someOtherValue = {
      if(someValue) 239 else 342
      43
    }
    //  Answer: 43

  }
  def valuesVariablesAndTypes(): Unit = {
     val x: Int = 42
      println(x)

      //x=2
      // VALS ARE IMMUTABLE, above line does not compile

      //DataTypes in Scala

      // int
      val aInt: Int = 42
      // string
      val aString: String = "Cody"
      // boolean
      val aBoolean: Boolean = true
      // char
      val aChar = 'c'
      // short
      val aShort: Short = 324
      // long
      val aLong: Long = 542435243524354323L
      // float
      val aFloat: Float = 2.54f
      // double
      val aDouble: Double = 3.23425

      // variables
      var aVariable: Int = 4
      aVariable = 5

      // VARIABLES CAN BE REASSIGNED
      // In functional programming these are known as side-effects
      // side-effects allow you to see what you are doing
      //  examples
      //     println
      //     call api
  }
}

