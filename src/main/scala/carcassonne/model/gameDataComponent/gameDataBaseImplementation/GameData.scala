package carcassonne.model.gameDataComponent.gameDataBaseImplementation

import carcassonne.model.gameDataComponent.gameDataBaseImplementation.Color.*
import carcassonne.model.gameDataComponent.*
import carcassonne.util.{Prototype, State}

import scala.collection.immutable.Queue
import scala.util.Random
import scala.xml.Elem

enum Color:
  case blue, red, green, yellow, black

class GameData(val map: TileMap = TileMap(),
               val stack: Queue[Tile] = Random.shuffle(TileStack().construct()),
               val players: Queue[PlayerState] = Queue(PlayerState(blue), PlayerState(red)),
               val turn: Int = 0,
               val state: State = MenuState)
  extends GameDataTrait {
  /* also used as memento, since it is unchangeable */
  def activePlayer(): Color = {
    players(turn % players.size).color
  }

  def currentTile(): Tile = {
    if (turn < stack.size) {
      return stack(turn)
    }
    TileStack().defaultTile
  }

  def startingTile(): Tile = {
    TileStack().startingTile
  }

  def withMap(newMap: TileMapTrait): GameData = {
    new GameData(newMap.asInstanceOf[TileMap], stack, players, turn, state)
  }

  def withState(newState: State): GameData = {
    new GameData(map, stack, players, turn, newState)
  }

  def withTurn(newTurn: Int): GameData = {
    new GameData(map, stack, players, newTurn, state)
  }

  def initialState(): GameData = {
    val initialMap = TileMap().add(Index(7), Index(7), Some(TileStack().startingTile))
    val newGameData = GameData(initialMap, stack, players, 0)
    newGameData
  }

  override def equals(obj: Any): Boolean = {
    obj match {
      case that: GameData =>
        this.map.data == that.map.data &&
          this.stack == that.stack &&
          this.players == that.players &&
          this.turn == that.turn
      case _ => false
    }
  }

  override def hashCode(): Int = (map, stack, players, turn).##

  override def deepClone(): GameData = {
    val gameData = GameData(this.map, this.stack, this.players, this.turn)
    gameData
  }

  def toXML: Elem = {
    <GameData>
      {map.toXML}<stack>
      {stack.map(tile => {
        tile.toXML
      }
      )}
    </stack>
      <players>
        {players.map(player => player.toXML)}
      </players>
      <turn>
        {turn}
      </turn>
      <state>
        {state.toString}
      </state>
    </GameData>
  }

  def fromXML(node: scala.xml.Node): GameData = {
    val mapNode = (node \ "tileMap").head
    val map = TileMap().fromXML(mapNode)

    val stackNodes = (node \ "stack" \ "tile")
    val stack = Queue(stackNodes.map(tileNode => Tile().fromXML(tileNode)): _*)

    val playerNodes = (node \ "players" \ "playerState")
    val players = Queue(playerNodes.map(playerNode => PlayerState().fromXML(playerNode)): _*)

    val turn = (node \ "turn").text.trim.toInt

    val stateString = (node \ "state").text.trim
    val state = stateString match {
      case "MenuState" => MenuState
      case "PlacingTileState" => PlacingTileState
      case "PlacingLiegemanState" => PlacingLiegemanState
      case "ReviewState" => ReviewState
      case _ => throw new IllegalArgumentException(s"Unknown state: $stateString")
    }

    new GameData(
      map,
      stack,
      players,
      turn,
      state
    )
  }
}

object GameData {

  import play.api.libs.json._

  implicit val gameDataWrites: Writes[GameData] = new Writes[GameData] {
    def writes(gameData: GameData): JsObject = Json.obj(
      "GameData" -> Json.obj(
        "map" -> Json.toJson(gameData.map),
        "stack" -> gameData.stack,
        "players" -> gameData.players,
        "turn" -> gameData.turn,
        "state" -> gameData.state.toString
      )
    )
  }

  implicit val gameDataReads: Reads[GameData] = new Reads[GameData] {
    def reads(json: JsValue): JsResult[GameData] = {
      (json \ "GameData").validate[JsObject].flatMap { gameDataJson =>
        for {
          map <- (gameDataJson \ "map").validate[TileMap]
          stack <- (gameDataJson \ "stack").validate[Seq[Tile]].map(Queue(_: _*))
          players <- (gameDataJson \ "players").validate[Seq[PlayerState]].map(Queue(_: _*))
          turn <- (gameDataJson \ "turn").validate[Int]
          stateString <- (gameDataJson \ "state").validate[String]
        } yield {
          val state = stateString match {
            case "MenuState" => MenuState
            case "PlacingTileState" => PlacingTileState
            case "PlacingLiegemanState" => PlacingLiegemanState
            case "ReviewState" => ReviewState
            case _ => throw new IllegalArgumentException(s"Unknown state: $stateString")
          }
          new GameData(
            map,
            stack,
            players,
            turn,
            state
          )
        }
      }
    }
  }
}


class PlayerState(val meepleCount: Int = 7, val color: Color = blue, val points: Int = 0) extends PlayerTrait {
  def this(color: Color) = {
    this(7, color, 0)
  }

  override def equals(obj: Any): Boolean = {
    obj match {
      case that: PlayerState =>
        this.meepleCount == that.meepleCount &&
          this.color == that.color &&
          this.points == that.points
      case _ => false
    }
  }

  override def hashCode(): Int = (meepleCount, color, points).##

  def toXML: Elem = {
    <playerState>
      <meepleCount>
        {meepleCount}
      </meepleCount>
      <color>
        {color}
      </color>
      <points>
        {points}
      </points>
    </playerState>
  }

  def fromXML(node: scala.xml.Node): PlayerState = {
    val meepleCount = (node \ "meepleCount").text.trim.toInt
    val color = Color.valueOf((node \ "color").text.trim)
    val points = (node \ "points").text.trim.toInt

    new PlayerState(
      meepleCount,
      color,
      points
    )
  }
}

object PlayerState {

  import play.api.libs.json._

  implicit val colorWrites: Writes[Color] = Writes[Color](c => JsString(c.toString))
  implicit val colorReads: Reads[Color] = Reads[Color](json => json.validate[String].map(Color.valueOf))

  implicit val playerStateWrites: Writes[PlayerState] = new Writes[PlayerState] {
    def writes(player: PlayerState): JsObject = Json.obj(
      "meepleCount" -> player.meepleCount,
      "color" -> player.color,
      "points" -> player.points
    )
  }

  implicit val playerStateReads: Reads[PlayerState] = new Reads[PlayerState] {
    def reads(json: JsValue): JsResult[PlayerState] = {
      for {
        meepleCount <- (json \ "meepleCount").validate[Int]
        color <- (json \ "color").validate[Color]
        points <- (json \ "points").validate[Int]
      } yield {
        new PlayerState(
          meepleCount,
          color,
          points
        )
      }
    }
  }
}

