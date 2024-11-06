package lectures.part4

object PimpMyLibrary extends App{
  
  // 2.isPrime
  implicit class RichInt(val value: Int) {
    def isEven: Boolean = value % 2 == 0
    def sqrt: Double = Math.sqrt(value)
    def times(function: () => Unit): Unit = {
      def timesAux(n: Int): Unit =
        if (n <= 0) ()
        else {
          function()
          timesAux(n - 1)
        }
      timesAux(value)
    }
    def *[T](list: List[T]): List[T] = {
      def concatenate(n: Int): List[T] =
        if (n <= 0) List()
        else concatenate(n - 1) ++ list
      concatenate(value)
    }
  }

  42.isEven // true
  42.sqrt // 6.48074069840786
  3.times(() => println("Scala Rocks!"))

  println(3 * List(1, 2)) // [1, 2, 1, 2, 1, 2]

  // Enrich String
  // asInt
  // encrypt
  // "John" -> Lqjp

  // Keep enriching the Int class
  // times(function)
  // *
  // println(3 * List(1, 2)) // [1, 2, 1, 2, 1, 2]

  implicit class RichString(string: String) {
    def asInt: Int = Integer.valueOf(string) // java.lang.Integer -> Int
    def encrypt(cypherDistance: Int): String = string.map(c => (c + cypherDistance).asInstanceOf[Char])
  }

  println("3".asInt + 4)
  println("John".encrypt(2)) 

  // "3" / 4
  implicit def stringToInt(string: String): Int = Integer.valueOf(string)
  println("6" / 2) // 3

  // danger zone
  implicit def intToBoolean(i: Int): Boolean = i == 1
  
}
