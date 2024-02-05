object Enums extends App{
  enum perms {
    case READ, WRITE
  } 

  println(perms.READ)

  enum permsint(v: Int) {
    case READ extends permsint(1)
    case WRITE extends permsint(2)
  }
  object permsint{
    def fromInt(v: Int): permsint = permsint.READ
  }

  // standard API
  // ordinals
  val permOrdinal = perms.READ.ordinal
  println(permOrdinal)
  // avilable enums
  val allPerms = perms.values
  println(allPerms)
  // derrive from string
  val readPerms: perms = perms.valueOf("READ")
  println(readPerms)
  val readBadPerms: perms = perms.valueOf("fsdafads") // blows up
  println(readBadPerms)

}
