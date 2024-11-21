package _view
import model.{TextProvider, Tile}
import controller.Tabletop
import model.Index
import util.Observer

import scala.collection.immutable.Queue
import scala.io.StdIn.readLine

class TextUI(tabletop: Tabletop) extends Observer {
  tabletop.add(this)
  private val textProvider = new TextProvider

  //printMap

  def exec(turn: Int, stack: Queue[Tile]): Int = {

    //get and print Tile from Queue
    val drawnTile = stack(turn)
    print("next tile:\n" + textProvider.toText(drawnTile) + "\n")
    val helpStr0 = "enter desired tile placement in the following format:\n" +
      "rotation line column\n" +
      "[0-3]   [0-14][0-14]\n"
    print(helpStr0)

    val placementInfo = readPlacement
    val cardToPlace = drawnTile.rotate(placementInfo._2)
    // check legality:
    //while (!isLegalPlacement)...

    //---liegeman
    //---check legality

    updateMap(placementInfo._1, placementInfo._3, placementInfo._4, cardToPlace)

    // TODO if placement correct and legal move and legal liegeman placement
    turn + 1
  }


  def readPlacement: (Boolean, Int, Index, Index) = {
    try {
      val commandSet = readLine.split(" ")
      val rotationCount = commandSet(0).toIntOption
      val line = commandSet(1).toIntOption
      val column = commandSet(2).toIntOption

      if (rotationCount.isEmpty | line.isEmpty | column.isEmpty) {
        (false, 0, Index(0), Index(0))
      } else {
        (true, rotationCount.get, Index(line.get), Index(column.get))
      }
    } catch {
      case _: Exception => (false, 0, Index(0), Index(0))
    }

  }

  def updateMap(b: Boolean, line: Index, column: Index, card: Tile): Unit = {
    print(b)
    tabletop.addTileToMap(line, column, card)
  }

  override def update(): Unit = {
    print(tabletop.constructTabletopFromMap())
  }
}


