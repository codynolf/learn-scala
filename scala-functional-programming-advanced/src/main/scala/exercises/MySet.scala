package exercises

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import lectures.part1.Recap.x

trait MySet[A] extends (A => Boolean) {
  override def apply(v1: A): Boolean = contains(v1)
  def contains(elem: A): Boolean
  def +(elem: A): MySet[A]
  def ++(anotherSet: MySet[A]): MySet[A]
  def map[B](f: A => B): MySet[B]
  def flatMap[B](f: A => MySet[B]): MySet[B]
  def filter(predicate: A => Boolean): MySet[A]
  def foreach(f: A => Unit): Unit
  def -(elem: A): MySet[A]
  def --(anotherSet: MySet[A]): MySet[A]
  def &(anotherSet: MySet[A]): MySet[A]
  def unary_! : MySet[A]
}

class EmptySet[A] extends MySet[A] {
  override def contains(elem: A): Boolean = false
  override def +(elem: A): MySet[A] = new NonEmptySet[A](elem, this)
  override def ++(anotherSet: MySet[A]): MySet[A] = anotherSet
  override def map[B](f: A => B): MySet[B] = new EmptySet[B]
  override def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptySet[B]
  override def filter(predicate: A => Boolean): MySet[A] = this
  override def foreach(f: A => Unit): Unit = ()
  override def -(elem: A): MySet[A] = this
  override def --(anotherSet: MySet[A]): MySet[A] = this
  override def &(anotherSet: MySet[A]): MySet[A] = this
  override def unary_! : MySet[A] = new PropertyBasedSet[A](_ => true)
}

class NonEmptySet[A](head: A, tail: MySet[A]) extends MySet[A] {
  override def contains(elem: A): Boolean = elem == head || tail.contains(elem)
  override def +(elem: A): MySet[A] = if (this.contains(elem)) this else new NonEmptySet[A](elem, this)

  /* 
  [1 2 3] ++ [4 5]
  [2 3] ++ [4 5] + 1
  [3] ++ [4 5] + 1 + 2
  [] ++ [4 5] + 1 + 2 + 3
  [4 5] + 1 + 2 + 3
  [4 5 1 2 3]
   */

  override def ++(anotherSet: MySet[A]): MySet[A] = tail ++ anotherSet + head
  override def map[B](f: A => B): MySet[B] = tail.map(f) + f(head)
  override def flatMap[B](f: A => MySet[B]): MySet[B] = f(head) ++ tail.flatMap(f)
  override def filter(predicate: A => Boolean): MySet[A] = {
    val filteredTail = tail.filter(predicate)
    if (predicate(head)) filteredTail + head else filteredTail
  }
  override def foreach(f: A => Unit): Unit = {
    f(head)
    tail.foreach(f)
  }

  override def -(elem: A): MySet[A] = if (head == elem) tail else tail - elem + head
  override def --(anotherSet: MySet[A]): MySet[A] = filter(!anotherSet)
  override def &(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)
  override def unary_! : MySet[A] = new PropertyBasedSet[A](x => !this.contains(x))
}

// all elements of type A which satisfy a property
// { x in A | property(x) }
// I have no fucking clue, need to come back to this at somepoint. Lectures 10, 11, 12
class PropertyBasedSet[A](property: A => Boolean) extends MySet[A] {
  override def contains(elem: A): Boolean = property(elem)
  // { x in A | property(x) } + elem = { x in A | property(x) || x == elem }
  override def +(elem: A): MySet[A] = new PropertyBasedSet[A](x => property(x) || x == elem)
  override def ++(anotherSet: MySet[A]): MySet[A] = new PropertyBasedSet[A](x => property(x) || anotherSet(x))
  override def map[B](f: A => B): MySet[B] = politelyFail
  override def flatMap[B](f: A => MySet[B]): MySet[B] = politelyFail
  override def filter(predicate: A => Boolean): MySet[A] = new PropertyBasedSet[A](x => property(x) && predicate(x))
  override def foreach(f: A => Unit): Unit = politelyFail
  override def -(elem: A): MySet[A] = filter(_ != elem)
  override def --(anotherSet: MySet[A]): MySet[A] = filter(!anotherSet)
  override def &(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)
  override def unary_! : MySet[A] = new PropertyBasedSet[A](x => !property(x))

  def politelyFail = throw new IllegalArgumentException("Really deep rabbit hole!")
}

object MySet {
  def apply[A](values: A*): MySet[A] = {
    @tailrec
    def buildSet(valSeq: Seq[A], acc: MySet[A]): MySet[A] = {
      if (valSeq.isEmpty) acc
      else buildSet(valSeq.tail, acc + valSeq.head)
    }

    buildSet(values.toSeq, new EmptySet[A])
  }
}

object MySetTest extends App {
  val s = MySet(1, 2, 3, 4)
  //s + 5 ++ MySet(-1, -2) + 3 map (x => x * 10) foreach println

  val negative = !s // s.unary_! = all the naturals not equal to 1, 2, 3, 4
  println(negative(2))
  println(negative(5))

  val negativeEven = negative.filter(_ % 2 == 0)
  println(negativeEven(5))
  val negativeEven5 = negativeEven + 5
  println(negativeEven5(5))
}
