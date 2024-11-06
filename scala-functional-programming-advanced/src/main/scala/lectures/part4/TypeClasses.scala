package lectures.part4

object TypeClasses extends App {
  
  trait HTMLWritable {
    def toHTML: String
  }

  case class User(name: String, age: Int, email: String) extends HTMLWritable {
    override def toHTML: String = s"<div>$name ($age yo) <a href=$email/> </div>"
  }
  val john = User("John", 32, "jdawg@aol.com")
  val johnHtml = john.toHTML

  /*
    1 - for the types WE write
    2 - ONE implementation out of quite a number
   */

  // option 2 - pattern matching
  object HTMLSerializerPM {
    def serializeToHTML(value: Any) = value match {
      case User(n, a, e) =>
      // case java.util.Date =>
      case _ =>
    }
  }

  /*
    1 - lost type safety
    2 - need to modify the code every time
    3 - still ONE implementation
   */

  // option 3 - type classes
  trait HTMLSerializer[T] {
    def serialize(value: T): String
  }

  implicit object UserSerializer extends HTMLSerializer[User] {
    def serialize(user: User): String = s"<div>${user.name} (${user.age} yo) <a href=${user.email}/> </div>"
  }

  println(UserSerializer.serialize(john))

  // 1 - we can define serializers for other types
  import java.util.Date
  object DateSerializer extends HTMLSerializer[Date] {
    override def serialize(date: Date): String = s"<div>${date.toString}</div>"
  }

  // 2 - we can define multiple serializers
  object PartialUserSerializer extends HTMLSerializer[User] {
    def serialize(user: User): String = s"<div>${user.name}</div>"
  }

  // TYPE CLASS
  trait MyTypeClassTemplate[T] {
    def action(value: T): String
  }

  // TYPE CLASS INSTANCES
  implicit object IntTemplate extends MyTypeClassTemplate[Int] {
    def action(value: Int): String = s"Int: $value"
  }

  /* 
    * Exercise: Equality Type Class
    */
  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }

  object NameEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name
  }

  object AgeEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.age == b.age
  }

  object EmailEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.email == b.email
  }

  implicit object FullEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = NameEquality(a, b) && AgeEquality(a, b) && EmailEquality(a, b)
  }

  // part 2
  object HTMLSerializer {
    def serialize[T](value: T)(implicit serializer: HTMLSerializer[T]): String = {
      serializer.serialize(value)
    }

    def apply[T](implicit serializer: HTMLSerializer[T]) = serializer
  }

  implicit object IntSerializer extends HTMLSerializer[Int] {
    override def serialize(value: Int): String = s"<div style='color: blue;'>$value</div>"
  }

  println(HTMLSerializer.serialize(42))
  println(HTMLSerializer.serialize(john))

  // access to the entire type class interface
  println(HTMLSerializer[User].serialize(john))


  /*
    Exercise: implement the type class pattern for the Equality type class
   */
  object Equal {
      def apply[T](a: T, b: T)(implicit equalizer: Equal[T]): Boolean = equalizer.apply(a, b)
    }
  
  val anotherJohn = User("John", 45, "j@j.com")
  println(Equal(john, anotherJohn))

  // AD-HOC polymorphism
  println(john == anotherJohn) // println(Equal(john, anotherJohn))
}
