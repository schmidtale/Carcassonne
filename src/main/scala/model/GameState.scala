package model

import scala.collection.immutable.Queue
import Color.*
import util.Prototype

import scala.util.Random

enum Color:
  case blue, red, green, yellow, black

class GameState(val map: TileMap = TileMap(),
                val stack: Queue[Tile] = Random.shuffle(TileStack().construct()),
                val players: Queue[PlayerState] = Queue(PlayerState(blue), PlayerState(red)),
                val turn: Int = 0) extends Prototype[GameState] {
  /* also used as memento, since it is unchangeable */
  def activePlayer(): Color = {
    players(turn % players.size).color
  }
  def currentTile(): Tile = {
    stack(turn)
  }
  def withMap(newMap: TileMap): GameState = {
    new GameState(newMap, stack, players, turn)
  }
  def initialState(): GameState = {
    val newGameState = new GameState(TileMap(), stack, players, 0)
    val newMap = TileMap(newGameState.map.data + ((Index(7), Index(7)) -> Some(TileStack().startingTile)))
    withMap(newMap)
  }
  
  override def equals(obj: Any): Boolean = {
    obj match {
      case that: GameState =>
        this.map == that.map &&
          this.stack == that.stack &&
          this.players == that.players &&
          this.turn == that.turn
      case _ => false
    }
  }

  override def deepClone(): GameState = {
    val gameState = GameState(this.map, this.stack, this.players, this.turn)
    gameState
  }
}

class PlayerState(val meepleCount: Int = 7 , val color: Color = blue , val points: Int = 0) {
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
  
}
