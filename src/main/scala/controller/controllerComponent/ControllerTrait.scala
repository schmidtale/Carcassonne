package controller.controllerComponent

import model.gameDataComponent.{GameDataTrait}
import model.gameDataComponent.gameDataBaseImplementation.Index
import util.Observable

trait ControllerTrait extends Observable {
  var gameData: GameDataTrait

  def resetGameData(): Unit
  def undo() : Unit
  def redo() : Unit
  def addCurrentTile(index1: Index, index2: Index, rotation: Int): Unit
  def constructTabletopFromMap(): String
}
