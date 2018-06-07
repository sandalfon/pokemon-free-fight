package pkm.models

import io.circe.{Decoder, HCursor, KeyDecoder}
import pkm.data.Pokedex



case class Dresseur(
                     number: Int,
                     name: String,
                     hp: Int,
                     pokedex: PokedexInt
                     //pokedex: List[Pokemon]

                   ) {
  // override def toString = s"""Dresseur $number "$name" - HP: $hp ${pokedexInt.toString}"""
}

case class PokedexInt(list: List[Int])
//case class LocalPokedex(list : List[Pokemon])



trait DresseurDecoder {

  import io.circe.generic.semiauto._

  implicit protected val pokedexIntDecoder = deriveDecoder[PokedexInt]

  //val globalPokedex = Pokedex.pokemons
 /* implicit protected val localPokedexDecoder: Decoder[List[Pokemon]] = new Decoder[List[Pokemon]]{
    final def apply(c: HCursor): Decoder.Result[List[Pokemon]] =
       for {
        lst<- c.downField("list").as[List[Int]]
      }yield(for{
        i <- lst
      }yield(Pokedex.pokemons(i))
        )

  }*/

  implicit protected val dresseurDecoder = Decoder.forProduct4("number", "name", "hp", "pokedex")(Dresseur.apply)

}