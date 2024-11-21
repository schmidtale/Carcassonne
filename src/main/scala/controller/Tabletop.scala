package controller

import model.{Index, Tile, TileMap, TileStack}
import util.Observable

import scala.collection.immutable.Queue

class Tabletop(var tileMap: TileMap) extends Observable {
  private val stack = new TileStack
  
  def tileStack(): Queue[Tile] = {
    stack.construct()
  }
  def startingTile(): Tile = {
    stack.startingTile
  }

  def constructTabletopFromMap(): String = {
    tileMap.toString
  }

  // return an empty with empty Values
  def emptyMap(): TileMap = {
    TileMap()
  }

  // Add Tile:
  // val newCard = Tile(...)
  // val updatedCardMap = cardMap + ((Index(5), Index(5)) -> Some(newCard))
  def addTileToMap(index1: Index, index2: Index, card: Tile): Unit = {
    tileMap = TileMap(tileMap.data + ((index1, index2) -> Some(card)))
    notifyObservers()
  }

  def initialMap(): Unit = {
    tileMap = emptyMap()
    addTileToMap(Index(7), Index(7), startingTile())
    notifyObservers()
  }
  
}
