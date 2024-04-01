package lectures

import java.util.Random
import scala.quoted.Expr
import scala.runtime.stdLibPatches.language.`3.3`

object PatternMatching extends App {
  // switch on steriods
  val random = new Random
  val x = random.nextInt(10)

  val desc = x match {
    case 1 => "one"
    case 2 => "two"
    case 3 => "three"
    case _ => "something else"
  }

  println(desc)

  // 1. decompose values
  // case classes can be desconstructed with pattern matching
  case class Person(name: String, age: Int)
  val bob = Person("bob", 20)

  /* 
    cases are matched in order, first pattern that matches will return expression
    what if no cases match when no default? Scala.MatchError
    will be unified type of all cases
    worked well with case class, becuase of extractor oob.
   */
  val greeting = bob match {
    case Person(n, a) if a < 21 => s"Hi my name is $n and I am $a years old. I cant drink in US."
    case Person(n, a) => s"Hi my name is $n and I am $a years old."
    case null => "Idk who i am"
  }

  println(greeting)

  // Patern matching on sealed hierarches
  sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Parrot(greeting: String) extends Animal

  val animal: Animal = Dog("Lab")

  // sealed classes will help cover your ass, compiler will warn if its sealed.
  animal match {
    case Dog(someBreed) => println(s"Dog of type $someBreed")
    case _ =>
  }

  // new students tend to match everything
  val isEven = x % 2 == 0
  // vs.
  val isEvenPM = x match {
    case n if n%2==0 => true
    case _: Int => false
  }// WHY!!?

  /* 
    Exercise

    simple function that takes Expr => human readable string

    examples:
      Sum(Number(2), Number(3)) => 2 + 3
      Sum(Number(2), Number(3), Number(4)) => 2 + 3 + 4
      Prod(Sum(Number(2), Number(1)), Number(3)) => (2+1)*3.3
      Sum(Prod(Number(2), Number(1)), Number(3)) => 2 * 1 + 3
   */

  trait Expr
  case class Number(n: Int) extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr

  val expr1 = Sum(Number(2), Number(3))
  //val expr2 = Sum(Number(2), Number(3), Number(4))
  val expr3 = Prod(Sum(Number(2), Number(1)), Number(3))
  val expr4 = Sum(Prod(Number(2), Number(1)), Number(3))

  def exprToString(expr: Expr):String =
    expr match {
      case Sum(Prod(Number(p1), Number(p2)), Number(s2)) => s"($p1 * $p2 + $s2)"
      case Sum(Number(e1), Number(e2)) => s"$e1 + $e2"
      case Prod(Sum(Number(s1), Number(s2)), Number(p2)) => s"($s1 + $s2) * $p2"
    }

  def show(expr: Expr):String =
    expr match {
      case Number(n) => s"$n"
      case Sum(e1, e2) => show(e1) + " + " + show(e2)
      case Prod(e1, e2) => {
        def maybeShowParens(expr: Expr) =
          expr match {
            case Prod(_,_) => show(expr)
            case Number(_) => show(expr)
            case _ => "(" + show(expr) + ")"
          }

        maybeShowParens(e1) + " * " + maybeShowParens(e2)
      }
    }

  println(exprToString(expr1))
  println(exprToString(expr3))
  println(exprToString(expr4))

  println(show(expr1))
  println(show(expr3))
  println(show(expr4))
  println(show(Prod(Sum(Number(2), Number(1)), Sum(Prod(Number(2), Number(1)), Number(1)))))
}
