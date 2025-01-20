package carcassonne

import carcassonne.controller.controllerComponent.ControllerTrait
import carcassonne.controller.controllerComponent.controllerBaseImplementation.Tabletop
import carcassonne.model.fileIoComponent.FileIOTrait
import carcassonne.model.fileIoComponent.fileIoXMLImpl.FileIO
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.*
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.Color.*
import carcassonne.model.gameDataComponent.*

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
