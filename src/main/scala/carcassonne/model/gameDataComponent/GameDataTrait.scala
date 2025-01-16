package carcassonne.model.gameDataComponent

import carcassonne.model.gameDataComponent.gameDataBaseImplementation.Index
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.{Color, Index, Tile, TileMap}
import carcassonne.util.{Prototype, State}
import play.api.libs.json.{Reads, Writes}

import scala.collection.immutable.{Queue, SortedMap}
import scala.xml.Elem

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
  def toXML: Elem
  def fromXML(node: scala.xml.Node): GameDataTrait

  def reads: Reads[GameDataTrait]
  def writes: Writes[GameDataTrait]
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

