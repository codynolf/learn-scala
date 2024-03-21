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
  val phonebook = Map(("Jim", 555),("JIM", 111), ("cody", 789), "daniel" -> 345).withDefaultValue(-1)
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
  println("exercise 1")
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

  /* 
    Exercises
    1. What would happen if you tolower "Jim" and "JIM"?
      takes latest pair
    2. Overly simplified social network based on maps
      Each person has name (string)
      Map of frieds (String, List(string) friends)
      - add a person to the network
      - remove
      - friend (mutual)
      - unfried

      - number of friends of a person
      - person with most friends
      - no friends
      - if there is a social connection between 2 people, direct or not
   */

   val codySocialNetwork = SocialNetworkCody

   val network: Map[String, Set[String]] = Map[String, Set[String]]()

   val newmap1 = codySocialNetwork.newPerson(network, "cody")
   val newmap2 = codySocialNetwork.newPerson(newmap1, "jen")
   val newmap3 = codySocialNetwork.addFriend(newmap2, "cody", "jen")
   val newmap4 = codySocialNetwork.removeFriend(newmap3, "cody", "jen")
   val newmap5 = codySocialNetwork.removePerson(newmap3, "cody")

   println(newmap3)
}


object SocialNetworkCody {
  

  def newPerson(network: Map[String, Set[String]], name: String) = network + (name -> Set[String]())

  def removePerson(network: Map[String, Set[String]], name: String) = {
    def removeAux(friends: Set[String], networkAcc: Map[String, Set[String]]) : Map[String, Set[String]] = {
      if(friends.isEmpty) networkAcc
      else removeAux(friends.tail, removeFriend(networkAcc, name, friends.head))
    }
    val unfriended = removeAux(network(name), network)
    unfriended - name
  }

  def addFriend(network: Map[String, Set[String]], name: String, friend: String) = 
    val nameFriends = network(name)
    val friendFriends = network(friend)

    network + (name -> (nameFriends + friend)) + (friend -> (friendFriends + name))

  def removeFriend(network: Map[String, Set[String]], name: String, friend: String) = 
    val nameFriends = network(name)
    val friendFriends = network(friend)

    network + (name -> (nameFriends - friend)) + (friend -> (friendFriends - name))

  def numberOfFriends(network: Map[String, Set[String]], name: String) = network(name).size

  def areConnected(network: Map[String, Set[String]], name1: String, name2: String): Boolean = {

      def bfs(target: String, consideredPeople: Set[String], discoveredPeople: Set[String]): Boolean = {
        if(discoveredPeople.isEmpty) false
        else {
          val person = discoveredPeople.head
          if (person == target) true
          else if (consideredPeople.contains(person)) bfs(target, consideredPeople, discoveredPeople.tail)
          else bfs(target, consideredPeople + person, discoveredPeople.tail ++ network(person))
        }
      }

      bfs(name2, Set(), network(name1)+name1)
  }

  def mostPopular(network: Map[String, Set[String]]): String = network.maxBy(pair => pair._2.size)._1
  def noFriends(network: Map[String, Set[String]]): List[String] = network.filter(_._2.size == 0).map(p => p._1).toList

}