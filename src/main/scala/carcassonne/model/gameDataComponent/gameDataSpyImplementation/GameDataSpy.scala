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
                  var deepCloneCalls: Int = 0,
                  var toXMLCalls: Int = 0,
                  var fromXMLCalls: Int = 0,
                  var readsCalls: Int = 0,
                  var writesCalls: Int = 0)
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
    toXMLCalls += 1
    <GameData>
    </GameData>
  }

  def fromXML(node: Node): GameDataSpy = {
    fromXMLCalls += 1
    new GameDataSpy()
  }

  override def reads: Reads[GameDataTrait] = {
    readsCalls += 1
    GameDataSpy.gameDataSpyReads.map(_.asInstanceOf[GameDataTrait])
  }

  override def writes: Writes[GameDataTrait] = {
    writesCalls += 1
    GameDataSpy.gameDataSpyWrites.contramap[GameDataTrait] {
      case gameDataSpy: GameDataSpy => gameDataSpy
    }
  }
}


object GameDataSpy {

  import play.api.libs.json._

  implicit val gameDataSpyWrites: Writes[GameDataSpy] = new Writes[GameDataSpy] {
    def writes(gameDataSpy: GameDataSpy): JsObject = Json.obj(
      "GameDataSpy" -> "GameDataSpy"
    )
  }

  implicit val gameDataSpyReads: Reads[GameDataSpy] = new Reads[GameDataSpy] {
    def reads(json: JsValue): JsResult[GameDataSpy] = {
      for {
        gameDataSpy <- (json \ "GameDataSpy").validate[String]
      } yield {
        new GameDataSpy()
      }
    }
  }
}
