import scala.language.postfixOps
object MethodNotations extends App {
  class Person(val name: String, favoriteMovie: String, val age: Int = 0){
    def likes(movie: String): Boolean = movie == favoriteMovie
    def +(person: Person): String = s"${this.name} is hanging out with ${person.name}"
    def +(nickname: String): Person = new Person(s"${this.name} ($nickname)", this.favoriteMovie)
    def unary_! : String = s"$name, what the heck!!"
    def unary_+ : Person = new Person(name, favoriteMovie, age + 1)
    def isAlive : Boolean = true
    def learns(skill: String): String = s"$name learns $skill"
    def learnsscala: String = learns("scala")
    def apply(): String = s"Hi, my name is $name and i like $favoriteMovie"
    def apply(x: Int): String = s"$name watched $favoriteMovie $x times."
  }

  val mary = new Person("Mary", "Inception")

  println(mary.likes("Inception"))
  // infix notation or operator notation, only works with 1 parameter
  println(mary likes "Inception")
  println(mary likes "Dogs")

  val tom = new Person("Tom", "Fight Club")
  val tery = new Person("Tery", "Toy Story")
  println(mary + tom)

  // ALL "operators" are methods

  // prefix notation, unary operators are methods
  val x = -1
  val y = 1.unary_- // equivelant to -1
  // Unary prefix only works with -+~!

  println(!mary)
  println(mary.unary_!)


  // postfix notation, only available to methods without parameters
  println(mary.isAlive)
  println(mary isAlive)

  // apply
  println(mary.apply())
  println(mary()) //  calls apply

  // Excerises
  // 1) overload infix operator '+', nickname that returns new Person w/ name "Mary (the rockstar)"
  // 2) Add an age to Person class, Add urnary_+ operator => new Person with incrementated age, similar to ++Int
  // 3) Add learns method, takes string param, returns string "Mary learns scala"
  //    Add learnsscala method, calls learns method with "scala"
  //    Use it in postfix notation
  // 4) Overload apply  method with int param and returns string "Mary watched inception 3 times."


  // Exercise 1
  println((mary + "maserati").name)

  // Exercise 2
  println(mary.age)
  val mary2 = +mary
  println(mary2.age)
  println((+mary2).age)

  // Exercise 3
  println(mary learnsscala)
  println(mary learns "golf")

  // Exercise 4
  println(mary())
  println(mary(3))
}
