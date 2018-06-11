package pkm.models

import org.xml.sax.ErrorHandler
import pkm.data.Dresseurdex
import pkm.models.Duel.{varStat1, varStat2}
import pkm.other.Utils

object Duel {
  val dresseurs = Dresseurdex.dresseurs
  val nbDresseur: Int = 2
  //nombre de round, le min entre nbRound et le min de pokemon des dresseurs
  val nbRound = 3

  val actionList: List[String] = List("Apkm", "Adrss", "Def")


  def initDresseurs = {
    val indexs = Utils.randomSampleWoReplace(Dresseurdex.dresseurs.length, nbDresseur)
    (dresseurs(indexs(0)), dresseurs(indexs(1)))
  }


  val (dresseur1, dresseur2) = initDresseurs

  val (varStat1, varStat2) = (new VarStats(dresseur1, nbRound), new VarStats(dresseur2, nbRound))


  def roundStart = {
    roundStep(false)

  }

  def roundStep(end: Boolean): Boolean = {
    println("*************")
    println("**  Round  **")
    println("*************")
    println(" ")
    println(" J1")
    println(varStat1.toString)
    println("-------------")
    println(" J2")
    println(varStat2.toString)
    println()
    println(end)
    println("\\\\\\\\\\\\\\")


    end match {
      case true => duelEnd
      case false => {
        val roundRes = roundResolution

        println("-------------")
        println("| Round end |")
        println("-------------")
        println(" ")
        println(" J1")
        println(varStat1.toString)
        println("-------------")
        println(" J2")
        println(varStat2.toString)
        println("")
        println("isvictory: " + roundRes)
        roundStep(roundRes)
      }
    }


  }

  def roundActions = {
    val act1 = actionList(Utils.randomSampleWoReplace(actionList.length, 1).head)
    val act2 = actionList(Utils.randomSampleWoReplace(actionList.length, 1).head)
    (act1, act2)
  }

  def roundResolution = {
    val (act1, act2) = roundActions
    println("action 1: " + act1 + "  Versus action2: " + act2)
    println("\\\\\\\\\\\\\\")

    (act1, act2) match {
      case ("Apkm", "Apkm") => apkmApkm
      case ("Apkm", "Adrss") => apkmAdrrs(varStat1, varStat2, "1->2")
      case ("Apkm", "Def") => apkmDef(varStat1, varStat2, "1->2")
      case ("Adrss", "Apkm") => apkmAdrrs(varStat2, varStat1, "2->1")
      case ("Adrss", "Adrss") => adrssAdrss
      case ("Adrss", "Def") => adrssDef(varStat1, varStat2, "1->2")
      case ("Def", "Apkm") => apkmDef(varStat2, varStat1, "2->1")
      case ("Def", "Adrss") => adrssDef(varStat2, varStat1, "2->1")
      case ("Def", "Def") => defDef
      case _ => throw new Exception("Actions inconnues")
    }
    isVictory
  }


  def isVictory: Boolean = {
    val (b1, b2) = isAllPkmKo
    val (d1, d2) = isDrssKo
    (b1, b2, d1, d2) match {
      case (false, false, false, false) => false
      case (_, _, _, _) => true
    }

  }


  def isAllPkmKo: (Boolean, Boolean) = {
    (varStat1.currentLifePkm, varStat2.currentLifePkm) match {

      case (0, 0) => (!varStat1.nextPkm, !varStat2.nextPkm)
      case (0, _) => (!varStat1.nextPkm, false)
      case (_, 0) => (false, !varStat2.nextPkm)
      case (_, _) => (false, false)
    }
  }

  def isDrssKo: (Boolean, Boolean) = {
    (varStat1.currentLifeDrss, varStat2.currentLifeDrss) match {
      case (0, 0) => (true, true)
      case (0, _) => (true, false)
      case (_, 0) => (false, true)
      case (_, _) => (false, false)
    }
  }


  def apkmApkm = {
    varStat1.currentLifePkm = Math.max(varStat1.currentLifePkm - varStat2.getCurrentPkmn.basicAttack.damage, 0)
    varStat2.currentLifePkm = Math.max(varStat2.currentLifePkm - varStat1.getCurrentPkmn.basicAttack.damage, 0)
  }

  def apkmAdrrs(v1: VarStats, v2: VarStats, order: String) = {
    val currentLifeDrss = Math.max(v1.currentLifeDrss - v2.getCurrentPkmn.basicAttack.damage, 0)
    val currentLifePkm = Math.max(v2.currentLifePkm - v1.getCurrentPkmn.basicAttack.damage, 0)
    order match {
      case "1->2" => {
        varStat1.currentLifeDrss = currentLifeDrss;
        varStat2.currentLifePkm = currentLifePkm
      }
      case "2->1" => {
        varStat2.currentLifeDrss = currentLifeDrss;
        varStat1.currentLifePkm = currentLifePkm
      }
      case _ => throw new Exception("Ordre inconnu")
    }
  }


  def defDef = {
    varStat1.currentLifePkm = Math.min(varStat1.currentLifePkm + (0.1 * varStat1.getCurrentPkmn.stats.hp).toInt, varStat1.getCurrentPkmn.stats.hp)
    varStat2.currentLifePkm = Math.min(varStat2.currentLifePkm + (0.1 * varStat2.getCurrentPkmn.stats.hp).toInt, varStat2.getCurrentPkmn.stats.hp)
  }

  def apkmDef(v1: VarStats, v2: VarStats, order: String) = {
    val currentLifePkm = Math.max(v2.currentLifeDrss - (0.75 * v1.getCurrentPkmn.basicAttack.damage).toInt, 0)
    order match {
      case "1->2" => {
        varStat2.currentLifePkm = currentLifePkm
      }
      case "2->1" => {
        varStat1.currentLifePkm = currentLifePkm
      }
      case _ => throw new Exception("Ordre inconnu")
    }

  }


  def adrssAdrss = {
    varStat1.currentLifeDrss = Math.max(varStat1.currentLifeDrss - varStat2.getCurrentPkmn.basicAttack.damage, 0)
    varStat2.currentLifeDrss = Math.max(varStat2.currentLifeDrss - varStat1.getCurrentPkmn.basicAttack.damage, 0)
  }


  def adrssDef(v1: VarStats, v2: VarStats, order: String) = {
    val currentLifePkm = Math.max(v1.currentLifePkm - (0.5 * v2.getCurrentPkmn.basicAttack.damage).toInt, 0)
    val currentLifeDrss = Math.max(v2.currentLifeDrss - (0.75 * v1.getCurrentPkmn.basicAttack.damage).toInt, 0)
    order match {
      case "1->2" => {
        varStat1.currentLifePkm = currentLifePkm;
        varStat2.currentLifeDrss = currentLifeDrss
      }
      case "2->1" => {
        varStat2.currentLifePkm = currentLifePkm;
        varStat1.currentLifeDrss = currentLifeDrss
      }
      case _ => throw new Exception("Ordre inconnu")
    }
  }


  def duelEnd: Boolean = {
    val (b1, b2) = isAllPkmKo
    (b1, b2) match {
      case (true, true) => println("DRAW!!!! Les 2 Dresseurs n'ont plus de Pokemons")
      case (true, _) => println(" Joueur 2 a éliminé tous les pokemons du Joueur 1")
      case (_, true) => println(" Joueur 1 a éliminé tous les pokemons du Joueur 2")
      case (_, _) => {
        val (d1, d2) = isDrssKo
        (d1, d2) match {
          case (true, true) => println("DRAW!!!! Les 2 Dresseurs sont épuisés")
          case (true, _) => println(" Joueur 2 a épuisé le Joueur 1")
          case (_, true) => println(" Joueur 1 a épuisé le Joueur 2")
          case (_, _) => throw new Exception("Condition de fin inconnue")
        }
      }

    }
    true
  }
  

  override def toString: String = "P1: \n" + varStat1 + "\nP2: \n" + varStat2

}
