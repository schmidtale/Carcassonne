package controller.controllerComponent

import model.gameDataComponent.{GameDataTrait, Index, TileTrait}
import util.{State, UndoManager}

import scala.collection.immutable.Queue

class Tabletop(var gameData: GameDataTrait) extends ControllerTrait {
  private val undoManager = new UndoManager
  
  def tileStack(): Queue[TileTrait] = {
    gameData.stack
  }
  def startingTile(): TileTrait = {
    gameData.startingTile()
  }

  def constructTabletopFromMap(): String = {
    gameData.map.toString
  }
  

  // Add Tile:
  // val newCard = Tile(...)
  // val updatedCardMap = cardMap + ((Index(5), Index(5)) -> Some(newCard))
  def addTileToMap(index1: Index, index2: Index, tile: TileTrait): Unit = {
    undoManager.doStep(new TurnCommand(index1, index2, tile, this))
    notifyObservers()
  }

  def addCurrentTile(index1: Index, index2: Index, rotation: Int): Unit = {
    addTileToMap(index1, index2, gameData.currentTile().rotate(rotation))
  }

  def resetGameData(): Unit = {
    gameData = gameData.initialState()
    notifyObservers()
  }

  def undo() : Unit = {
    undoManager.undoStep()
    notifyObservers()
  }

  def redo() : Unit = {
    undoManager.redoStep()
    notifyObservers()
  }

  def changeState(state: State): Unit = {
    gameData = gameData.withState(state)
  }
  
  def incrementTurn(): Int = {
    gameData = gameData.withTurn(gameData.turn + 1)
    gameData.turn
  }
}
