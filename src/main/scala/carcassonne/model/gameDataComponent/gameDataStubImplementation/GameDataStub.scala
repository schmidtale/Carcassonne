package carcassonne.model.gameDataComponent.gameDataStubImplementation

import carcassonne.model.gameDataComponent.*
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.*
import carcassonne.util.State
import play.api.libs.json.{Reads, Writes}

import scala.collection.immutable.Queue
import scala.xml.{Elem, Node}


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

  def toXML: Elem = {
    <GameData>
    </GameData>
  }

  def fromXML(node: Node): GameDataTrait = {
    new GameData()
  }

  override def reads: Reads[GameDataTrait] = {
    GameDataSpy.gameDataStubReads.map(_.asInstanceOf[GameDataTrait])
  }

  override def writes: Writes[GameDataTrait] = {
    GameDataSpy.gameDataStubWrites.contramap[GameDataTrait] {
      case gameDataStub: GameDataStub => gameDataStub
    }
  }
}

object GameDataSpy {

  import play.api.libs.json._

  implicit val gameDataStubWrites: Writes[GameDataStub] = new Writes[GameDataStub] {
    def writes(gameDataStub: GameDataStub): JsObject = Json.obj(
      "GameDataStub" -> "GameDataStub"
    )
  }

  implicit val gameDataStubReads: Reads[GameDataStub] = new Reads[GameDataStub] {
    def reads(json: JsValue): JsResult[GameDataStub] = {
      for {
        GameDataStub <- (json \ "GameDataStub").validate[String]
      } yield {
        new GameDataStub()
      }
    }
  }
}
