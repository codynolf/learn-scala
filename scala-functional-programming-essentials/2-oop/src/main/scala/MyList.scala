abstract class MyList[+A] {
  /* 
  * head = first element of this list
  * tail = remainder of the list
  * isEmpty = boolean
  * add(int) = returns new list with element added
  * toString = string of list
  *  */

  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](value: B): MyList[B]
  def printElements: String
  override def toString: String = "[" + printElements + "]"
}

object EmptyList extends MyList[Nothing]{
  def head: Nothing = throw new NoSuchElementException
  def tail: MyList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](value: B): MyList[B] = new Cons(value, EmptyList)
  def printElements: String = ""
}

class Cons[+A](h: A, t: MyList[A]) extends MyList[A]{
  def head: A = h
  def tail: MyList[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](value: B): MyList[B] = new Cons(value, this)
  def printElements: String = 
    if(t.isEmpty) "" + h
    else h + " " + t.printElements
}

object ListTest extends App {
    val list = new Cons(1, EmptyList)
    println(list.head)
    list.add(3)
    list.add(33)
    println(list.add(31423).head)
    println(list.tail.toString())
    println(list.toString())
}