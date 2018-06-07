package pkm

import scala.util.Random

import pkm.data.Pokedex
import pkm.data.Dresseurdex

object Main extends App {

  println(Pokedex.pokemons.mkString("\n"))

  val randomPokemon = Pokedex.pokemons(Random.nextInt(Pokedex.pokemons.length))
  println(s"Random: $randomPokemon")

  println(Dresseurdex.dresseurs.mkString("\n"))

  val randomDresseur = Dresseurdex.dresseurs(Random.nextInt(Dresseurdex.dresseurs.length))
  println(s"Random: $randomDresseur")


}
