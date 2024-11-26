package model

import model.Color._
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class GameStateSpec extends AnyWordSpec {
  "A GameState" should {
    val game = GameState()
    "return the color of the active Player" in {
      assert(game.activePlayer().equals(blue))
      val game1 = GameState(turn = 1)
      assert(game1.activePlayer().equals(red))
    }
    "return a different reference to current tile based on the turn" in {
      assert(game.currentTile().isInstanceOf[Tile])
      val gameTurn1 = GameState(turn = 20)
      val gameTurn0 = gameTurn1.initialState()
      assert(gameTurn1.currentTile() != gameTurn0.currentTile())
    }
    "have reflexive equals" in {
      assert(game.equals(game))
    }
    "have symmetric equals" in {
      val game1 = game.deepClone()
      assert(game.equals(game1) && game1.equals(game))
    }
    "have transitive equals" in {
      val game1 = game.deepClone()
      val game2 = game.deepClone()
      assert(game.equals(game1) && game1.equals(game2) && game.equals(game2))
    }
    "return false if you compare a GameState to null" in {
      assert(!game.equals(null))
    }
    "return false if you compare a GameState to something else" in {
      val that = "empty"
      assert(!game.equals(that))
    }
    "return false if you compare two different GameStates" in {
      val game1 = GameState(turn = 1)
      assert(!game.equals(game1))
    }
    "return the same hashcode for equal GameStates" in {
      val game1 = game.deepClone()
      assert(game.hashCode() == game1.hashCode())
    }
    "provide an unchanging reference to its current data" in {
      var game = GameState()
      val clone = game.deepClone()
      assert(game.equals(clone))
      
      game = GameState()
      assert(game != clone)
    }
  }
  "A PlayerState" should {
    val player = PlayerState()
    "have reflexive equals" in {
      assert(player.equals(player))
    }
    "have symmetric equals" in {
      val player1 = PlayerState()
      assert(player.equals(player1) && player1.equals(player))
    }
    "have transitive equals" in {
      val player1 = PlayerState()
      val player2 = PlayerState()
      assert(player.equals(player1) && player1.equals(player2) && player.equals(player2))
    }
    "return false if you compare a player to null" in {
      assert(!player.equals(null))
    }
    "return false if you compare a player to something else" in {
      val that = "empty"
      assert(!player.equals(that))
    }
    "return false if you compare two different players" in {
      val player1 = PlayerState(red)
      assert(!player.equals(player1))
    }
    "return the same hashcode for equal players" in {
      val player1 = PlayerState()
      assert(player.hashCode() == player1.hashCode())
    }
  }
}
