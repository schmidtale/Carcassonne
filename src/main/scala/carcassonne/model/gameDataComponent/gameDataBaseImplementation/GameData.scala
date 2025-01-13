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
    if(turn < stack.size) {
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

  def withState(newState: State) : GameData = {
    new GameData(map, stack, players, turn, newState)
  }
  
  def withTurn(newTurn: Int) : GameData = {
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
      <meepleCount>{meepleCount}</meepleCount>
      <color>{color}</color>
      <points>{points}</points>
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

