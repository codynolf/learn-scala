import java.nio.ByteBuffer
import scodec.bits.{BitVector, ByteVector}

object Main extends App {
  val sectionControl = List(1431655765, 1431655764, 1431655760, 1431655744, 1431655680, 1431654400,
    1431568384, 1426063360, 0, 1, 0, 1342177280, 1431306240, 1431633920, 1431655424, 1431655680,
    1431655760, 1431655764, 1431655765)

  //sectionControl.map(convertToBooleanArray(_))
  //sectionControl.map(decimalToBinaryTwosComplement(_))
  sectionControl.map(longToBooleanArray(_))


  def longToBooleanArray(n: Int): Array[Boolean] = {
    val bits = new scala.collection.mutable.ArrayBuffer[Boolean]
    var x = n
    while (x != 0) {
      bits += ((x & 3) == 1) // Check if the two least significant bits are 01
      x = x >> 2 // Right-shift by two bits
    }
    println(bits.toArray.mkString(", "))
    bits.toArray
  }

  def convertToBooleanArray(sectionControlValue: Int) = {
    val arrayOfByte = ByteVector.fromInt(sectionControlValue).toArray

    val read_condensed_state_func: Array[Byte] => Array[Int] = (bytes: Array[Byte]) => {
      import scala.collection.immutable.BitSet
      if (bytes == null) {
        null
      } else {
        val recovered = BigInt(bytes).toLong
        val bits = BitSet.fromBitMask(Array(recovered))
        val states = (0 until 16)
          .map { number =>
            val first = bits(number * 2)
            val second = bits(number * 2 + 1)
            (first, second) match {
              case (false, false) => 0
              case (false, true)  => 1
              case (true, false)  => 2
              case (true, true)   => 3
            }
          }
        states.toArray
      }
    }

    val value = read_condensed_state_func(arrayOfByte)

    println(value.mkString(" "))
  }

  def decimalToBinaryTwosComplement(sectionControlValue: Int) = {
    // Convert the decimal value to binary as an unsigned value
    val binary = Integer.toBinaryString(sectionControlValue)
    println(binary)
    if (sectionControlValue >= 0) {
      // Positive value, pad with zeros to achieve the specified bit width
      val padZeros = "0" * (32 - binary.length)
      val rtn = padZeros + binary
      println(rtn)
      println(" ")
      rtn
    } else {
      // Negative value, perform 2's complement
      val inverted = binary.map {
        case '0' => '1'
        case '1' => '0'
      }
      val invertedPadded = "1" * (32 - inverted.length) + inverted
      println(invertedPadded)
    }
  }
}
