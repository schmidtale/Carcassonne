package controller

import model.{GameData, Index, Tile, TileMap, TileStack}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.immutable.Queue

class TabletopSpec extends AnyWordSpec {
  val tabletop = new Tabletop(GameData())
  val tileStack = new TileStack
  private val tile = tileStack.startingTile
  private val emptyTile = Option.empty[Tile]
  private val emptyState = tabletop.gameData.map

  "A tabletop" should {
    "be able to return an initial GameData" in {
      val tabletop2 = new Tabletop(GameData().initialState())
      val oldState = tabletop2.gameData.deepClone()
      assert(tabletop2.gameData.equals(oldState))
      tabletop2.resetGameData()
      assert(tabletop2.gameData.equals(oldState))
    }
    "let a tile be added to the tile map" in {
      tabletop.addTileToMap(Index(7), Index(7), tile)
      val tileFromMap = tabletop.gameData.map.data((Index(7), Index(7))).get
      assert(tileFromMap.equals(tile))
    }
    "return an empty Tile when none is in the map" in {
      val tileFromMap = tabletop.gameData.map.data((Index(0), Index(0)))
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
      val tabletopString = Tabletop(GameData()).constructTabletopFromMap()
      val tileMapString = TileMap().toString
      assert(tabletopString == tileMapString)
    }
  }
}
