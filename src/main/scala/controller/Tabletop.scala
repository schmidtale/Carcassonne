package controller

import model.Index
import model.Card
import model.TileMap

class Tabletop {
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

  def constructTabletopFromMap(map: TileMap): String = {
    val strBuilder = new StringBuilder
    val provider = new TextProvider

    // Loop through all rows
    for (i <- 0 to 14) {
      // Loop through every column 5 times for every line in a card
      for (l <- 0 to 4) {
        // Loop through all columns in the i th row
        for (j <- 0 to 14) {
          map.data.get((Index(i), Index(j))).flatten match {
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
  def emptyMap(): TileMap = {
    TileMap()
  }

  // Add Card:
  // val newCard = Card(...)
  // val updatedCardMap = cardMap + ((Index(5), Index(5)) -> Some(newCard))
  def addCardToMap(index1: Index, index2: Index, card: Card, oldMap: TileMap): TileMap = {
    val updatedCardMap = oldMap.data + ((index1, index2) -> Some(card))
    TileMap(updatedCardMap)
  }
}
