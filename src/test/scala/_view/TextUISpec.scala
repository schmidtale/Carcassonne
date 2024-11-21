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
import org.scalatest.concurrent.TimeLimits
import org.scalatest.concurrent.TimeLimits.failAfter
import org.scalatest.time.{Seconds, Span}


class TextUISpec extends AnyWordSpec {
  val tile2 = new Tile(false, true, borders = Vector(town, pasture, pasture, town), (knight, north))
  val tabletop =  new Tabletop(new TileMap)
  val textUI = new TextUI(tabletop)

  def readPlacementFrom(input: String): (Boolean, Int, Index, Index) = {
    val originalIn = System.in
    try {
      System.setIn(new ByteArrayInputStream(input.getBytes))
      textUI.readPlacement
    } finally {
      System.setIn(originalIn)
    }
  }

  "The TextUI" should {
    "add a tile to the selected place and return the new mapping" in {
      textUI.updateMap(true, Index(0), Index(0), tile2)
      assert(tabletop.constructTabletopFromMap().startsWith("* B B B *"))
      assert(tabletop.constructTabletopFromMap().contains("B . . . ."))
    }
//    "convert a user's command into placement information" in {
//      failAfter(Span(12, Seconds)) {
//        assert(readPlacementFrom("0 5 14\n") == (true, 0, Index(5), Index(14)))
//        assert(readPlacementFrom("3 15 2\n") == (false, 0, Index(0), Index(0)))
//      }


  }
}

//      val originalIn: InputStream = System.in
//      val input1 = "0 5 14\n"
//      val input2 = "3 15 2\n" //not valid, index 15 does not exist
//      try {
//        System.setIn(new ByteArrayInputStream(input1.getBytes))
//        assert(textUI.readPlacement == (true, 0, Index(5), Index(14)))
//
//        System.setIn(new ByteArrayInputStream(input2.getBytes))
//        assert(textUI.readPlacement == (false, 0, Index(0), Index(0)))
//      } finally {
//        System.setIn(originalIn)
//      }
//    }