package model

import scala.collection.immutable.Queue
import Color._

enum Color:
  case blue, red, green, yellow, black

class GameState(val map: TileMap, val stack: Queue[Tile], val players: Queue[PlayerState], val turn: Int) {
  /* also used as memento, since it is unchangeable */
  def activePlayer(): Color = {
    players(turn % players.size).color
  }
  def currentTile(): Tile = {
    stack(turn)
  }
}

class PlayerState(val meepleCount: Int = 7 , val color: Color = blue , val points: Int = 0) {}
