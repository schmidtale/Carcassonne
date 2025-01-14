package carcassonne.model.fileIoComponent.fileIoJSONImpl

import carcassonne.CarcassonneModule.given
import carcassonne.model.fileIoComponent.FileIOTrait
import carcassonne.model.gameDataComponent.GameDataTrait
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.GameData
import play.api.libs.json.{JsValue, Json}
import scala.io.Source

import scala.util.Using

class FileIO extends FileIOTrait {
  val gameData: GameDataTrait = summon[GameDataTrait]

  override def load: GameDataTrait = {
    val json: JsValue = Using(Source.fromFile("gameData.json")) {source => Json.parse(source.getLines().mkString)}.get
    json.as[GameDataTrait](gameData.reads)
  }

  override def save(gameData: GameDataTrait): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("gameData.json"))
    pw.write(Json.prettyPrint(Json.toJson(gameData)(gameData.writes)))
    pw.close()
  }
}
