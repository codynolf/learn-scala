package lectures

object PatternsEverywhere extends {
  try {

  } catch{
    case e: RuntimeException => "runtime"
    case npe: NullPointerException => "npe"
    case _ => "something else"
  }

  // catches are actuall matches
}
