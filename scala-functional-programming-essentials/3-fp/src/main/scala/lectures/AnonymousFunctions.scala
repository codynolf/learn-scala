package lectures

object AnonymousFunctions extends App{
    val doubler = new (Int => Int) {
        override def apply(x: Int): Int = x * 2
    }

    // syntac sugar for above, called an anonymous function or LAMBDA
    val doublerV2 = (x: Int) => x * 2
    val dobblerV3: Int => Int = x => x*2 // specified type makes the method shorter

    // multiple params in lambda
    val adderV1: (Int, Int) => Int = (a, b) => a + b
    val adderV2 = (a: Int, b: Int) => a + b

    //  no params
    val doSomethingV1 = () => 3
    val doSomethingV2: () => Int = () => 3

    // careful, the below is not the same! Lambdas must be called with parens
    println(doSomethingV1) // function itself
    println(doSomethingV1()) // the function call

    // curly braces with lambdas
    val stringToInt = { (str: String) =>
        str.toInt    
    }

    // MOAR syntac sugar
    val niceIncrementer: Int => Int = _ + 1 // use _ instead of defining param name
    val niceAdder: (Int, Int) => Int = _ + _


    /* 
        Exercises
        1. MyList: Replace all function x calls with lambdas
        2. rewrite special function as anonymous function
     */

     val specialfunction = new (Int => (Int => Int)){
        override def apply(v1: Int): Int => Int = new (Int => Int){
            override def apply(v2: Int): Int = v1 * v2
        }
    }

    val specialfunctionv2 = (v1: Int) => (v2: Int) => v1 * v2
}
