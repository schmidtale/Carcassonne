package carcassonne.model

import carcassonne.model.gameDataComponent.gameDataBaseImplementation.Color.*
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.{GameData, MenuState, PlayerState, Tile, TileStack}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.Json

import scala.collection.immutable.Queue
import scala.xml.Elem

class GameDataSpec extends AnyWordSpec {
  "A GameData instance" should {
    val game = GameData()
    "return the color of the active Player" in {
      assert(game.activePlayer().equals(blue))
      val game1 = GameData(turn = 1)
      assert(game1.activePlayer().equals(red))
    }
    "return a different reference to current tile based on the turn" in {
      assert(game.currentTile().isInstanceOf[Tile])
      val gameTurn1 = GameData(turn = 20)
      val gameTurn0 = gameTurn1.initialState()
      assert(gameTurn1.currentTile() != gameTurn0.currentTile())
    }
    "have reflexive equals" in {
      assert(game.equals(game))
    }
    "have symmetric equals" in {
      val game1 = game.deepClone()
      assert(game.equals(game1) && game1.equals(game))
    }
    "have transitive equals" in {
      val game1 = game.deepClone()
      val game2 = game.deepClone()
      assert(game.equals(game1) && game1.equals(game2) && game.equals(game2))
    }
    "return false if you compare a GameData to null" in {
      assert(!game.equals(null))
    }
    "return false if you compare a GameData to something else" in {
      val that = "empty"
      assert(!game.equals(that))
    }
    "return false if you compare two different GameData" in {
      val game1 = GameData(turn = 1)
      assert(!game.equals(game1))
    }
    "return the same hashcode for equal GameData" in {
      val game1 = game.deepClone()
      assert(game.hashCode() == game1.hashCode())
    }
    "provide an unchanging reference to its current data" in {
      var game = GameData()
      val clone = game.deepClone()
      assert(game.equals(clone))

      game = GameData()
      assert(game != clone)
    }
  }
  "A PlayerState" should {
    val player = PlayerState()
    "have reflexive equals" in {
      assert(player.equals(player))
    }
    "have symmetric equals" in {
      val player1 = PlayerState()
      assert(player.equals(player1) && player1.equals(player))
    }
    "have transitive equals" in {
      val player1 = PlayerState()
      val player2 = PlayerState()
      assert(player.equals(player1) && player1.equals(player2) && player.equals(player2))
    }
    "return false if you compare a player to null" in {
      assert(!player.equals(null))
    }
    "return false if you compare a player to something else" in {
      val that = "empty"
      assert(!player.equals(that))
    }
    "return false if you compare two different players" in {
      val player1 = PlayerState(red)
      assert(!player.equals(player1))
    }
    "return the same hashcode for equal players" in {
      val player1 = PlayerState()
      assert(player.hashCode() == player1.hashCode())
    }
    "let players be convertible to XML" in {
      val player = new PlayerState()
      val xmlOutput = player.toXML
      val expectedXml: Elem =
        <playerState>
          <meepleCount>
            {7}
          </meepleCount>
          <color>
            {blue}
          </color>
          <points>
            {0}
          </points>
        </playerState>
      val normalizedXmlOutput = xmlOutput.toString.replaceAll("\\s+", "")
      val normalizedExpectedXml = expectedXml.toString.replaceAll("\\s+", "")
      assert(normalizedXmlOutput.equals(normalizedExpectedXml))
    }
    "let players be constructable from XML" in {
      val player = new PlayerState()
      // Create an instance of PrettyPrinter with the desired settings
      val XML = player.toXML
      print(XML)
      val XMLTile = player.fromXML(XML)
      assert(player.equals(XMLTile))
    }
    "let playerState be convertible to JSON" in {
      val player = new PlayerState()
      val jsonOutput = Json.toJson(player)
      val expectedJson = Json.obj(
        "meepleCount" -> 7,
        "color" -> "blue",
        "points" -> 0
      )
      assert(jsonOutput.equals(expectedJson))
    }
    "let playerState be constructable from JSON" in {
      val expectedPlayer = new PlayerState()
      val json = Json.obj(
        "meepleCount" -> 7,
        "color" -> "blue",
        "points" -> 0
      )
      val playerFromJson = json.as[PlayerState]
      assert(playerFromJson.equals(expectedPlayer))
    }
    "be convertible to XML" in {
      var gameData = GameData(stack = Queue(TileStack().startingTile))
      gameData = gameData.initialState()
      val xmlOutput = gameData.toXML
      val expectedXml: Elem =
        <GameData>
          {gameData.map.toXML}<stack>
          <tile>
            <name>
              D
            </name>
            <monastery>
              false
            </monastery>
            <townConnection>
              false
            </townConnection>
            <borders>
              <border>
                town
              </border> <border>
              road
            </border> <border>
              pasture
            </border> <border>
              road
            </border>
            </borders>
            <liegeman>
              <type>
                none
              </type>
              <position>
                nowhere
              </position>
            </liegeman>
            <coatOfArms>
              false
            </coatOfArms>
            <rotation>
              3
            </rotation>
          </tile>
        </stack>
          <players>
            <playerState>
              <meepleCount>7</meepleCount>
              <color>blue</color>
              <points>0</points>
            </playerState>
            <playerState>
              <meepleCount>7</meepleCount>
              <color>red</color>
              <points>0</points>
            </playerState>
          </players>
          <turn>0</turn>
          <state>MenuState</state>
        </GameData>
      val normalizedXmlOutput = xmlOutput.toString.replaceAll("\\s+", "")
      val normalizedExpectedXml = expectedXml.toString.replaceAll("\\s+", "")
      assert(normalizedXmlOutput.equals(normalizedExpectedXml))
    }
    "be constructable from XML" in {
      var gameData = GameData(stack = Queue(TileStack().startingTile))
      gameData = gameData.initialState()
      val xmlOutput = gameData.toXML
      val expectedXml: Elem =
        <GameData>
          {gameData.map.toXML}<stack>
          <tile>
            <name>
              D
            </name>
            <monastery>
              false
            </monastery>
            <townConnection>
              false
            </townConnection>
            <borders>
              <border>
                town
              </border> <border>
              road
            </border> <border>
              pasture
            </border> <border>
              road
            </border>
            </borders>
            <liegeman>
              <type>
                none
              </type>
              <position>
                nowhere
              </position>
            </liegeman>
            <coatOfArms>
              false
            </coatOfArms>
            <rotation>
              3
            </rotation>
          </tile>
        </stack>
          <players>
            <playerState>
              <meepleCount>7</meepleCount>
              <color>blue</color>
              <points>0</points>
            </playerState>
            <playerState>
              <meepleCount>7</meepleCount>
              <color>red</color>
              <points>0</points>
            </playerState>
          </players>
          <turn>0</turn>
          <state>MenuState</state>
        </GameData>
      val XMLGameData = gameData.fromXML(expectedXml)
      assert(gameData.equals(XMLGameData))
    }
    "be convertible to JSON" in {
      var gameData = GameData(stack = Queue(TileStack().startingTile))
      gameData = gameData.initialState()
      val jsonOutput = Json.toJson(gameData)
      val expectedJson = Json.obj(
        "GameData" -> Json.obj(
          "map" -> Json.toJson(gameData.map),
          "stack" -> Json.toJson(gameData.stack),
          "players" -> Json.toJson(gameData.players),
          "turn" -> 0,
          "state" -> "MenuState"
        )
      )
      assert(jsonOutput.equals(expectedJson))
    }
    "be constructable from JSON" in {
      var gameData = GameData(stack = Queue(TileStack().startingTile))
      gameData = gameData.initialState()
      val json = Json.obj(
        "GameData" -> Json.obj(
          "map" -> Json.toJson(gameData.map),
          "stack" -> Json.toJson(gameData.stack),
          "players" -> Json.toJson(gameData.players),
          "turn" -> 0,
          "state" -> "MenuState"
        )
      )
      val gameDataFromJson = json.as[GameData]
      assert(gameDataFromJson.equals(gameData))
    }
  }
}
