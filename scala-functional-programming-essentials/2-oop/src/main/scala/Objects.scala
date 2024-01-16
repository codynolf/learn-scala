object Objects extends App {
  // SCALA does not have class-level functionality, it has objects
  // scala object = singleton instance
  // defining and object is defining a type with its only instance
  object Person{
    // can be defined similar to class
    // no contructor params
    // static
    val N_EYES = 2
    def canFly = false

    // factory method
    def apply(mother: Person, father: Person): Person = new Person("Bobby")
  }

  // practice used in scala, class and object with same name
  // seperate instance functionality from static functionality
  // Class and Object in same scope is called companion
  class Person(val name: String) {

  }

  val mary = new Person("Mary")
  val john = new Person("John")

  
  val bobby = Person(mary, john)
}
