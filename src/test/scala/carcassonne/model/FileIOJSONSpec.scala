package carcassonne.model

import carcassonne.CarcassonneModule.given
import carcassonne.model.fileIoComponent.fileIoJSONImpl.FileIO
import carcassonne.model.gameDataComponent.GameDataTrait
import org.scalatest.matchers.should.Matchers.{shouldBe, shouldEqual}
import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.{Json, Reads, Writes}

import java.io.File
import scala.io.Source
import scala.util.Using

class FileIOJSONSpec extends AnyWordSpec {
  "FileIO" should {
    val fileIO = new FileIO()
    val testFile = new File("gameData.json")
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
  }
}
