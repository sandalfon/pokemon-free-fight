package pkm.models

import io.circe.{Decoder, HCursor, KeyDecoder}
import pkm.data.Pokedex
import pkm.models


case class Dresseur(
                     number: Int,
                     name: String,
                     hp: Int,
                     pokedex: DresseurPokedex
                     //pokedex: List[Pokemon]

                   ) {
  // override def toString = s"""Dresseur $number "$name" - HP: $hp ${pokedexInt.toString}"""
}

case class PokedexInt(list: List[Int])

case class DresseurPokedex(list: List[Pokemon])


trait DresseurDecoder {

  import io.circe.generic.semiauto._

  val pokemons = Pokedex.pokemons
  //implicit protected val pokedexIntDecoder = deriveDecoder[PokedexInt]

  implicit val dresseurPokedexDecoder: Decoder[DresseurPokedex] = Decoder.instance(c =>
    for {
      t <- c.downField("list").as[List[Int]]
    } yield { DresseurPokedex(
      for{ i <- t}
        yield(pokemons.filter(p => p.number == i).head)
    )
    }
  )




 /* implicit val pokedexIntDecoder: Decoder[PokedexInt] = Decoder.instance(c =>
    for {
      t <- c.downField("list").as[List[Int]]
    } yield { PokedexInt(
      for{ i <- t}
        yield(i)
    )
    }
  )*/



  /*implicit val pokedexIntDecoder: Decoder[PokedexInt] = Decoder.instance(c =>
    for {
      t <- c.downField("list").as[List[Int]]
    } yield PokedexInt(t)
  )*/

  implicit protected val dresseurDecoder = Decoder.forProduct4("number", "name", "hp", "pokedex")(Dresseur.apply)

}