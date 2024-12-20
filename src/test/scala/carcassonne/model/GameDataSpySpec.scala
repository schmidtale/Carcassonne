package carcassonne.model

import carcassonne.model.gameDataComponent.gameDataBaseImplementation.PlacingTileState
import carcassonne.model.gameDataComponent.gameDataSpyImplementation.GameDataSpy
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

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
      assert(silentSpy.startingTileCalls == 2)
      assert(silentSpy.initialStateCalls == 1)
      assert(silentSpy.withStateCalls == 1)
      assert(silentSpy.withTurnCalls == 3)
      assert(silentSpy.withMapCalls == 1)
      assert(silentSpy.currentTileCalls == 1)
      assert(silentSpy.deepCloneCalls == 1)
    }
  }
}
