import java.nio.ByteBuffer

object Main extends App {
  val intValue = 1431655765  // Replace with your integer value
  val binaryString = Integer.toBinaryString(intValue)

  println(s"Integer: $intValue")
  println(s"Binary: $binaryString")

  val inputString = "abcdefghij"

  // Use `sliding` to create pairs of characters and `map` to apply a function
  val result = inputString.sliding(2, 2).map { pair =>
    // `pair` contains two characters, and you can perform some operation on them
    // For this example, we simply return the pair as a string
    pair
  }.toList

  result.foreach(println)


  def decimalToBinaryTwosComplement(decimalValue: Int, bitWidth: Int): String = {
  // Convert the decimal value to binary as an unsigned value
  val binary = Integer.toBinaryString(decimalValue)
  println(binary)
  if (decimalValue >= 0) {
    // Positive value, pad with zeros to achieve the specified bit width
    val padZeros = "0" * (bitWidth - binary.length)
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
    val invertedPadded = "1" * (bitWidth - inverted.length) + inverted
    invertedPadded
  }
}

  
  val bitWidth = 32

  val sectionControl = List(1431655765, 1431655764, 1431655760,1431655744,1431655680,1431654400,1431568384,1426063360,0,1,0,1342177280,1431306240,1431633920,1431655424,1431655680,1431655760,1431655764,1431655765)

  sectionControl.map(decimalToBinaryTwosComplement(_, bitWidth))

  println("-----")

  decimalToBinaryTwosComplement(-235, bitWidth)

  val bp = 5;
}