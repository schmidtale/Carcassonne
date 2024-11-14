package _view
import controller.TextProvider
import model.TileStack
import model.Tile
import model.TileMap
import controller.Tabletop
import model.Index

import scala.collection.immutable.{Queue, SortedMap}
import scala.io.StdIn.readLine

object TextUI {
  private val textProvider = new TextProvider
  private val tabletop = new Tabletop

  //printMap
  print(tabletop.constructTabletopFromMap(tabletop.initialMap()))
  def exec(parameters: (TileMap, Int), stack: Queue[Tile]): (TileMap, Int) = {

    //get and print Tile from Queue
    val drawnTile = stack(parameters(1))
    print("next card:\n" + textProvider.toText(drawnTile) + "\n")
    val helpStr0 = "enter desired card placement in the following format:\n" +
      "rotation line column\n" +
      "[0-3]   [0-14][0-14]\n"
    print(helpStr0)

    val placementInfo = readPlacement
    val cardToPlace = drawnTile.rotate(placementInfo._2)
    // check legality:
    //while (!isLegalPlacement)...

    //---liegeman
    //---check legality

    val updatedMap = updateMap(placementInfo._1, placementInfo._3, placementInfo._4, cardToPlace, tabletop, parameters(0))

    print(tabletop.constructTabletopFromMap(updatedMap))
    (updatedMap, parameters(1) + 1)
  }


  private def readPlacement: (Boolean, Int, Index, Index) = {
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

  def updateMap(b: Boolean, line: Index, column: Index, card: Tile, t: Tabletop, oldMap: TileMap): TileMap = {
    print(b)
    t.addTileToMap(line, column, card, oldMap)
  }
}


