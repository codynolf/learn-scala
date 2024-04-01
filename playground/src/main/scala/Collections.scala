object Collections extends App {
    case class SpatialRecord(geometry: Int, changePrevElementGeom: Int)

    // random int 0 or 1
    val randomInt = scala.util.Random.nextInt(2)
    
    // convert 0 or 1 to boolean
    val randomBool = randomInt match {
        case 0 => false
        case 1 => true
    }

    val spatialRecordsIterator = Iterator.tabulate(10)(i => SpatialRecord(i+scala.util.Random.nextInt(250), scala.util.Random.nextInt(2) match {
        case 0 => false
        case 1 => true
    }))

    val newList = spatialRecordsIterator
        .foldLeft(List.empty[SpatialRecord])((acc, sr) => {
            if(acc.isEmpty) {
                List(sr)
            } else {
                val last = acc.last
                if(last.changePrevGeom) {
                    // change the last element in the list
                    acc.dropRight(1) :+ last.copy(geometry = last.geometry + sr.geometry)
                    acc :+ sr.copy(geometry = last.geometry + sr.geometry)
                } else {
                    acc :+ sr
                }
            }
    })

    val bp = 0
}