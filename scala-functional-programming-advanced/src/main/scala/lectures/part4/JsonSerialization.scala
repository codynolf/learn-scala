package lectures.part4

object JsonSerialization extends App {
  /* 
   *Users, posts, feeds
    *Serialize to JSON
   */

  case class User(name: String, age: Int, email: String)
  case class Post(content: String, createdAt: String)
  case class Feed(user: User, posts: List[Post])

  // 1 - intermediate data types: Int, String, List, Map
  // 2 - type classes for conversion to intermediate data types
  // 3 - serialize to JSON

  sealed trait JSONValue { // intermediate data type
    def stringify: String
  }

  // type class
  final case class JSONObject(values: Map[String, JSONValue]) extends JSONValue {
    override def stringify: String = values.map {
      case (key, value) => s""""$key": ${value.stringify}""" // partial function
    }.mkString("{", ",", "}")
  }

  final case class JSONString(value: String) extends JSONValue {
    override def stringify: String = s""""$value""""
  }

  final case class JSONNumber(value: Int) extends JSONValue {
    override def stringify: String = value.toString
  }

  final case class JSONArray(values: List[JSONValue]) extends JSONValue {
    override def stringify: String = values.map(_.stringify).mkString("[", ",", "]")
  }

  val data = JSONObject(Map(
    "user" -> JSONString("Daniel"),
    "posts" -> JSONArray(List(
      JSONString("Scala Rocks!"),
      JSONNumber(42)
    ))
  ))

  println(data.stringify)

  // type class
  // 1 - type class
  // 2 - type class instances (implicit)
  // 3 - pimp library to use type class instances

  // type class
  trait JSONConverter[T] {
    def convert(value: T): JSONValue
  }

  implicit object StringConverter extends JSONConverter[String] {
    override def convert(value: String): JSONValue = JSONString(value)
  }

  implicit object NumberConverter extends JSONConverter[Int] {
    override def convert(value: Int): JSONValue = JSONNumber(value)
  }

  implicit object UserConverter extends JSONConverter[User] {
    override def convert(user: User): JSONValue = JSONObject(Map(
      "name" -> JSONString(user.name),
      "age" -> JSONNumber(user.age),
      "email" -> JSONString(user.email)
    ))
  }

  implicit object PostConverter extends JSONConverter[Post] {
    override def convert(post: Post): JSONValue = JSONObject(Map(
      "content" -> JSONString(post.content),
      "created" -> JSONString(post.createdAt)
    ))
  }

  implicit object FeedConverter extends JSONConverter[Feed] {
    override def convert(feed: Feed): JSONValue = JSONObject(Map(
      "user" -> UserConverter.convert(feed.user),
      "posts" -> JSONArray(feed.posts.map(PostConverter.convert))
    ))
  }

  // conversion class
  implicit class JSONOps[T](value: T) {
    def toJson(implicit converter: JSONConverter[T]): JSONValue = converter.convert(value)
  }

  // call stringify on the result
  val now = System.currentTimeMillis().toString
  val john = User("John", 34, "j@j.com")
  val feed = Feed(john, List(
    Post("hello", now),
    Post("look at this cute puppy", now)
  ))


  println(feed.toJson.stringify)

}
