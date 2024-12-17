package actors

import akka.actor._
import com.typesafe.config._

object IntroAkkaConfig extends App {

  class SimpleLoggingActor extends Actor with ActorLogging {
    override def receive: Receive = {
      case message => log.info(message.toString)
    }
  }

  /**
    * 1 - inline configuration
    * 
    */
  val configString =
    """
      | akka {
      |   loglevel = "ERROR"
      | }
    """.stripMargin
  val config = ConfigFactory.parseString(configString)
  val system = ActorSystem("ConfigurationDemo", ConfigFactory.load(config))

  val actor = system.actorOf(Props[SimpleLoggingActor]())

  actor ! "A message to remember"

  /**
    * 2 - config file
    */

  val defaultConfigFileSystem = ActorSystem("DefaultConfigFileDemo")
  val defaultConfigActor = defaultConfigFileSystem.actorOf(Props[SimpleLoggingActor]())
  defaultConfigActor ! "Remember me, I am the default config"

  /**
    * 3 - separate config in the same file
    */

  val specialConfig = ConfigFactory.load().getConfig("mySpecialConfig")
  val specialConfigSystem = ActorSystem("SpecialConfigDemo", specialConfig)
  val specialConfigActor = specialConfigSystem.actorOf(Props[SimpleLoggingActor]())
  specialConfigActor ! "Remember me, I am the special config"

  /**
    * 4 - separate config in another file
    */
    
  val separateConfig = ConfigFactory.load("secretFolder/secretConfiguration.conf")
  println(s"Separate config log level: ${separateConfig.getString("akka.loglevel")}")
  val separateConfigSystem = ActorSystem("SeparateConfigDemo", separateConfig)
  val separateConfigActor = separateConfigSystem.actorOf(Props[SimpleLoggingActor]())
  separateConfigActor ! "Remember me, I am the separate config"

  /**
    * 5 - different file formats
    * JSON, Properties
    */

  val jsonConfig = ConfigFactory.load("json/jsonConfig.json")
  println(s"Separate config log level: ${jsonConfig.getString("akka.loglevel")}")
  val jsonConfigSystem = ActorSystem("JsonConfigDemo", jsonConfig)
  val jsonConfigActor = jsonConfigSystem.actorOf(Props[SimpleLoggingActor]())
  jsonConfigActor ! "Remember me, I am the JSON config"

  val propsConfig = ConfigFactory.load("props/propsConfiguration.properties")
  println(s"Separate config log level: ${propsConfig.getString("akka.loglevel")}")
  val propsConfigSystem = ActorSystem("PropsConfigDemo", propsConfig)
  val propsConfigActor = propsConfigSystem.actorOf(Props[SimpleLoggingActor]())
  propsConfigActor ! "Remember me, I am the properties config"


}
