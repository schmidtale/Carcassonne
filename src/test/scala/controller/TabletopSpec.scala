package controller

import model.{GameState, Index, Tile, TileMap, TileStack}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.immutable.Queue

class TabletopSpec extends AnyWordSpec {
  val tabletop = new Tabletop(GameState())
  val tileStack = new TileStack
  private val tile = tileStack.startingTile
  private val emptyTile = Option.empty[Tile]
  private val emptyState = tabletop.gameState.map

  "A tabletop" should {
    "be able to return an initial GameState" in {
      val tabletop2 = new Tabletop(GameState().initialState())
      val oldState = tabletop2.gameState.deepClone()
      assert(tabletop2.gameState.equals(oldState))
      tabletop2.resetGameState()
      assert(tabletop2.gameState.equals(oldState))
    }
    "let a tile be added to the tile map" in {
      tabletop.addTileToMap(Index(7), Index(7), tile)
      val tileFromMap = tabletop.gameState.map.data((Index(7), Index(7))).get
      assert(tileFromMap.equals(tile))
    }
    "return an empty Tile when none is in the map" in {
      val tileFromMap = tabletop.gameState.map.data((Index(0), Index(0)))
      assert(tileFromMap == emptyTile)
    }
    "return its internal TileStack" in {
      val stack = tabletop.tileStack()
      val stack2 = tileStack.construct()
      assert(stack == stack2 )
    }
    "return a starting tile" in {
      val starting_tile = tabletop.startingTile()
      assert(starting_tile == tileStack.startingTile)
    }
    "return a string of the tabletop" in {
      val tabletopString = Tabletop(GameState()).constructTabletopFromMap()
      val tileMapString = TileMap().toString
      assert(tabletopString == tileMapString)
    }
  }
}
