package model

import model.BorderType.*
import model.LiegemanPosition.north
import model.LiegemanType.knight
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class TextProviderSpec extends AnyWordSpec {
  val tile2 = new Tile(false, true, borders = Vector(town, pasture, pasture, town), (knight, north))
  val textProvider = new TextProvider
  val expectedStringTile2 = "* B B B *\nB B . . .\nB . . . .\nB . . . .\n* . . . *"

  val t1 = new Tile(true, false, Vector(pasture, pasture, pasture, pasture))
  val s1 = "* . . . *\n. . . . .\n. . C . .\n. . . . .\n* . . . *"
  val t2 = new Tile(false, false, Vector(pasture, road, pasture, road))
  val s2 = "* . . . *\n. . . . .\nH H H H H\n. . . . .\n* . . . *"
  val t3 = new Tile(false, true, Vector(pasture, town, town, town)) //town connection must be true in this combination
  val s3 = "* . . . *\nB . . . B\nB B B B B\nB B B B B\n* B B B *"
  val t4 = new Tile(false, false, Vector(road, road, road, road))
  val s4 = "* . H . *\n. . H . .\nH H H H H\n. . H . .\n* . H . *"
  val t5 = new Tile(false, false, Vector(road, town, town, road))
  val s5 = "* . H . *\n. . H . B\nH H H . B\n. . . . B\n* B B B *"

  "A model.TextProvider" should {
    "provide a string representation of a whole Tile" in {
      assert(textProvider.toText(tile2) == expectedStringTile2)
      assert(textProvider.toText(t1) == s1)
      assert(textProvider.toText(t2) == s2)
      assert(textProvider.toText(t3) == s3)
      assert(textProvider.toText(t4) == s4)
      assert(textProvider.toText(t5) == s5)
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
