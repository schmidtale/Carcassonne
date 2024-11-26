package controller

import model.{Index, Tile, TileMap, TileStack, GameState}
import util.Observable

import scala.collection.immutable.Queue

class Tabletop(var gameState: GameState) extends Observable {
  private val stack = new TileStack
  
  def tileStack(): Queue[Tile] = {
    stack.construct()
  }
  def startingTile(): Tile = {
    stack.startingTile
  }

  def constructTabletopFromMap(): String = {
    gameState.map.toString
  }
  

  // Add Tile:
  // val newCard = Tile(...)
  // val updatedCardMap = cardMap + ((Index(5), Index(5)) -> Some(newCard))
  def addTileToMap(index1: Index, index2: Index, tile: Tile): Unit = {
    val newMap = TileMap(gameState.map.data + ((index1, index2) -> Some(tile)))
    gameState = gameState.withMap(newMap)
    notifyObservers()
  }

  def resetGameState(): Unit = {
    gameState = gameState.initialState()
    notifyObservers()
  }
  
}
