package pkm.models

import pkm.other.Utils

class VarStats(dresseur: Dresseur, nbRound: Int) {

  var currentLifeDrss = this.dresseur.hp
  var pokeOrder = Utils.randomSampleWoReplace(this.dresseur.pokedex.list.length, Math.min(nbRound, this.dresseur.pokedex.list.length))
  var currentLifePkm = this.dresseur.pokedex.list(pokeOrder.head).stats.hp


  def nextPkm = {
    val crPkmn = this.getCurrentPkmn
    if (this.pokeOrder.length > 1) {
      this.pokeOrder = this.pokeOrder.tail
      this.currentLifePkm = this.getCurrentPkmn.stats.hp
      println(crPkmn.name + " ==>> " + this.getCurrentPkmn.name + " " + this.currentLifePkm)
      true
    }

    else {

      false

    }
  }

  def printLineUp = {
    s"${this.dresseur.name} - PV: ${this.currentLifeDrss}" + "/" + s"${this.dresseur.hp} \n" +
      "LineUp: " +
      this.pokeOrder.map(i => s"Pokemon ${this.dresseur.pokedex.list(i).name} - PV: ${this.dresseur.pokedex.list(i).stats.hp}  ").mkString(", ")

  }

  override def toString = s"${this.dresseur.name} - PV: ${this.currentLifeDrss}" + "/" + s"${this.dresseur.hp} \n" +
    s"Pokemon ${this.getCurrentPkmn.name} - PV: ${this.currentLifePkm}" + "/" + s"${this.getCurrentPkmn.stats.hp}  Pkmn "+this.pokeOrder.length+"/"+nbRound


  def getDrssCurrentLife = {
    this.currentLifeDrss
  }

  def getPkmCurrentLife = {
    this.currentLifePkm
  }


  def getCurrentPkmn = {
    this.dresseur.pokedex.list(this.pokeOrder.head)
  }
}