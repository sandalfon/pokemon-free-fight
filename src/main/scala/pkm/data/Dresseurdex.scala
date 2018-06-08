package pkm.data

import cats.data.Xor
import pkm.models.{Dresseur, DresseurDecoder}

import scala.io.Source


object Dresseurdex extends DresseurDecoder {

  val dresseurs = {
    val source = Source.fromURL(getClass.getClassLoader.getResource("dresseurs.json")).mkString
    io.circe.jawn.decode[Seq[Dresseur]](source) match {
      case Xor.Right(drss) => drss
      case Xor.Left(errors) => throw new RuntimeException(s"Cannot load dresseur: $errors")
    }
  }


}