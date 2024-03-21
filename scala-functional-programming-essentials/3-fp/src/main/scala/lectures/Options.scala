package lectures

import java.util.Random

object Options extends App {
  val myFirstOption: Option[Int] = Some(4)
  val noOption: Option[Int] = None

  println(myFirstOption)

  // Unsafe APIs
  def unsafeMethod(): String = null
  val result= Some(unsafeMethod()) // WRONG, becuase it returns null, some should also have a valid value

  val resultGood = Option(unsafeMethod()) // option will take care of some or none
  println(resultGood)

  // chained methods
  def backupMethod(): String = "A valid result."
  val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod()))
  println(chainedResult);

  // DESIGN unsafe APIs
  def betterUnsafeMethod(): Option[String] = None
  def betterBackupMethod(): Option[String] = Some("A valid result")

  val betterChainedResult = betterUnsafeMethod() orElse betterBackupMethod()

  // functions on Options
  println(myFirstOption.isEmpty) // false, good way to check if option has a value
  println(myFirstOption.get) // UNSAFE, do not use this, breaks the whole option idea

  // map, flatMap, filter
  println(myFirstOption.map(_ * 2)) // some(8)
  println(noOption.map(_*2)) // returns none
  println(myFirstOption.filter(x => x > 10)) // returns none
  println(myFirstOption.flatMap(x => Option(x*10))) // some(40)


  /* 
      excercises
      
   */

   val config: Map[String, String] = Map(
    "host" -> "127.0.0.1",
    "port" -> "80"
   )

   class Connection {
    def connect = "Connected"

   }

   object Connection {
    val random = new Random(System.nanoTime())
    def apply(host: String, port: String): Option[Connection] = 
      if(random.nextBoolean()) Some(new Connection)
      else None
   }

   // try to establish connection, if so - print the connect method
   val host = config.get("host")
   val port = config.get("port")
   val connection = host.flatMap(h => port.flatMap(p => Connection.apply(h, p)))
   val connectionStatus = connection.map(c => c.connect)

   connectionStatus.foreach(println)

   // chained implementation
   config.get("host")
    .flatMap(h => config.get("port")
      .flatMap(p => Connection.apply(h, p))
      .map(connection => connection.connect))
    .foreach(println)

    // for-comphrehension
    val status = for{
      host <- config.get("host")
      port <- config.get("port")
      connection <- Connection.apply(host, port)
    } yield connection.connect

    status.foreach(println)
}
