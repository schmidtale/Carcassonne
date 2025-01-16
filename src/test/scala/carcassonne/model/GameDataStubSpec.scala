package carcassonne.model

import carcassonne.model.gameDataComponent.GameDataTrait
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.PlacingTileState
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.Json
import carcassonne.model.gameDataComponent.gameDataStubImplementation.GameDataStub

import scala.xml.Node

class GameDataStubSpec extends AnyWordSpec {
  "A GameDataStub" should {
    "let the GameDataTrait methods be called and compile" in {
      val gameDataStub = GameDataStub()
      gameDataStub.startingTile()
      gameDataStub.initialState()
      gameDataStub.withState(PlacingTileState)
      gameDataStub.withTurn(5)
      gameDataStub.withMap(gameDataStub.map)
      gameDataStub.currentTile()
      gameDataStub.deepClone()
      gameDataStub.fromXML(<empty/>)
      gameDataStub.toXML
      val gameDataStubForJSON: GameDataTrait = gameDataStub
      Json.toJson(gameDataStubForJSON)(gameDataStub.writes)
      val json = Json.obj(
        "GameDataStub" -> "GameDataStub"
      )
      json.as[GameDataTrait](gameDataStub.reads)
    }
  }
}
