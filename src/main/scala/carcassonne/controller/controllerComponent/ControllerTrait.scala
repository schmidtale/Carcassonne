package carcassonne.controller.controllerComponent

import carcassonne.model.gameDataComponent.GameDataTrait
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.Index
import carcassonne.util.Observable

trait ControllerTrait extends Observable {
  var gameData: GameDataTrait

  def resetGameData(): Unit
  def undo() : Unit
  def redo() : Unit
  def addCurrentTile(index1: Index, index2: Index, rotation: Int): Unit
  def constructTabletopFromMap(): String

  def save(): Unit
  def load(): Unit
}
