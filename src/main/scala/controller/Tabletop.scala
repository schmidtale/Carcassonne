package controller

import model.Card
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
  } yield ((Index(i), Index(j)) -> Option.empty[Card])).toSeq: _*) // unpack sequence

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

  def constructTabletopFromMap(map: SortedMap[(Index, Index), Option[Card]]): String = {
    val strBuilder = new StringBuilder
    val provider = new TextProvider

    // Loop through all rows
    for (i <- 0 to 14) {
      // Loop through every column 5 times for every line in a card
      for (l <- 0 to 4) {
        // Loop through all columns in the i th row
        for (j <- 0 to 14) {
          map.get((Index(i), Index(j))).flatten match {
            case Some(card) =>
              // Append i th line of column to String
              strBuilder.append(provider.line(card, l.asInstanceOf[Int & (0 | 1 | 2 | 3 | 4)]))
              strBuilder.append(" ")
            // Print empty card (index)
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
    val tabletopString = strBuilder.toString()
    tabletopString
  }

  // return an empty with empty Values
  def emptyMap() : SortedMap[(Index, Index), Option[Card]] = {
    cardMap
  }

  // Add Card:
  // val newCard = Card(...)
  // val updatedCardMap = cardMap + ((Index(5), Index(5)) -> Some(newCard))
  def addCardToMap(index1: Index, index2: Index, card: Card, oldMap: SortedMap[(Index, Index), Option[Card]]): SortedMap[(Index, Index), Option[Card]] = {
    val updatedCardMap = oldMap + ((index1, index2) -> Some(card))
    updatedCardMap
  }
}
