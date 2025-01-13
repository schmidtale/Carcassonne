package carcassonne.model.gameDataComponent.gameDataBaseImplementation

import carcassonne.model.gameDataComponent.{TileMapTrait, TileTrait}
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.Index
import carcassonne.util.Prototype

import scala.collection.immutable.SortedMap
import scala.xml.Elem

case class Index(value: Int) {
  require(value >= 0 && value <= 14, "Value must be between 0 and 14")

  override def equals(obj: Any): Boolean = {
    obj match
      case that: Index =>
        this.value == that.value

      case _ => false
  }

  override def hashCode(): Int = (this.value).##
}

// Companion Object for Ordering
object Index {
  // Implicit ascending ordering for Index inside the companion object
  implicit val ascendingOrdering: Ordering[Index] = Ordering.by(_.value)
}

class TileMap( // Create a Map with keys 0 0 to 14 14 and None as values
               val data: SortedMap[(Index, Index), Option[Tile]] = SortedMap((for {
                 i <- 0 to 14
                 j <- 0 to 14
               } yield ((Index(i), Index(j)) -> Option.empty[Tile])).toSeq: _*)) // unpack sequence)
  extends Prototype[TileMap] with TileMapTrait {
  override def toString: String = {
    val strBuilder = new StringBuilder
    val provider = new TextProvider

    // Loop through all rows
    for (i <- 0 to 14) {
      // Loop through every column 5 times for every line in a tile
      for (l <- 0 to 4) {
        // Loop through all columns in the i th row
        for (j <- 0 to 14) {
          data.get((Index(i), Index(j))).flatten match {
            case Some(tile) =>
              // Append i th line of column to String
              strBuilder.append(tile.line(l.asInstanceOf[Int & (0 | 1 | 2 | 3 | 4)]))
              strBuilder.append(" ")
            // Print empty tile (index)
            case None =>
              if (l == 2) {
                strBuilder.append(" " * 3 + i.toHexString + " " + j.toHexString + " " * 4)
              } else {
                strBuilder.append(" " * 10)
              }
          }
        }
        strBuilder.append("\n")
      }
    }
    val tileMapString = strBuilder.toString()
    tileMapString
  }

  override def deepClone(): TileMap = {
    val tileMap = TileMap(this.data)
    tileMap
  }

  def add(index1: Index, index2: Index, tile: Option[TileTrait]): TileMap = {
    TileMap(data + ((index1, index2) -> tile.asInstanceOf[Option[Tile]]))
  }

  def toXML: Elem = {
    <tileMap>
      {data.collect {
      case ((index1, index2), Some(tile)) =>
        <entry>
          <position>
            <x>
              {index1.value}
            </x>
            <y>
              {index2.value}
            </y>
          </position>{tile.toXML}
        </entry>
    }}
    </tileMap>
  }

  def fromXML(node: scala.xml.Node): TileMap = {
    val entries = (node \ "entry").map { entry =>
      val x = (entry \ "position" \ "x").text.trim.toInt
      val y = (entry \ "position" \ "y").text.trim.toInt

      val tileNode = (entry \ "tile").headOption.getOrElse(
        throw new IllegalArgumentException("Missing <tile> node in entry")
      )
      val tile = Tile().fromXML(tileNode)
      (Index(x), Index(y), Some(tile))
    }
    var map = new TileMap()
    for (entry <- entries) {
      map = map.add(entry._1, entry._2, entry._3)
    }
    val result = map.deepClone()
    result
  }
}

object TileMap {

  import play.api.libs.json._

  implicit val indexWrites: Writes[Index] = (index: Index) => Json.toJson(index.value)

  // TileMap Writes
  implicit val tileMapWrites: Writes[TileMap] = new Writes[TileMap] {
    def writes(tileMap: TileMap): JsObject = {
      val entriesJson = tileMap.data.collect {
        case ((index1, index2), Some(tile)) =>
          Json.obj(
            "position" -> Json.obj(
              "x" -> index1.value,
              "y" -> index2.value
            ),
            "tile" -> Json.toJson(tile)
          )
      }.toSeq

      Json.obj(
        "tileMap" -> Json.obj(
          "entries" -> entriesJson
        )
      )
    }
  }

  implicit val tileMapReads: Reads[TileMap] = new Reads[TileMap] {
    def reads(json: JsValue): JsResult[TileMap] = {
      for {
        entries <- (json \ "tileMap" \ "entries").validate[Seq[JsObject]].flatMap { entrySeq  =>
          val parsedEntries = entrySeq.map { entry =>
              for {
                x <- (entry \ "position" \ "x").validate[Int]
                y <- (entry \ "position" \ "y").validate[Int]
                tile <- (entry \ "tile").validate[Tile]
              } yield {
                (Index(x), Index(y), Some(tile))
              }
          }
          // Collect all results into a single JsResult
          parsedEntries.foldLeft(JsSuccess(Seq.empty[(Index, Index, Option[Tile])]): JsResult[Seq[(Index, Index, Option[Tile])]]) {
            // combines the current accumulator with the new value.
            case (JsSuccess(acc, _), JsSuccess(value, _)) => JsSuccess(acc :+ value)
            // accumulator is a JsError, and the current entry is valid
            case (JsError(errors), JsSuccess(_, _)) => JsError(errors)
            // the accumulator is a valid JsSuccess but the current entry is a JsError
            case (JsSuccess(_, _), JsError(errors)) => JsError(errors)
            // both the accumulator and the current entry are errors
            case (JsError(err1), JsError(err2)) => JsError(err1 ++ err2)
          }
        }
      } yield {
        var map = new TileMap()
        for (entry <- entries) {
          map = map.add(entry._1, entry._2, entry._3)
        }
        val result = map.deepClone()
        result
      }
    }
  }
}

