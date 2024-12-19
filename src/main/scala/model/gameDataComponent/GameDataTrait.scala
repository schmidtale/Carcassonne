package model.gameDataComponent

import model.gameDataComponent.{Index, Tile}
import util.{Prototype, State}

import scala.collection.immutable.{Queue, SortedMap}

trait GameDataTrait extends Prototype[GameDataTrait] {
  val map: TileMapTrait
  val stack: Queue[TileTrait]
  val players: Queue[PlayerTrait]
  val turn: Int
  val state: State

  def startingTile(): TileTrait
  def initialState(): GameDataTrait
  def withState(newState: State): GameDataTrait
  def withTurn(newTurn: Int) : GameDataTrait
  def withMap(newMap: TileMapTrait): GameDataTrait
  def currentTile(): TileTrait
}

trait TileTrait {
  val name: String
  def rotate(r: Int): Tile
}

trait TileMapTrait {
  val data: SortedMap[(Index, Index), Option[Tile]]
  def add(index1: Index, index2: Index, tile: Option[TileTrait]): TileMap
}

trait PlayerTrait {
  val meepleCount: Int
  val color: Color
  val points: Int
}

trait TextProviderTrait {
  def toText(c: TileTrait): String
}

