package controller

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import model.BorderType.*
import model.Tile
import model.LiegemanPosition.north
import model.LiegemanType.knight

class TextProviderSpec extends AnyWordSpec {
  val tile2 = new Tile(false, true, borders = Vector(town, pasture, pasture, town), (knight, north))
  val textProvider = new TextProvider
  val expectedStringTile2 = "* B B B *\nB B . . .\nB . . . .\nB . . . .\n* . . . *"

  "A controller.TextProvider" should {
    "provide a string representation of a whole Tile" in {
      assert(textProvider.toText(tile2) == expectedStringTile2)
    }
    
    "translate the data of a tile to Strings used by the TUI" in {
      assert(textProvider.line(tile2,0) == "* B B B *")
      assert(textProvider.line(tile2,1) == "B B . . .")
      assert(textProvider.line(tile2,2) == "B . . . .")
      assert(textProvider.line(tile2,3) == "B . . . .")
      assert(textProvider.line(tile2,4) == "* . . . *")
    }

    
  }

}
