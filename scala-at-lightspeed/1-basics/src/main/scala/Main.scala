object Main extends App {
  val meaningOfLife: Int = 42;
  //meaningOfLife = 43; // no reassignments;

  val bool = true; //type is optional

  val str = "I Love Scala"
  val composedStr = "I" + " " + "love" + " " + "scala";
  val interpolatedStr = s"I love scala $meaningOfLife";

  // expressions are structures that can be reduced to a value.
  val exp = 2 + 3

  // if-expression
  val ifExp = if(meaningOfLife < 43) 55 else 56

  // code block expression
  val codeBlock = {
    // defs
    val localValue = 5;

    localValue + 3; // value of the 'codeBlock'
  }

  // Side Effects, returns unit
  // Print to screen
  // send message
  // NOTHING TO DO WITH COMPUTE

  println("I love scala");
  
}