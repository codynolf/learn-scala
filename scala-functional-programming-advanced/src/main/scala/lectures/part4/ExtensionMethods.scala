package lectures.part4

object ExtensionMethods extends App{
  
  case class Person(name: String) {
    def greet: String = s"Hi, my name is $name"
  }

  extension (string: String) {
    def greet: String = s"Hi, my name is $string"
  }

  println("Daniel".greet)
}
