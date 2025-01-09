package carcassonne

import carcassonne.model.gameDataComponent.gameDataBaseImplementation.Color.{black, blue, green, red, yellow}
import com.google.inject.AbstractModule
import controller.controllerComponent.ControllerTrait
import controller.controllerComponent.controllerBaseImplementation.Tabletop
import model.gameDataComponent.{GameDataTrait, PlayerTrait, TextProviderTrait, TileMapTrait, TileTrait}
import model.gameDataComponent.gameDataBaseImplementation.*

import scala.collection.immutable.Queue;

class CarcassonneModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[GameDataTrait]).toInstance(GameData(players = Queue(PlayerState(blue), PlayerState(red), PlayerState(yellow), PlayerState(green), PlayerState(black))).initialState())
    bind(classOf[TileTrait]).toInstance(Tile())
    bind(classOf[TileMapTrait]).toInstance(TileMap())
    bind(classOf[PlayerTrait]).toInstance(PlayerState())
    bind(classOf[TextProviderTrait]).to(classOf[TextProvider])
    bind(classOf[ControllerTrait]).to(classOf[Tabletop])
  }
}
