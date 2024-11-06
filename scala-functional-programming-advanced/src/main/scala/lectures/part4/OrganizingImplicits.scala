package lectures.part4

object OrganizingImplicits extends App {
  
  implicit val reverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
  //implicit val normalOrdering: Ordering[Int] = Ordering.fromLessThan(_ < _)
  println(List(1, 4, 2, 3).sorted)

  // scala.Predef
  /*
    Implicits (used as implicit parameters):
      - val/var
      - object
      - accessor methods = defs with no parentheses
   */

  // Exercise
  case class Person(name: String, age: Int)

  val persons = List(
    Person("Steve", 30),
    Person("Amy", 22),
    Person("John", 66)
  )

  object Person {
    implicit val alphabeticalOrdering: Ordering[Person] = Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)
  }

  println(persons.sorted)

  /*
    Implicit scope
    - normal scope = LOCAL SCOPE
    - imported scope
    - companions of all types involved in the method signature
      - List
      - Ordering
      - all the types involved = A or any supertype
   */

  // def sorted[B >: A](implicit ord: Ordering[B]): List[B]

  /*
    Best practices
    - If you can edit the code for the type, use the COMPANION OBJECT
    - If ordering is only needed in a specific place, use LOCAL IMPLICITS
    - If you need different orderings at different place, use LOCAL IMPLICITS
   */

  // Exercise
  // 1. totalPrice = most used (50%)
  // 2. by unit count = 25%
  // 3. by unit price = 25%
  // Use Ordering

  case class Purchase(nUnits: Int, unitPrice: Double)

  val purchases = List(
    Purchase(2, 50),
    Purchase(4, 15),
    Purchase(3, 7)
  )

  object Purchase {
    implicit val totalPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan((a, b) => (a.nUnits * a.unitPrice) < (b.nUnits * b.unitPrice))
  }

  object UnitCountOrdering {
    implicit val unitCountOrdering: Ordering[Purchase] = Ordering.fromLessThan(_.nUnits < _.nUnits)
  }

  object UnitPriceOrdering {
    implicit val unitPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan(_.unitPrice < _.unitPrice)
  }


  println(purchases.sorted)
  import UnitCountOrdering._
  println(purchases.sorted)
  import UnitPriceOrdering._
  //println(purchases.sorted)
}
