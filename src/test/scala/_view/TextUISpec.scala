package _view

import controller.Tabletop
import model.Index
import model.TileMap
import model.BorderType.*
import model.Tile
import model.LiegemanPosition.*
import model.LiegemanType.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import java.io.{ByteArrayInputStream, InputStream}

class TextUISpec extends AnyWordSpec {
  val tile2 = new Tile(false, true, borders = Vector(town, pasture, pasture, town), (knight, north))
  val tabletop =  new Tabletop(new TileMap)
  val textUI = new TextUI(tabletop)

  "The TextUI" should {
//    "add a tile to the selected place and return the new mapping" in {
//      textUI.updateMap(true, Index(0), Index(0), tile2)
//      assert(tabletop.tileMap.equals()
//        equals(tabletop.addTileToMap(Index(0), Index(0), tile2, tabletop.emptyMap()).data))
//    }
//    "convert a user's command into placement information" in {
//      val originalIn: InputStream = System.in
//      val input1 = "0 5 14"
//      val input2 = "3 15 2" //not valid, index 15 does not exist
//      try {
//        System.setIn(new ByteArrayInputStream(input1.getBytes))
//        assert(readPlacement == (true, 0, Index(5), Index(14)))
//
//        System.setIn(new ByteArrayInputStream(input2.getBytes))
//        assert(readPlacement == (false, 0, Index(0), Index(0)))
//      } finally {
//        System.setIn(originalIn)
//      }
//    }
  }
}
