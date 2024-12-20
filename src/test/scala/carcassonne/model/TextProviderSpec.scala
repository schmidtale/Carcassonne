package carcassonne.model

import carcassonne.model.gameDataComponent.gameDataBaseImplementation.BorderType.*
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.LiegemanPosition.north
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.LiegemanType.knight
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.{TextProvider, Tile}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class TextProviderSpec extends AnyWordSpec {
  val textProvider = new TextProvider
  val tile2 = new Tile(monastery = false, true, borders = Vector(town, pasture, pasture, town), (knight, north))
  val expectedStringTile2 = "* B B B *\nB B . . .\nB . . . .\nB . . . .\n* . . . *"

  val t1 = new Tile(monastery = true, false, Vector(pasture, pasture, pasture, pasture))
  val s1 = "* . . . *\n. . . . .\n. . C . .\n. . . . .\n* . . . *"
  val t2 = new Tile(monastery = false, false, Vector(pasture, road, pasture, road))
  val s2 = "* . . . *\n. . . . .\nH H H H H\n. . . . .\n* . . . *"
  val t3 = new Tile(monastery = false, true, Vector(pasture, town, town, town)) //town connection must be true in this combination
  val s3 = "* . . . *\nB . . . B\nB B B B B\nB B B B B\n* B B B *"
  val t4 = new Tile(monastery = false, false, Vector(road, road, road, road))
  val s4 = "* . H . *\n. . H . .\nH H H H H\n. . H . .\n* . H . *"
  val t5 = new Tile(monastery = false, false, Vector(road, town, town, road))
  val s5 = "* . H . *\n. . H . B\nH H H . B\n. . . . B\n* B B B *"

  val t6 = new Tile(monastery = false, true, Vector(town, town, town, town))
  val s6 = "* B B B *\nB B B B B\nB B B B B\nB B B B B\n* B B B *"
  val t7 = new Tile(monastery = false, true, Vector(town, town, town, pasture))
  val s7 = "* B B B *\n. . B B B\n. . B B B\n. . B B B\n* B B B *"
  val t8 = new Tile(monastery = false, true, Vector(town, town, pasture, town))
  val s8 = "* B B B *\nB B B B B\nB B B B B\nB . . . B\n* . . . *"
  val t9 = new Tile(monastery = false, true, Vector(town, pasture, town, town))
  val s9 = "* B B B *\nB B B . .\nB B B . .\nB B B . .\n* B B B *"

  val t10 = new Tile(monastery = false, false, Vector(town, pasture, pasture, town))
  val s10 = "* B B B *\nB . . . .\nB . . . .\nB . . . .\n* . . . *"
  val t11 = new Tile(monastery = false, false, Vector(town, town, pasture, pasture))
  val s11 = "* B B B *\n. . . . B\n. . . . B\n. . . . B\n* . . . *"
  val t12 = new Tile(monastery = false, true, Vector(town, town, pasture, pasture))
  val s12 = "* B B B *\n. . . B B\n. . . . B\n. . . . B\n* . . . *"
  val t13 = new Tile(monastery = false, true, Vector(pasture, town, town, pasture))
  val s13 = "* . . . *\n. . . . B\n. . . . B\n. . . B B\n* B B B *"
  val t14 = new Tile(monastery = false, true, Vector(pasture, pasture, town, town))
  val s14 = "* . . . *\nB . . . .\nB . . . .\nB B . . .\n* B B B *"
  val t15 = new Tile(monastery = false, false, Vector(pasture, pasture, town, town))
  val s15 = "* . . . *\nB . . . .\nB . . . .\nB . . . .\n* B B B *"

  val t16 = new Tile(monastery = false, false, Vector(pasture, pasture, road, road))
  val s16 = "* . . . *\n. . . . .\nH H H . .\n. . H . .\n* . H . *"
  val t17 = new Tile(monastery = false, false, Vector(pasture, road, road, pasture))
  val s17 = "* . . . *\n. . . . .\n. . H H H\n. . H . .\n* . H . *"

  /* non existing tile t18 : */
  val t18 = new Tile(monastery = true, false, Vector(pasture, road, town, road))
  val s19 = "* . . . *\n. . . . .\nH H M H H\n. . H . .\n* B B B *"

  "A model.TextProvider" should {
    "provide a string representation of a whole Tile" in {
      assert(textProvider.toText(tile2) == expectedStringTile2)
      assert(textProvider.toText(t1) == s1)
      assert(textProvider.toText(t2) == s2)
      assert(textProvider.toText(t3) == s3)
      assert(textProvider.toText(t4) == s4)
      assert(textProvider.toText(t5) == s5)
      assert(textProvider.toText(t6) == s6)
      assert(textProvider.toText(t7) == s7)
      assert(textProvider.toText(t8) == s8)
      assert(textProvider.toText(t9) == s9)
      assert(textProvider.toText(t10) == s10)
      assert(textProvider.toText(t11) == s11)
      assert(textProvider.toText(t12) == s12)
      assert(textProvider.toText(t13) == s13)
      assert(textProvider.toText(t14) == s14)
      assert(textProvider.toText(t15) == s15)
      assert(textProvider.toText(t16) == s16)
      assert(textProvider.toText(t17) == s17)
    }
    
    "translate the data of a tile to Strings used by the TUI" in {
      assert(textProvider.line(tile2,0) == "* B B B *")
      assert(textProvider.line(tile2,1) == "B B . . .")
      assert(textProvider.line(tile2,2) == "B . . . .")
      assert(textProvider.line(tile2,3) == "B . . . .")
      assert(textProvider.line(tile2,4) == "* . . . *")
    }

    "replace unknown chars with # char" in {
      assert(". + .".map(char => textProvider.replaceChar(char, t1)) == "# # #")
    }
    
  }

}
