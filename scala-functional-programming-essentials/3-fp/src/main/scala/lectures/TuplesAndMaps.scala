package lectures

object TuplesAndMaps extends App{
  // tuples = finite ordered "lists"

  val tuple = new Tuple2(2, "hello") // aka (Int, String)
  val syntacTuple = (2, "hello")
  // atmost 22 elements of differnt types
  // similar to function types

  println(tuple._1)
  println(tuple._2)
  println(tuple.copy(_2 = "bye"))
  println(tuple.swap) //("hello", 2)

  // Maps - keys to values, similar to Dictionary in c#
  val map: Map[String, Int] = Map()
  val phonebook = Map(("Jim", 555), ("cody", 789), "daniel" -> 345).withDefaultValue(-1)
  println(phonebook)

  // map ops
  println(phonebook.contains("cody"))
  println(phonebook("Jim"))
  println(phonebook("Mary")) // would crash without withDefaultValue(), returns 1

  val newPairing = "Mary" -> 567
  val newPhonebook = phonebook + newPairing
  println(newPhonebook)
  println(newPhonebook("Mary"))

  // functionals on maps
  // map, flatMap, filter
  println(phonebook.map(pair => pair._1.toLowerCase() -> pair._2))

  // filterKeys
  println(phonebook.filterKeys(x => x.startsWith("c")).toMap)

  // mapValues
  println(phonebook.mapValues(v => v * 10).toMap)

  // conversion to other collections
  println(phonebook.toList)
  println(List(("cody", 789)).toMap) // vice versa
  val names = List("Bob", "James", "Cody", "Cara", "Daniel", "James")
  println(names.groupBy(name => name.charAt(0)))
}
