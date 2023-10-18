object Main extends App {
  
  class Animal {
    // define fields
    val age: Int = 0
    // define methods
    def eat() = println("I'm eating")

  }

  val animal1 = new Animal

  // constructor is part of class def
  class Dog(name: String) extends Animal{
    // constructor args are NOT fields
  }

  val dog = new Dog("Jim")

  //abstract class
  abstract class WalkingAnimal {
    val hasLegs = true
    def walk(): Unit
    // Everything is public by default, can make private or protected
  }

  // Interface
  trait Carnivore {
    def eat(animal: Animal): Unit
  }

  // single-class inheritance, multi-trait "mixing"
  class Crocodile extends  Animal with Carnivore{
    override def eat(animal: Animal): Unit = println("I am eating animal");
  }

  val croc = new Crocodile
  croc.eat(dog)
  croc eat dog // infix notation: only avilable for methods with 1 arg

  // operators in scala are methods
  val basicMath = 1 + 1/// + is a method

  // anonymous classes
  val dino = new Carnivore {
    override def eat(animal: Animal): Unit = println("I am a dino")
  }

  // class Carnivore_Anonymouse_1413423 extends Carnivore ....
  // val dinosour = new Carnivore_Anonymouse_1413423

  // singleton object
  object mysingleton{
    val myval = 3213;
    def mymethod(): Int = 32423;

    def apply(x: Int): Int = x + 1
  }

  println(mysingleton.myval);
  println(mysingleton.mymethod());


  // apply makes these the same
  mysingleton.apply(65)
  mysingleton(65) // uses apply method

  // case classes
  // lightweight data structures with some boilerplate
  // sensible equals and hash code
  // serialization
  //  has a companion apply

  case class Person(name: String, age: Int)
  val bob = Person("Bob", 44); // Equiv to Person.Apply()

  // generics
  abstract class MyList[T] {
    def head: T
    def tail: MyList[T]
  }

  val list: List[Int] = List(1,2,3)



  // Point #1: in scala we usualy operator in IMMUTABLE value/objects
  // Any modification to object should return a new object, eg apply
  // good for multithreaded environment
  // makes sense for code
  // Point #2: Scala is good object oriented language
  // Point #3: 
}