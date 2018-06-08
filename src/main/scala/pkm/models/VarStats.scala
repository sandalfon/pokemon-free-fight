package pkm.models

import pkm.other.Utils

class VarStats(dresseur: Dresseur, nbRound: Int) {

  var currentLifeDrss = this.dresseur.hp
  var pokeOrder = Utils.randomSampleWoReplace( this.dresseur.pokedex.list.length, Math.min(nbRound, this.dresseur.pokedex.list.length)).map(i => i - 1)
  var currentLifePkm = this.dresseur.pokedex.list(pokeOrder(0)).stats.hp



  def nextPkm = {
    if (this.pokeOrder.length > 0) {
      this.pokeOrder = this.pokeOrder.drop(1)
      this.currentLifePkm = this.dresseur.pokedex.list(this.pokeOrder(0)).stats.hp
    }
    else
      None
  }

  def printLineUp = {
    s"${this.dresseur.name} - PV: ${this.currentLifeDrss}" + "/" + s"${this.dresseur.hp} \n"+
      "LineUp: " +
    this.pokeOrder.map(i => s"Pokemon ${this.dresseur.pokedex.list(i).name} - PV: ${this.dresseur.pokedex.list(i).stats.hp}  ").mkString(", ")

  }

  override def toString = s"${this.dresseur.name} - PV: ${this.currentLifeDrss}" + "/" + s"${this.dresseur.hp} \n" +
    s"Pokemon ${this.dresseur.pokedex.list(this.pokeOrder(0)).name} - PV: ${this.currentLifePkm}" + "/" + s"${this.dresseur.pokedex.list(this.pokeOrder(0)).stats.hp}  "
}