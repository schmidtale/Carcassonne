package model.gameDataComponent
import util.Prototype

import scala.collection.immutable.SortedMap

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

class TileMap(// Create a Map with keys 0 0 to 14 14 and None as values
              val data: SortedMap[(Index, Index), Option[Tile]] = SortedMap((for {
                i <- 0 to 14
                j <- 0 to 14
              } yield ((Index(i), Index(j)) -> Option.empty[Tile])).toSeq: _*))// unpack sequence)
extends Prototype[TileMap] with TileMapTrait
{
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
}

