package model
import scala.collection.immutable.SortedMap

case class Index(value: Int) {
  require(value >= 0 && value <= 14, "Value must be between 0 and 14")
}

// Companion Object for Ordering
object Index {
  // Implicit ascending ordering for Index inside the companion object
  implicit val ascendingOrdering: Ordering[Index] = Ordering.by(_.value)
}

class TileMap(// Create a Map with keys 0 0 to 14 14 and None as values
              val data: SortedMap[(Index, Index), Option[Card]] = SortedMap((for {
                i <- 0 to 14
                j <- 0 to 14
              } yield ((Index(i), Index(j)) -> Option.empty[Card])).toSeq: _*))// unpack sequence)

