package lectures

object WhatIsAFunction extends App {
  // use functions as first class elements
  // work with functions like we would with values

  trait MyFunction[A, B] {
    def apply(element: A):B = ???
  }
  
  val doubler = new MyFunction[Int, Int]{
    override def apply(element: Int): Int = element * 2
  }

  // doubler acts like function
  println(doubler(2))

  // function types out of the box = Function1[A, B] ....  up to 22

  val stringToIntConverter = new Function1[String, Int] {
    override def apply(string: String): Int = string.toInt
  }

  println(stringToIntConverter("43"))

  val adder = new Function2[Int, Int, Int] {
    override def apply(n1: Int, n2: Int): Int = n1 + n2
  }

  println(adder(1, 2))

  // same as above, syntac sugar, will use moving foward
  val subtractor = new ((Int, Int) => Int){
    override def apply(n1: Int, n2: Int): Int = n1-n2
  }

  println(subtractor(10,5))

  // ALL SCALA FUNCTIONS ARE OBJECTS

  /* 
    Exercise
    1. a function which takes 2 strings and concatenates then
    2. transform the my predicate and mytransformer into function types
    3. define a function which takes an int and returns another functino which takes and int and returns and int
      - whats the function type
    
   */

   // Exercise 1
   val concatenator = new ((String, String)=> String){
    override def apply(s1: String, s2: String) = s1 + s2
   }
   println(concatenator("test ", "123"))

   // Exercise 2
   val specialfunction = new (Int => (Int => Int)){
    override def apply(v1: Int): Int => Int = new (Int => Int){
        override def apply(v2: Int): Int = v1 * v2
    }
   }

   val mult1 = specialfunction(4)
   println(mult1(4))
   println(specialfunction(2)(2)) // curried function, nested apply due to returning functions
}


