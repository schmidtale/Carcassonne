package carcassonne.model.fileIoComponent.fileIoXMLImpl

import carcassonne.CarcassonneModule.given
import carcassonne.model.fileIoComponent.FileIOTrait
import carcassonne.model.gameDataComponent.GameDataTrait

import scala.xml.{Elem, PrettyPrinter}

class FileIO extends FileIOTrait{
  val gameData: GameDataTrait = summon[GameDataTrait]

  override def load: GameDataTrait = {
    val file = scala.xml.XML.loadFile("gameData.xml")
    gameData.fromXML(file)
  }

  override def save(gameData: GameDataTrait): Unit = {
    val xmlData: Elem = gameData.toXML
    val prettyPrinter = new PrettyPrinter(80, 2)
    val prettyXml: String = prettyPrinter.format(xmlData)
    scala.xml.XML.save("gameData.xml", scala.xml.XML.loadString(prettyXml))
  }

}
