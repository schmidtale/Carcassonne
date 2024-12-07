package _view

import model.{TextProvider, Tile}
import controller.Tabletop
import model.Index
import util.{MusicPlayer, Observer}

import scala.collection.immutable.Queue
import java.io.InputStream
import scala.io.Source
import scala.util.Try

class TextUI(tabletop: Tabletop) extends Observer {
  tabletop.add(this)
  private val textProvider = new TextProvider

  //printMap

  def exec(inputStream: InputStream): Int = {

    //get and print Tile from Queue
    val drawnTile = tabletop.gameData.currentTile()
    print("next tile:\n" + textProvider.toText(drawnTile) + "\n")
    val helpStr0 = "enter desired tile placement in the following format:\n" +
      "rotation line column\n" +
      "[0-3]   [0-14][0-14]\n"
    print(helpStr0)

    val line = Source.fromInputStream(inputStream).getLines().next() // Read the first line
    val placementInfo = readPlacement(line)

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
      tabletop.gameData.turn
    }
    // TODO use turn from gameData
    else {
//      // Undo
//      if (line == "z") {
//        turn - 1
//        // Redo
//      }
//      else if (line == "y") {
//        turn + 1
//      }
//      else if (line == "n") {
//        0
//      }
//      else {
//        turn
//      } 
     tabletop.gameData.turn
    }
  }


  def readPlacement(line: String): (Boolean, Int, Index, Index) = {
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
      case _ => line.split("\\s+").flatMap(part => Try(part.toInt).toOption).toList match {
        case rotation :: index1 :: index2 :: Nil if index1 >= 0 && index1 <= 14 && index2 >= 0 && index2 <= 14 =>
          (true, rotation, Index(index1), Index(index2))
        case _ =>
          (false, 0, Index(0), Index(0))
      }
    }
    placementInfo
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


