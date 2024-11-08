import scala.io.StdIn.readLine

def startTUI(): Unit = {
  //construct Queue
  val cardStack = construct()
  //initialize Tabletop

  //printMap
  //get and printCard from Queue
  val i = 0
  val drawnCard = cardStack(i)
  val helpStr0 = "enter desired card placement in the following format:\n" +
    "rotation line column\n" +
    "[0-3]   [0-14][0-14]\n"
  print(helpStr0)
  val placementInfo = readPlacement
  print(placementInfo._1)

  //rotate aufrufen
  val cardToPlace = rotate(placementInfo._2)
  //---liegeman
  //---check legality
  //addCardToMap aufrufen
  addCardToMap()
  //move on in Queue
}

//einlesen readLine().split(" ") und parsen
def readPlacement: (String, Int, Int, Int) = {
  val commandSet = readLine.split(" ")
  val rotationCount = commandSet(0).toIntOption
  val line = commandSet(1).toIntOption
  val column = commandSet(2).toIntOption
  if (rotationCount.isEmpty | line.isEmpty | column.isEmpty) {
    => ""
  }
}

