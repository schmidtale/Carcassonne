import scala.collection.immutable.SortedMap
import scala.io.StdIn.readLine

def startTUI(): Unit = {
  val textProvider = new TextProvider
  val tabletop = new Tabletop
  val cardStack = new CardStack
  val stack = cardStack.construct()
  val initialMap = tabletop.addCardToMap(Index(7),Index(7),cardStack.starting_card)

  //printMap
  print(tabletop.constructTabletopFromMap(initialMap))
  //get and printCard from Queue
  val i = 0
  val drawnCard = stack(i)
  print("next card:\n" + textProvider.toText(drawnCard) + "\n")
  val helpStr0 = "enter desired card placement in the following format:\n" +
    "rotation line column\n" +
    "[0-3]   [0-14][0-14]\n"
  print(helpStr0)

  val placementInfo = readPlacement
  val cardToPlace = drawnCard.rotate(placementInfo._2)

  //---liegeman
  //---check legality

  val updatedMap = updateMap(placementInfo._1, placementInfo._3, placementInfo._4, cardToPlace, tabletop, initialMap)
  //move on in Queue, repeat process...
  print(tabletop.constructTabletopFromMap(updatedMap))
}


def readPlacement: (Boolean, Int, Index, Index) = {
  val commandSet = readLine.split(" ")
  val rotationCount = commandSet(0).toIntOption
  val line = commandSet(1).toIntOption
  val column = commandSet(2).toIntOption
  
  if (rotationCount.isEmpty | line.isEmpty | column.isEmpty) {
    (false, 0, Index(0), Index(0))
  } else {
    (true, rotationCount.get, Index(line.get), Index(column.get))
  }
}

def updateMap(b: Boolean, line: Index, column: Index, card: Card, t: Tabletop, oldMap: SortedMap[(Index, Index), Option[Card]]): SortedMap[(Index, Index), Option[Card]] = {
  print(b)
  t.addCardToMap(line, column, card, oldMap)
}

