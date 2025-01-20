package carcassonne.model.fileIoComponent.fileIoXMLImpl

import carcassonne.CarcassonneModule.given
import carcassonne.model.fileIoComponent.FileIOTrait
import carcassonne.model.gameDataComponent.GameDataTrait

import scala.util.Try
import scala.xml.{Elem, PrettyPrinter}

class FileIO(val fileName: String = "gameData.xml") extends FileIOTrait {
  val gameData: GameDataTrait = summon[GameDataTrait]

  override def load: GameDataTrait = {
    Try {
      val file = scala.xml.XML.loadFile(fileName)
      gameData.fromXML(file)
    } match {
      case scala.util.Success(gameData) => gameData
      case scala.util.Failure(exception) =>
        println(s"Error loading game data: ${exception.getMessage}")
        gameData
    }
  }

  override def save(gameData: GameDataTrait): Unit = {
    val xmlData: Elem = gameData.toXML
    val prettyPrinter = new PrettyPrinter(80, 2)
    val prettyXml: String = prettyPrinter.format(xmlData)
    scala.xml.XML.save(fileName, scala.xml.XML.loadString(prettyXml))
  }

}
