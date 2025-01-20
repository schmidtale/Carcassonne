package carcassonne.model.fileIoComponent.fileIoJSONImpl

import carcassonne.CarcassonneModule.given
import carcassonne.model.gameDataComponent.GameDataTrait

import java.io.File
import scala.io.Source
import scala.util.Using

import org.scalatest.matchers.should.Matchers.shouldBe
import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.Json

class FileIOJSONSpec extends AnyWordSpec {
  "FileIO" should {
    val fileIO = new FileIO("testGameData.json")
    val testFile = new File("testGameData.json")
    val gameData: GameDataTrait = summon[GameDataTrait]

    "save a GameData instance to a file" in {
      fileIO.save(gameData)

      assert(testFile.exists())

      val content = Using(Source.fromFile(testFile))(_.mkString).get
      val json = Json.parse(content)

      assert(json == Json.toJson(gameData)(gameData.writes))
    }
    "load a GameData instance from a file" in {
      val loadedGameData = fileIO.load

      assert(loadedGameData.equals(gameData))
    }
    "clean up the test file" in {
      // Ensure the test file is deleted after the tests
      if (testFile.exists()) {
        testFile.delete()
      }
      testFile.exists() shouldBe false
    }
    "return given gameDataTrait if test file does not exist" in {
      if (testFile.exists()) {
        testFile.delete()
      }
      val loadedGameData = fileIO.load
      assert(loadedGameData == gameData)
    }
  }
}
