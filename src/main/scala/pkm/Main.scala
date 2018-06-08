package pkm

import scala.util.Random

import pkm.data.Pokedex
import pkm.data.Dresseurdex
import pkm.models.VarStats
import pkm.models.Duel

object Main extends App {
/*
  println(Pokedex.pokemons.mkString("\n"))

  val randomPokemon = Pokedex.pokemons(Random.nextInt(Pokedex.pokemons.length))
  println(s"Random: $randomPokemon")

  println(Dresseurdex.dresseurs.mkString("\n"))

  val randomDresseur = Dresseurdex.dresseurs(Random.nextInt(Dresseurdex.dresseurs.length))
  println(s"Random: $randomDresseur")
*/
  val randomVarStat = new VarStats(Dresseurdex.dresseurs(Random.nextInt(Dresseurdex.dresseurs.length)),3)
  println(s"Random: $randomVarStat ${randomVarStat.pokeOrder}" )
  println(s"Random: ${randomVarStat.printLineUp}" )
  println(Duel.toString)

}
