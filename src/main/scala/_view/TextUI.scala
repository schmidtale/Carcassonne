package _view
import controller.TextProvider
import model.TileStack
import model.Tile
import model.TileMap
import controller.Tabletop
import model.Index

import scala.collection.immutable.SortedMap
import scala.io.StdIn.readLine

object TextUI {
  private val textProvider = new TextProvider
  private val tabletop = new Tabletop
  private val stack = tabletop.tileStack()
  private val initialMap = tabletop.addTileToMap(Index(7), Index(7), tabletop.startingTile(), tabletop.emptyMap())

  //printMap
  print(tabletop.constructTabletopFromMap(initialMap))
  def exec(): Unit = {
    
    //get and print Tile from Queue
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

  def updateMap(b: Boolean, line: Index, column: Index, card: Tile, t: Tabletop, oldMap: TileMap): TileMap = {
    print(b)
    t.addTileToMap(line, column, card, oldMap)
  }
}


