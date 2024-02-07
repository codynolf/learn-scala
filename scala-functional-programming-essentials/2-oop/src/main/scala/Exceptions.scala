object Exceptions extends App{
  val x: String = null
  // println(x.length)
  // this ^^ will blow up

  // 1) throwing exceptions

  //val exceptString: String = throw new NullPointerException
  //val exceptNothing: Nothing = throw new NullPointerException // this is actuall nothing

  // NullPointerException is a class, being instantiated by new
  // for close to use "throw" method it needs to extend Throwable
  //  Exceptions and Error are major throwable subtypes
  // exeptions denotes something that went run with program
  // errors denotes something that went wrong with system, out of memory

  // 2) Catch exceptions
  def getInt(withExeptions: Boolean): Int = {
    if(withExeptions) throw new RuntimeException("fdsafd")
    else 42
  }

  try {
    // code that might fail
    getInt(true)
  } catch {
    // if proper case is not found it will blow up
    case e: RuntimeException => println("caught exeptions")
  } finally {
    // code that is executed no matter what
    println("yup, told ya")
  }

  // just like anything in scala, try, catch, value is an expression
  // above would return any
  // example below would return int, compiler unifies based on possible return types

  val intTCF = try {
    // code that might fail
    getInt(true)
  } catch {
    // if proper case is not found it will blow up
    case e: RuntimeException => 43
  } finally {
    // code that is executed no matter what
    println("yup, told ya")
  }

  println(intTCF)

  // 3) defining own exceptions

  class MyException extends Exception
  val exception = new MyException

  throw exception

  // Exercises
  /* 
    1) crash program with an OutOfMemoryError
    2) crash with stackoverflow error
    3) Define PocketCalculator
        - add(x, y)
        - multiply (x, y)
        - divide (x, y)
        - subtract (x, y)
   */
}
