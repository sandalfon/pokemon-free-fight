package pkm.models

import pkm.data.Dresseurdex
import pkm.other.Utils

object Duel {
  val dresseurs = Dresseurdex.dresseurs
  val nbDresseur: Int = 2
  //nombre de round, le min entre nbRound et le min de pokemon des dresseurs
  val nbRound = 3

  val actionList: List[String] = List("Apkm", "Adrss","Def")




  def initDresseurs = {
    val indexs = Utils.randomSampleWoReplace(Dresseurdex.dresseurs.length, nbDresseur).map(i => i-1)
    (dresseurs(indexs(0)), dresseurs(indexs(1)))
  }



  val (dresseur1,dresseur2) = initDresseurs

  val (vartStat1, varStat2) = (new VarStats(dresseur1, nbRound), new VarStats(dresseur2,nbRound))

  def roundStart ={

  }

  def roundStep ={

  }

  def roundActions = {}

  def roundResolution ={}


  override def toString: String = "P1: \n"+vartStat1+"\nP2: \n"+varStat2

}
