package carcassonne._view

import carcassonne.controller.controllerComponent.controllerBaseImplementation.Tabletop
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.{GameData, Index, TextProvider, Tile, TileMap}
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.BorderType.*
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.LiegemanPosition.*
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.LiegemanType.*
import carcassonne.CarcassonneModule.given_ControllerTrait
import carcassonne.CarcassonneModule.given_TextProviderTrait
import carcassonne.CarcassonneModule.given_GameDataTrait
import carcassonne.CarcassonneModule.given_TextProviderTrait
import carcassonne.controller.controllerComponent.ControllerTrait
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, InputStream, PrintStream}
import org.scalatest.concurrent.TimeLimits
import org.scalatest.concurrent.TimeLimits.failAfter
import org.scalatest.time.{Seconds, Span}


class TextUISpec extends AnyWordSpec {
  val tile2 = new Tile(monastery = false, true, borders = Vector(town, pasture, pasture, town), (knight, north))
  val textUI = new TextUI()


  "The TextUI" should {
    "add a tile to the selected place and return the new mapping" in {
      val tabletop = new Tabletop()
      val anotherTextUI = new TextUI(using tabletop)
      anotherTextUI.updateMap(true, Index(0), Index(0), 0)
      assert(tabletop.constructTabletopFromMap().startsWith("*"))
      assert(tabletop.constructTabletopFromMap().contains("* . . . *"))
  }
    "convert a user's command into placement information" in {
        val input1 = "0 5 14"
        val input2 = "3 15 2" // Invalid input
        val input3 = "3 f 2" // Invalid input
        val input4 = "n" // new game
        val input5 = "z" // undo
        val input6 = "y" // redo
        val input7 = "s" // save
        val input8 = "l" // load
        assert(textUI.readPlacement(input1) == (true, 0, Index(5), Index(14)))
        assert(textUI.readPlacement(input2) == (false, 0, Index(0), Index(0)))
        assert(textUI.readPlacement(input3) == (false, 0, Index(0), Index(0)))
        assert(textUI.readPlacement(input4) == (false, 0, Index(0), Index(0)))
        assert(textUI.readPlacement(input5) == (false, 0, Index(0), Index(0)))
        assert(textUI.readPlacement(input6) == (false, 0, Index(0), Index(0)))
        assert(textUI.readPlacement(input7) == (false, 0, Index(0), Index(0)))
        assert(textUI.readPlacement(input8) == (false, 0, Index(0), Index(0)))
    }
    "return an Int equal to (exampleTurn + 1)" in {
      val exampleTurn = 5
      val input = new ByteArrayInputStream("0 5 8\n".getBytes)
      val tt =  new Tabletop()
      tt.gameData = tt.gameData.withTurn(exampleTurn)
      given tabletop: ControllerTrait = tt
      val textUI = new TextUI()
      assert(textUI.exec(input) == exampleTurn + 1)
    }
    "return an Int equal to exampleTurn when undoing" in {
      val exampleTurn = 5
      val input = new ByteArrayInputStream("0 5 8\n".getBytes)
      val tt = new Tabletop()
      tt.gameData = tt.gameData.withTurn(exampleTurn)
      given tabletop: ControllerTrait = tt
      val textUI = new TextUI()
      assert(textUI.exec(input) == exampleTurn + 1)
      val input2 = new ByteArrayInputStream("z\n".getBytes)
      assert(textUI.exec(input2) == exampleTurn)
    }
    "return an Int equal to (exampleTurn + 1) when redoing" in {
      val exampleTurn = 5
      val input = new ByteArrayInputStream("0 5 8\n".getBytes)
      val tt = new Tabletop()
      tt.gameData = tt.gameData.withTurn(exampleTurn)
      given tabletop: ControllerTrait = tt
      val textUI = new TextUI()
      assert(textUI.exec(input) == exampleTurn + 1)
      val input2 = new ByteArrayInputStream("z\n".getBytes)
      assert(textUI.exec(input2) == exampleTurn)
      val input3 = new ByteArrayInputStream("y\n".getBytes)
      assert(textUI.exec(input3) == exampleTurn + 1)
    }
    "return an Int equal to 0 when restarting" in {
      val exampleTurn = 5
      val input = new ByteArrayInputStream("n\n".getBytes)
      val tt = new Tabletop()
      tt.gameData = tt.gameData.withTurn(exampleTurn)
      given tabletop: ControllerTrait = tt
      val textUI = new TextUI()
      assert(textUI.exec(input) == 0)
    }
    "return an Int equal to exampleTurn when using invalid input" in {
      val exampleTurn = 5
      val input = new ByteArrayInputStream("\n".getBytes)
      val tt = new Tabletop()
      tt.gameData = tt.gameData.withTurn(exampleTurn)
      given tabletop: ControllerTrait = tt
      val textUI = new TextUI()
      assert(textUI.exec(input) == exampleTurn)
    }
    "provide a review print for the end of the game" in {
      /* simulate end of game: */
      val endingGame = new Tabletop()
      endingGame.gameData = GameData().withTurn(71)
      val tui = new TextUI(using endingGame)
      tui.update()
    }
    "print the current tabletop map via update() to the console" in {
      textUI.update()
    }
  }
}
