import BorderType.road
import scala.collection.immutable.SortedMap

case class Index(value: Int) {
  require(value >= 0 && value <= 14, "Value must be between 0 and 14")
}

// Companion Object for Ordering
object Index {
  // Implicit ascending ordering for Index inside the companion object
  implicit val ascendingOrdering: Ordering[Index] = Ordering.by(_.value)
}

class Tabletop {
  // Create a Map with keys 0 0 to 14 14 and None as values
  private val cardMap: SortedMap[(Index, Index), Option[Card]] = SortedMap((for {
    i <- 0 to 14
    j <- 0 to 14
  } yield ((Index(i), Index(j)) -> Option.empty[Card])).toSeq: _*)   // unpack sequence

  def constructTabletop(): String = {
    val strBuilder = new StringBuilder()
    for (i <- 0 to 3) {
      strBuilder.append("\n" * 2)
      for (j <- 0 to 3) {
        strBuilder.append(" " * 3 + i + " " + j + " " * 4)
      }
      strBuilder.append("\n" * 3)
    }
    strBuilder.toString()
  }

  def constructTabletopFromMap(): String = {
    val strBuilder = new StringBuilder()

    // Iterate through the whole map
    //cardMap.foreach { case ((index1, index2), value) =>
    //  println(s"Key: (${index1.value}, ${index2.value}), Value: $value")
    //}
    // Loop 5 times for every line in a card
    for (i <- 0 to 4) {
      for (j <- 0 to 14) {
        cardMap.get((Index(0), Index(j))) match {
          case Some(card) =>
          // Process card
          case None =>
            if (i == 2) {
              strBuilder.append(" " * 3 + i + " " + j + " " * 4)
            }
            strBuilder.append(" " * 9)
        }
    }

    }

    strBuilder.toString()
  }

  // Add Card:
  // val newCard = Card(...)
  // val updatedCardMap = cardMap + ((Index(5), Index(5)) -> Some(newCard))
  def addCardToMap(index1 : Index, index2: Index, card : Card): SortedMap[(Index, Index), Option[Card]] = {
    val updatedCardMap = cardMap + ((index1, index2) -> Some(card))
    updatedCardMap
  }
}
