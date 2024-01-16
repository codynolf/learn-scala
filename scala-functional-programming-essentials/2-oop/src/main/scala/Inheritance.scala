object Inheritance extends App{
    // single class inhertiance
    // can only inherit one class at a time
    class Animal {
        def eat = println("nomnom")

        // not accesible from children
        // eg cat.typeof
        private def typeof = "animal"

        // protected allows child class
        // functionality to use function
        protected def run = "run run run"

        val creatureType = "wild creature"
    }

    class Cat extends Animal{
        def running = super.run
    }

    class Lab extends Dog

    val cat = new Cat
    cat.eat

    val lab = new Lab
    lab.eat

    // inheritance and constructors
    class Person(name: String, age: Int){
        def this(name: String) = this(name, 0)
    }
    class Adult(name: String, age: Int, idCard: String) extends Person(name, age)
    class Teen(name: String, age: Int, librayCard: String) extends Person(name)

    // overriding
    class Dog extends Animal{
        override def eat = println("crunch, crunch")
        override val creatureType = "Domestic"
    }

    val dog = new Dog
    dog.eat

    // type subsition
    // polymorphism
    val unknownAnimal: Animal = new Dog
    unknownAnimal.eat
 
}
