object Generics extends App{
  class MyList[A] {
    //use the type A
    def add[B>:A](element: B): MyList[B] = ???
  }

  class MyMap[Key, Value]

  val listInt = new MyList[Int]
  val listString = new MyList[String]

  // generic methods
  object MyList{
    def empty[A]: MyList[A] = new MyList[A]
  }

  val listInt2 = MyList.empty[Int]

  // variance problem
  class Animal
  class Cat extends Animal
  class Dog extends Animal

  // 1) yes List[Cat] extends List[Animal] == COVARIANCE
  class CovariantList[+A]
  val animal: Animal = new Cat
  val animalList: CovariantList[Animal] = new CovariantList[Cat]
  //animalList.add(new Dog) ????? => return a list of animals
  
  // 2) No, INTRAVARIANT
  class InvariantList[A]
  val invariantListOfAnimal: InvariantList[Animal] = new InvariantList[Animal]

  // 3) Hell, NO! CONTRAVARIANCE
  class ContravariantList[-A]
  val contravariantList: ContravariantList[Cat] = new ContravariantList[Animal]

  // Bounded types

  // upper bounded type
  class Cage[A <: Animal](animal: A)

  val cage = new Cage(new Dog)

  class Car
  // fails
  //val carCage = new Cage(new Car)

  class Cage2[A >: Animal](animal: A)

  // Expand the MyList to be generic
}
