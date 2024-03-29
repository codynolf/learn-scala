package lectures

object AllThePatterns extends App {
  // 1 - contants
  val x: Any = "Scala"
  val contants = x match {
    case 1 => "its int"
    case "Scala" => "the scala"
    case true => "the truth"
    case AllThePatterns => "a singleton"
  }

  // 2 - match anything
  // 2.1 wildcard
  val matchAnything = x match {
    case _ =>
  }
  // 2.2 variable
  val matchAVariable = x match {
    case something => s"I found $something"
  }

  // 3 - tuples
  val tuple = (1,2)
  val matchATuple = tuple match {
    case (1, 1) =>
    case (something, 2) =>
  }

  val nestedTuple = (1, (2,3))
  val matchNestedTuple = nestedTuple match {
    case (_, (2, v)) => s"I found $v"
  }
  // PM can be nested

  // 4-case classes - contructor pattern
  // PM can be nested with case classes
  val list: MyList[Int] = Cons(1, Cons(2, EmptyList))
  val matchList = list match {
    case EmptyList => ???
    case Cons(head, Cons(subhead, subtail)) => ???
  }

  // 5 - List patterns
  val standardList = List(1,2,3,42)
  val standListMatching = standardList match {
    case 1 :: List(_) => //infix pattern
    case List(1, _, _, _) => // extractor -> advanced
    case List(1, _*) => // list of arbitrary length - advanced
    case List(1,2,3) :+ 42 => //infix pattern
    case _ =>
  }

  // 6 - type specifiers
  //val unknown: Any = 2
  //unknown match {
  //  case list: List[Int] => //explicit type specifier
  //  case _ =>
  //}

  // 7 - name binding
  val nameBindingMatch = list match {
    case Cons(1, rest @ Cons(2, _)) => // named patterns inside nested patterns
    case notEmpty @ Cons(_,_) => // named pattern, use the name later
  }

  // 8 - multi patterns
  val multiPattern = list match {
    case EmptyList | Cons(0,_) => // compound (multi) pattern
  }

  // 9 - if guards
  val secondEl = list match {
    case Cons(_,Cons(special, _)) if special % 2 == 0 =>
  }


  /* 
    Exercise
   */

   // this will match list of strings!!!!!!
   // JVM trick questions
   
   val numbers = List(1,2,3)
   val numbersMatch = numbers match {
    //case listOfStrings: List[String] => "a list of strings"
    case listoFNumbers: List[Int] => "list of numbers"
    case null => println("")
   }
}
