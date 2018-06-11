package pkm.other

import scala.util.Random

object Utils {
  def randomSampleWoReplace(len: Int, sampleSize: Int): Seq[Int] = {
    var randArray = (0 until len).toList

    for (i <- 1 to sampleSize) yield {
      val index = randArray(Random.nextInt(randArray.length))
      randArray = randArray.filter(_ != index)
      index
    }
  }
}
