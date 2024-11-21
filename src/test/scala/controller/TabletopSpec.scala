package controller

import model.Tile
import model.TileStack
import model.TileMap
import model.Index
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.immutable.{Queue, SortedMap}

class TabletopSpec extends AnyWordSpec {
  val tabletop = new Tabletop(new TileMap)
  val tileStack = new TileStack
  private val tile = tileStack.startingTile
  private val emptyTile = Option.empty[Tile]
  private val emptyMap = tabletop.emptyMap()

  "A tabletop" should {
    "construct the correct tabletop grid as a String" in {
      val expectedOutput = (
          "\n\n   0 0       0 1       0 2       0 3    \n\n\n" +
          "\n\n   1 0       1 1       1 2       1 3    \n\n\n" +
          "\n\n   2 0       2 1       2 2       2 3    \n\n\n" +
          "\n\n   3 0       3 1       3 2       3 3    \n\n\n"
        )
      assert(tabletop.constructTabletop() == expectedOutput)
    }
    "be able to return a map with empty values" in {
      assert(tabletop.emptyMap().isInstanceOf[TileMap])
      assert(emptyMap.data(Index(0), Index(0)).isEmpty)
    }
    "let a tile be added to the tile map" in {
      tabletop.addTileToMap(Index(7), Index(7), tile)
      val tileFromMap = tabletop.tileMap.data((Index(7), Index(7))).get
      assert(tileFromMap.equals(tile))
    }
    "return an empty Tile when none is in the map" in {
      val tileFromMap = tabletop.tileMap.data((Index(0), Index(0)))
      assert(tileFromMap == emptyTile)
    }
    "return its internal TileStack" in {
      val stack = tabletop.tileStack()
      assert(stack.isInstanceOf[Queue[Tile]] )
    }
    "return a starting tile" in {
      val starting_tile = tabletop.startingTile()
      assert(starting_tile == tileStack.startingTile)
    }
    "return a string of the tabletop" in {
      val tabletopString = tabletop.constructTabletopFromMap()
      assert(tabletopString.isInstanceOf[String])
    }
    "change its TileMap to the initial map" in {
      tabletop.initialMap()
      assert(tabletop.tileMap.data(Index(7), Index(7)).get == tileStack.startingTile )
    }
  }
}
