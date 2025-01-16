package carcassonne.model

import carcassonne.model.gameDataComponent.GameDataTrait
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.PlacingTileState
import carcassonne.model.gameDataComponent.gameDataSpyImplementation.GameDataSpy
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.Json
import carcassonne.model.gameDataComponent.gameDataSpyImplementation.GameDataSpy.*

import scala.xml.Node

class GameDataSpySpec extends AnyWordSpec {
  "A GameDataSpy" should {
    "return the number of calls to a GameDataTrait method" in {
      val silentSpy = GameDataSpy()
      silentSpy.startingTile()
      silentSpy.startingTile()
      silentSpy.initialState()
      silentSpy.withState(PlacingTileState)
      silentSpy.withTurn(5)
      silentSpy.withTurn(6)
      silentSpy.withTurn(3)
      silentSpy.withMap(silentSpy.map)
      silentSpy.currentTile()
      silentSpy.deepClone()
      silentSpy.fromXML(<empty/>)
      silentSpy.toXML
      val gameDataSpy: GameDataTrait = silentSpy
      Json.toJson(gameDataSpy)(gameDataSpy.writes)
      val json = Json.obj(
        "GameDataSpy" -> "GameDataSpy"
      )
      json.as[GameDataTrait](gameDataSpy.reads)
      assert(silentSpy.startingTileCalls == 2)
      assert(silentSpy.initialStateCalls == 1)
      assert(silentSpy.withStateCalls == 1)
      assert(silentSpy.withTurnCalls == 3)
      assert(silentSpy.withMapCalls == 1)
      assert(silentSpy.currentTileCalls == 1)
      assert(silentSpy.deepCloneCalls == 1)
      assert(silentSpy.toXMLCalls == 1)
      assert(silentSpy.fromXMLCalls == 1)
      assert(silentSpy.writesCalls == 1)
      assert(silentSpy.readsCalls == 1)
    }
  }
}
