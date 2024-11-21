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
      val stack2 = tileStack.construct()
      assert(stack == stack2 )
    }
    "return a starting tile" in {
      val starting_tile = tabletop.startingTile()
      assert(starting_tile == tileStack.startingTile)
    }
    "return a string of the tabletop" in {
      val tabletopString = Tabletop(new TileMap).constructTabletopFromMap()
      val tileMapString = TileMap().toString
      assert(tabletopString == tileMapString)
    }
    "change its TileMap to the initial map" in {
      tabletop.initialMap()
      assert(tabletop.tileMap.data(Index(7), Index(7)).get == tileStack.startingTile )
    }
  }
}
