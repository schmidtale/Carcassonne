package model.gameDataStubImplementation

import model.gameDataComponent.*
import util.State

import scala.collection.immutable.Queue


class GameDataStub(val map: TileMap = TileMap(),
val stack: Queue[Tile] = TileStack().construct(),
val players: Queue[PlayerState] = Queue(PlayerState()),
val turn: Int = 0,
val state: State = MenuState)
extends GameDataTrait {
  def startingTile(): TileTrait = TileStack().startingTile

  def initialState(): GameDataStub = this

  def withState(newState: State): GameDataStub = this

  def withTurn(newTurn: Int): GameDataStub = this

  def withMap(newMap: TileMapTrait): GameDataStub = this

  def currentTile(): TileTrait = TileStack().startingTile
  
  override def deepClone(): GameDataStub = this

}
