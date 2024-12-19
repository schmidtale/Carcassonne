package model.gameDataComponent

import model.gameDataComponent.{Index, Tile}
import util.{Prototype, State}

import scala.collection.immutable.Queue

trait GameDataTrait extends Prototype[GameDataTrait] {
  val map: TileMap
  val stack: Queue[Tile]
  val players: Queue[PlayerState]
  val turn: Int
  val state: State

  def startingTile(): Tile
  def initialState(): GameDataTrait
  def withState(newState: State): GameDataTrait
  def withTurn(newTurn: Int) : GameDataTrait
  def withMap(newMap: TileMap): GameDataTrait
  def currentTile(): Tile
}

