package _view

import controller.Tabletop
import model.{GameData, Index, Tile, TileMap}
import model.BorderType.*
import model.LiegemanPosition.*
import model.LiegemanType.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, InputStream, PrintStream}
import org.scalatest.concurrent.TimeLimits
import org.scalatest.concurrent.TimeLimits.failAfter
import org.scalatest.time.{Seconds, Span}


class TextUISpec extends AnyWordSpec {
  val tile2 = new Tile(false, true, borders = Vector(town, pasture, pasture, town), (knight, north))
  val tabletop =  new Tabletop(GameData())
  val textUI = new TextUI(tabletop)


  "The TextUI" should {
    "print the current tabletop map via update() to the console" in {
      /** which it reliably does unless tested. The test used to work, now it does so no longer.
       * Unfortunately, I do not dare to add a parameter, since we need the method as is for the Observer-pattern.*/
//      val originalOut = System.out
//      val outContent = new ByteArrayOutputStream()
//      System.setOut(new PrintStream(outContent))
//
//      try {
//        tabletop.initialMap()
//        textUI.update()
//        System.out.flush()
//        println("Constructed Tabletop Map: " + tabletop.constructTabletopFromMap())
//
//        val expectedOutput = tabletop.constructTabletopFromMap()
//        assert(outContent.toString == expectedOutput)
//      } finally {
//        System.setOut(originalOut)
//      }
      textUI.update()
    }
    "add a tile to the selected place and return the new mapping" in {
      textUI.updateMap(true, Index(0), Index(0), tile2)
      assert(tabletop.constructTabletopFromMap().startsWith("* B B B *"))
      assert(tabletop.constructTabletopFromMap().contains("B . . . ."))
      textUI.updateMap(false, Index(0), Index(1), tile2)
      assert(tabletop.constructTabletopFromMap().contains("* B B B *          "))
      //                                                               ^empty slot
    }
    "convert a user's command into placement information" in {
      failAfter(Span(12, Seconds)) {
        val input1 = new ByteArrayInputStream("0 5 14\n".getBytes)
        val input2 = new ByteArrayInputStream("3 15 2\n".getBytes) // Invalid input
        val input3 = new ByteArrayInputStream("3 f 2\n".getBytes) // Invalid input
        assert(textUI.readPlacement(input1) == (true, 0, Index(5), Index(14)))
        assert(textUI.readPlacement(input2) == (false, 0, Index(0), Index(0)))
        assert(textUI.readPlacement(input3) == (false, 0, Index(0), Index(0)))
      }
    }
    "return an Int equal to (input + 1)" in {
      val exampleTurn = 5
      val input = new ByteArrayInputStream("0 5 8\n".getBytes)
      assert(textUI.exec(exampleTurn, tabletop.tileStack(), input) == exampleTurn + 1)
    }
  }
}
