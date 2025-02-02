package carcassonne.model.fileIoComponent.fileIoJSONImpl

import carcassonne.CarcassonneModule.given
import carcassonne.model.fileIoComponent.FileIOTrait
import carcassonne.model.gameDataComponent.GameDataTrait

import play.api.libs.json
import play.api.libs.json.{JsValue, Json}

import scala.io.Source
import scala.util.{Try, Using}

class FileIO(val fileName: String = "gameData.json") extends FileIOTrait {
  val gameData: GameDataTrait = summon[GameDataTrait]

  override def load: GameDataTrait = {
    Try {
      val json: JsValue = Using(Source.fromFile(fileName)) { source => Json.parse(source.getLines().mkString) }.get
      json.as[GameDataTrait](gameData.reads)
    } match {
      case scala.util.Success(gameData) => gameData
      case scala.util.Failure(exception) =>
        println(s"Error loading game data: ${exception.getMessage}")
        gameData
    }
  }

  override def save(gameData: GameDataTrait): Unit = {
    import java.io.*
    val pw = new PrintWriter(new File(fileName))
    pw.write(Json.prettyPrint(Json.toJson(gameData)(gameData.writes)))
    pw.close()
  }
}
