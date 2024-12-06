package _view

import model.{TextProvider, Tile}
import controller.Tabletop
import model.Index
import util.{MusicPlayer, Observer}

import scala.collection.immutable.Queue
import java.io.InputStream
import scala.io.Source

class TextUI(tabletop: Tabletop) extends Observer {
  tabletop.add(this)
  private val textProvider = new TextProvider

  //printMap

  def exec(turn: Int, stack: Queue[Tile], inputStream: InputStream): Int = {

    //get and print Tile from Queue
    val drawnTile = stack(turn)
    print("next tile:\n" + textProvider.toText(drawnTile) + "\n")
    val helpStr0 = "enter desired tile placement in the following format:\n" +
      "rotation line column\n" +
      "[0-3]   [0-14][0-14]\n"
    print(helpStr0)

    val line = Source.fromInputStream(inputStream).getLines().next() // Read the first line
    val placementInfo = line match {
      // "new" reset game
      case "n" =>
        tabletop.resetGameData()
        (false, 0, Index(0), Index(0)) // not a placement or invalid
      // "strg Z" undo
      case "z" =>
        tabletop.undo()
        (false, 0, Index(0), Index(0))
      // strg "y" redo
      case "y" =>
        tabletop.redo()
        (false, 0, Index(0), Index(0))
      case _ => line.toList.filter(c => c != ' ').filter(_.isDigit).map(c => c.toString.toInt) match {
        case rotation :: index1 :: index2 :: Nil =>
          (true, rotation, Index(index1), Index(index2))
        case _ =>
          (false, 0, Index(0), Index(0))
      }
    }

    // valid placement
    if (placementInfo._1) {
      val cardToPlace = drawnTile.rotate(placementInfo._2)
      // check legality:
      //while (!isLegalPlacement)...

      //---liegeman
      //---check legality

      updateMap(placementInfo._1, placementInfo._3, placementInfo._4, cardToPlace)
      // TODO if placement correct and legal move and legal liegeman placement

      val roundFinishedPlayer = MusicPlayer("TownJingle")
      roundFinishedPlayer.play()
      turn + 1
    }
    // TODO use turn from gameData
    else {
      // Undo
      if (line == "z") {
        turn - 1
        // Redo
      }
      else if (line == "y") {
        turn + 1
      }
      else if (line == "n") {
        0
      }
      else {
        turn
      }
    }
  }


  def readPlacement(input: InputStream): (Boolean, Int, Index, Index) = {
    try {
      val line = Source.fromInputStream(input).getLines().next() // Read the first line
      val commandSet = line.split(" ")
      val rotationCount = commandSet(0).toIntOption
      val row = commandSet(1).toIntOption
      val column = commandSet(2).toIntOption

      if (rotationCount.isEmpty || row.isEmpty || column.isEmpty) {
        (false, 0, Index(0), Index(0))
      } else {
        (true, rotationCount.get, Index(row.get), Index(column.get))
      }
    } catch {
      case _: Exception => (false, 0, Index(0), Index(0))
    }
  }


  def updateMap(b: Boolean, line: Index, column: Index, card: Tile): Unit = {
    if (b) {
      tabletop.addTileToMap(line, column, card)
    }
  }

  override def update(): Unit = {
    print(tabletop.constructTabletopFromMap())
  }
}


