package controller

import model.{GameData, Index, Tile, TileMap, TileStack}
import util.{Observable, State, UndoManager}

import scala.collection.immutable.Queue

class Tabletop(var gameData: GameData) extends Observable {
  private val stack = new TileStack

  private val undoManager = new UndoManager
  
  def tileStack(): Queue[Tile] = {
    stack.construct()
  }
  def startingTile(): Tile = {
    stack.startingTile
  }

  def constructTabletopFromMap(): String = {
    gameData.map.toString
  }
  

  // Add Tile:
  // val newCard = Tile(...)
  // val updatedCardMap = cardMap + ((Index(5), Index(5)) -> Some(newCard))
  def addTileToMap(index1: Index, index2: Index, tile: Tile): Unit = {
    undoManager.doStep(new TurnCommand(index1, index2, tile, this))
    notifyObservers()
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
}
