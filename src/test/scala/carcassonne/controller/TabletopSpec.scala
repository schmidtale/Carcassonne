package carcassonne.controller

import carcassonne.CarcassonneModule.given_GameDataTrait
import carcassonne.controller.controllerComponent.controllerBaseImplementation.Tabletop
import carcassonne.model.gameDataComponent.GameDataTrait
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.{GameData, Index, PlacingLiegemanState, PlacingTileState, ReviewState, Tile, TileMap, TileStack}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.immutable.Queue

class TabletopSpec extends AnyWordSpec {
  val tabletop = new Tabletop()
  val tileStack = new TileStack
  private val tile = tileStack.startingTile
  private val emptyTile = Option.empty[Tile]
  private val emptyState = tabletop.gameData.map

  "A tabletop" should {
    "be able to return an initial GameData" in {
      val tabletop2 = new Tabletop()
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
    // TODO fix
//    "return its internal TileStack" in {
//      val stack = tabletop.tileStack()
//      val stack2 = tileStack.construct()
//      assert(stack == stack2 )
//    }
    "return a starting tile" in {
      val starting_tile = tabletop.startingTile()
      assert(starting_tile == tileStack.startingTile)
    }
    "return a string of the tabletop" in {
      val tabletopString = Tabletop().constructTabletopFromMap()

      val tileMapString = TileMap().add(Index(7), Index(7), Some(TileStack().startingTile)).toString
      assert(tabletopString == tileMapString)
    }
    "undo and redo a command" in {
      val tabletop = new Tabletop()
      val startingTile = TileStack().startingTile
      tabletop.addTileToMap(Index(0), Index(0), startingTile)
      tabletop.undo()
      assert(tabletop.gameData.map.data(Index(0), Index(0)).isEmpty)
      tabletop.redo()
      assert(tabletop.gameData.map.data(Index(0), Index(0)).get.equals(startingTile))
    }
    "not undo a command if there is nothing to undo" in {
      val gd = GameData().initialState()
      given gameData: GameDataTrait = gd
      val tabletop = new Tabletop()
      tabletop.undo()
      assert(tabletop.gameData.equals(gd))
    }
    "not redo a command if there is nothing to redo" in {
      val gd = GameData().initialState()
      given gameData: GameDataTrait = gd
      val tabletop = new Tabletop()
      tabletop.redo()
      assert(tabletop.gameData.equals(gd))
    }
    "change gameData states and call commands with different States" in {
      // TODO configure test correctly when using state pattern
      val gameData = GameData().initialState()
      val tabletop = new Tabletop()
      val startingTile = TileStack().startingTile
      tabletop.addTileToMap(Index(0), Index(0), startingTile)
      tabletop.changeState(PlacingTileState)
      assert(tabletop.gameData.state == PlacingTileState)
      tabletop.addTileToMap(Index(1), Index(1), startingTile)
      tabletop.changeState(PlacingLiegemanState)
      assert(tabletop.gameData.state == PlacingLiegemanState)
      tabletop.addTileToMap(Index(2), Index(2), startingTile)
      tabletop.changeState(ReviewState)
      assert(tabletop.gameData.state == ReviewState)
      // TODO Check for review state, adding should not be possible anymore
      tabletop.addTileToMap(Index(3), Index(3), startingTile)
    }
  }
}
