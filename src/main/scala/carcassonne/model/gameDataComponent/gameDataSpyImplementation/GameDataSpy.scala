package carcassonne.model.gameDataComponent.gameDataSpyImplementation

import carcassonne.model.gameDataComponent.*
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.*
import carcassonne.util.State
import play.api.libs.json.{Reads, Writes}

import scala.collection.immutable.Queue
import scala.xml.{Elem, Node}


class GameDataSpy(val map: TileMap = TileMap(),
                  val stack: Queue[Tile] = TileStack().construct(),
                  val players: Queue[PlayerState] = Queue(PlayerState()),
                  val turn: Int = 0,
                  val state: State = MenuState,
                  var startingTileCalls: Int = 0,
                  var initialStateCalls: Int = 0,
                  var withStateCalls: Int = 0,
                  var withTurnCalls: Int = 0,
                  var withMapCalls: Int = 0,
                  var currentTileCalls: Int = 0,
                  var deepCloneCalls: Int = 0)
  extends GameDataTrait {
  def startingTile(): TileTrait = {
    startingTileCalls += 1
    TileStack().startingTile
  }

  def initialState(): GameDataSpy = {
    initialStateCalls += 1
    this
  }

  def withState(newState: State): GameDataSpy = {
    withStateCalls += 1
    this
  }

  def withTurn(newTurn: Int): GameDataSpy = {
    withTurnCalls += 1
    this
  }

  def withMap(newMap: TileMapTrait): GameDataSpy = {
    withMapCalls += 1
    this
  }

  def currentTile(): TileTrait = {
    currentTileCalls += 1
    TileStack().startingTile
  }

  override def deepClone(): GameDataSpy = {
    deepCloneCalls += 1
    this
  }

  def toXML: Elem = {
    <GameData>
    </GameData>
  }

  def fromXML(node: Node): GameDataTrait = {
    new GameData()
  }

  override def reads: Reads[GameDataTrait] = {
    null.asInstanceOf[Reads[GameDataTrait]]
  }

  override def writes: Writes[GameDataTrait] = {
    null.asInstanceOf[Writes[GameDataTrait]]
  }

}
