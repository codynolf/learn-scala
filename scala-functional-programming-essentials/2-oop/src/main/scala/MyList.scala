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

  // Higher order fucntions
  // - functions which take function as parameter or
  // - functions which return function as a result
  def map[B](transformer: A => B): MyList[B]
  def flatMap[B](transformer: A => MyList[B]): MyList[B]
  def filter(predicate: A => Boolean): MyList[A]
  def ++[B >: A](list: MyList[B]): MyList[B]
}

case object EmptyList extends MyList[Nothing]{
  def head: Nothing = throw new NoSuchElementException
  def tail: MyList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](value: B): MyList[B] = new Cons(value, EmptyList)
  def printElements: String = ""
  def map[B](transformer: Nothing => B): MyList[B] = EmptyList
  def flatMap[B](transformer: Nothing => MyList[B]): MyList[B] = EmptyList
  def filter(predicate: Nothing => Boolean): MyList[Nothing] = EmptyList
  def ++[B >: Nothing](list: MyList[B]): MyList[B] = list
}

case class Cons[+A](h: A, t: MyList[A]) extends MyList[A]{
  def head: A = h
  def tail: MyList[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](value: B): MyList[B] = new Cons(value, this)
  def printElements: String = 
    if(t.isEmpty) "" + h
    else String.valueOf(h) + " " + t.printElements
  def map[B](transformer: A => B): MyList[B] = {
    new Cons(transformer(h), t.map(transformer))
  }
  def flatMap[B](transformer: A => MyList[B]): MyList[B] = {
    transformer(h) ++ t.flatMap(transformer)
  }
  def filter(predicate: A => Boolean): MyList[A] = {
    if(predicate(head)) new Cons(h, t.filter(predicate))
    else t.filter(predicate)
  }
  def ++[B >: A](list: MyList[B]): MyList[B] = {
    new Cons(h, t ++ list)
  }
}

object ListTest extends App {
    val list = new Cons(1, new Cons(2, new Cons(3, EmptyList)))
    println(list.head)
    list.add(3)
    list.add(33)
    println(list.add(31423).head)
    println(list.tail.toString())
    println(list.toString())

    println("-----MAP-----")
    println(list.map(new (Int => Int){
      override def apply(elem: Int):Int = elem * 2
    }).toString)

    println("----- FlatMap -----")
    println(list.flatMap(new (Int => MyList[Int]) {
      override def apply(elem: Int): MyList[Int] = {
        new Cons(elem, new Cons(elem + 1, EmptyList))
      }
    }).toString)
}

