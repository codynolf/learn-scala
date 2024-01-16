object AbstractDataTypes extends App {
  abstract class Animal{
    val creatureType: String
    def eat: Unit
  }

  class Dog extends Animal{
    val creatureType: String = "K9"
    def eat = println("crunch")
  }

  // traits
  trait Carnivore{
    def eat(animal: Animal): Unit
  }

  class Crocodile extends Animal with Carnivore{
    val creatureType: String = "croc"
    def eat: Unit = println("uh huh")
    def eat(animal: Animal): Unit = println(s"I'm a croc and I am eating ${animal.creatureType}")
  }

  val dog = new Dog
  val croc = new Crocodile

  croc.eat(dog)


  // trais vs. abstract classes
  // 1) traits do not have constructor parameters
  // 2) can only extend 1 class, but can inherit multiple traits
  // 3) traits are behavior, example carnivore vs abstract class is a thing, example animal
}
