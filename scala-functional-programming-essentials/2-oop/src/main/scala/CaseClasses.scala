object CaseClasses extends App{
  /* 
  boiler plate methods for new models
  equals, hashCode, toString etc
  case class help handle this 
   */

   case class Person(name: String, age: Int)
   // 1. Class parameters are promoted to fields
   val jim = new Person("Jim", 23)
   println(s"${jim.name} ${jim.age}")
   // 2. sensible toString  ex Person[Jim, 34]
   println(jim) // no toString needed, called by itself
   // 3. equals and hashcode are implemented out of the box
   // very useful in collections
   val jim2 = new Person("Jim", 23)
   println(jim == jim2) //true due to implemented hashcode
   // 4. Case class have Copy methods
   val jim3 = jim.copy(age = 45)
   println(jim3)
   // 5. companion objects, and factory methods ex: apply
   val thePerson = Person
   val mary = Person("Mary", 34)
   // 6. serializable
   // Akka sends serializable messages through network

   // 7. extractor patters
   // case classes can be used in pattern matching

   case object UnitedKingdom{
    def name: String = "The UK of GB and NI"

   }

   /* 
   Exercise: convert MyList to case class and objects
   
    */
}
