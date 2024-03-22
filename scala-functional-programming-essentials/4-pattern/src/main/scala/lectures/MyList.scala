package lectures

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
  def foreach(f: A => Unit): Unit
  def sort(f: (A, A) => Int): MyList[A]
  def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C]
  def fold[B](start: B)(operator: (B, A)=>B): B
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
  def foreach(f: Nothing => Unit): Unit = ()
  def sort(f: (Nothing, Nothing) => Int): MyList[Nothing] = EmptyList
  def zipWith[B, C](list: MyList[B], zip: (Nothing, B) => C): MyList[C] =
    if(!list.isEmpty) throw new RuntimeException("Lists do not have the same length")
    else EmptyList
  def fold[B](start: B)(operator: (B, Nothing) => B): B = start
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
  def foreach(f: A => Unit): Unit =
    f(h)
    t.foreach(f)

  def sort(f: (A, A) => Int): MyList[A] = {
    def insert(x: A, sortedList: MyList[A]): MyList[A] =
      if(sortedList.isEmpty) new Cons(x, EmptyList)
      else if (f(x, sortedList.head) <= 0) new Cons(x, sortedList) 
      else new Cons(sortedList.head, insert(x, sortedList.tail))

    val sortedTail = t.sort(f)
    insert(h, sortedTail)
  }

  def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C] =
    if(list.isEmpty) throw new RuntimeException("Lists do not have the same length")
    else new Cons(zip(h, list.head), t.zipWith(list.tail, zip))

  def fold[B](start: B)(operator: (B, A) => B): B = 
    t.fold(operator(start, h))(operator)

  def ++[B >: A](list: MyList[B]): MyList[B] = {
    new Cons(h, t ++ list)
  }
}

object ListTest extends App {
    val list = new Cons(1, new Cons(2, new Cons(3, EmptyList)))
    val list2 = new Cons("four", new Cons("five", new Cons("six", EmptyList)))
    println(list.head)
    list.add(3)
    list.add(33)
    println(list.add(31423).head)
    println(list.tail.toString())
    println(list.toString())

    println("----- map -----")
    println(list.map(_ * 2).toString)

    println("----- flatMap -----")
    println(list.flatMap(x => new Cons(x, new Cons(x + 1, EmptyList))).toString)

    println("----- foreach ------")
    list.foreach(println)

    println("----- sort -----")
    println(list.sort((x, y) => y-x))

    println("----- zipWith ----")
    println(list.zipWith(list2, _ + "-" + _))

    println("----- fold -----")
    println(list.fold(0)(_ + _))

    println("----- For Comphrehension -----")
    val forCombination = for {
      l1 <- list
      l2 <- list2
    } yield l1 + "" + l2
    println(forCombination)
}

