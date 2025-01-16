package carcassonne.model

import carcassonne.CarcassonneModule.given
import carcassonne.model.fileIoComponent.fileIoXMLImpl.FileIO
import carcassonne.model.gameDataComponent.GameDataTrait
import org.scalatest.matchers.should.Matchers.shouldBe
import org.scalatest.wordspec.AnyWordSpec

import java.io.File
import scala.io.Source
import scala.util.Using
import scala.xml.{PrettyPrinter, XML}

class FileIOXMLSpec extends AnyWordSpec {
  "FileIO" should {
    val fileIO = new FileIO()
    val testFile = new File("gameData.xml")
    val gameData: GameDataTrait = summon[GameDataTrait]

    "save a GameData instance to a file" in {
      fileIO.save(gameData)

      assert(testFile.exists())

      val content = Using(Source.fromFile(testFile))(_.mkString).get
      val xml = XML.loadString(content)

      val prettyPrinter = new PrettyPrinter(80, 2)
      val prettyXmlFromFile: String = prettyPrinter.format(xml)
      val prettyXmlFromGameData: String = prettyPrinter.format(gameData.toXML)

      assert(prettyXmlFromFile.equals(prettyXmlFromGameData))
    }
    "load a GameData instance from a file" in {
      fileIO.save(gameData)
      val loadedGameData = fileIO.load
      assert(loadedGameData.equals(gameData))
    }
    "clean up the test file" in {
      if (testFile.exists()) {
        testFile.delete()
      }
      testFile.exists() shouldBe false
    }
  }
}
