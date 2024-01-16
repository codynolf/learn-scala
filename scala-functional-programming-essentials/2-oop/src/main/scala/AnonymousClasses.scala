object AnonymousClasses extends App {
  abstract class Animal{
    def eat: Unit
  }

  // anonymous class
  val funnyAnimal: Animal = new Animal{
    override def eat: Unit = println("ahahaha")
  }

  // proof its anonymous class
  println(funnyAnimal.getClass)

  class Person(name: String){
    def sayHi: Unit = println("Hi")
  }

  // anonymous class for non-abstract data type as well
  val jim = new Person("jim"){
    override def sayHi: Unit = println("Hi my name is jim")
  }

  println(jim.getClass)
}
