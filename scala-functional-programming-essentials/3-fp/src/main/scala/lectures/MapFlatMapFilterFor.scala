package lectures

object MapFlatMapFilterFor extends App {
  val list = List(1,2,3)
  println(list)
  println(list.head)
  println(list.tail)

  //map
  println(list.map(_+1))
  println(list.map(_+ " is a number"))

  //filter
  println(list.filter(_ %2 == 0))

  //flatMap
  println(list.flatMap(e => List(e, e+1)))

  /* 
    Exercise
    1) print all combinations between two lists
   */
  val numbers = List(1, 2, 3, 4)
  val chars = List('a','b','c','d')

  println(numbers.flatMap(n => chars.map(c => n + "" + c)))

  //foreach
  list.foreach(println)

  // for-comphrehensions
  val forCombination = for {
    n <- numbers
    c <- chars
  } yield n + "" + c
  println(forCombination)
}
