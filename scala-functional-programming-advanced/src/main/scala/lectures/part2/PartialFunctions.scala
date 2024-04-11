package lectures.part2

object PartialFunctions extends App{
  
  val aFunction = (x: Int) => x + 1 // Function1[Int, Int] === Int => Int

  val aFussyFunction = (x: Int) => 
    if (x == 1) 42
    else if (x == 2) 56
    else if (x == 5) 999
    else throw new FunctionNotApplicableException

  class FunctionNotApplicableException extends RuntimeException

  val aNicerFussyFunction = (x: Int) => x match {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  }

  // {1, 2, 5} => Int, partial domain of int, 1, 2, 5. 
  // brackets are a way to define a partial function
  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  } // partial function value

  println(aPartialFunction(2))
  // println(aPartialFunction(57273)) // MatchError

  // PF utilities
  println(aPartialFunction.isDefinedAt(67))
  println(aPartialFunction.isDefinedAt(5))

  // lift to total function returning some/none, no match error
  val lifted = aPartialFunction.lift // Int => Option[Int]
  println(lifted(2)) // Some(56)
  println(lifted(98)) // None

  val pfChain = aPartialFunction.orElse[Int, Int] {
    case 45 => 67
  }

  println(pfChain(2)) // 56
  println(pfChain(45)) // 67

  // PF extends normal functions
  val aTotalFunction: Int => Int = {
    case 1 => 99
  }

  // HOFs accept partial functions as well
  // very nice
  val aMappedList = List(1, 2, 3).map {
    case 1 => 42
    case 2 => 56
    case 3 => 999
  }

  println(aMappedList) // 42, 56, 999

  /*
    Note: PF can only have one parameter type
   */

  /**
    * Exercises
    *
    * 1 - construct a PF instance yourself (anonymous class)
    * 2 - dumb chatbot as a PF
    */

  val codyPF = new PartialFunction[Int, Int] {
    override def isDefinedAt(x: Int): Boolean = x == 1 || x == 2 || x == 5

    override def apply(v1: Int): Int = v1 match {
      case 1 => 42
      case 2 => 56
      case 5 => 999
    }
  }

  println(codyPF)

  val chatbot: PartialFunction[String, String] = {
    case "hello" => "Hi, my name is Cody"
    case "goodbye" => "Goodbye"
    case "what is my name" => "Cody"
    case _ => "I don't understand"
  }

  scala.io.Source.stdin.getLines().map(chatbot).foreach(println)
}
