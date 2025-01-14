package carcassonne

import carcassonne.model.fileIoComponent.FileIOTrait
import carcassonne.model.fileIoComponent.fileIoJSONImpl.FileIO
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.Color.{black, blue, green, red, yellow}
import controller.controllerComponent.ControllerTrait
import controller.controllerComponent.controllerBaseImplementation.Tabletop
import model.gameDataComponent.{GameDataTrait, PlayerTrait, TextProviderTrait, TileMapTrait, TileTrait}
import model.gameDataComponent.gameDataBaseImplementation.*

import scala.collection.immutable.Queue;

object CarcassonneModule {
  given GameDataTrait = GameData(players = Queue(PlayerState(blue), PlayerState(red), PlayerState(yellow), PlayerState(green), PlayerState(black))).initialState()
  given TileTrait = Tile()
  given TileMapTrait = TileMap()
  given PlayerTrait = PlayerState()
  given TextProviderTrait = TextProvider()
  given ControllerTrait = Tabletop()
  given FileIOTrait = FileIO()
}
