package controller
import model.{GameData, Index, MenuState, PlacingLiegemanState, PlacingTileState, ReviewState, Tile}
import util.Command

class TurnCommand(index1: Index, index2: Index, tile: Tile, tabletop: Tabletop) extends Command {
  private var memento: GameData = tabletop.gameData
  // Modifies GameData, simulating progress of a turn
  override def doStep(): Unit = {

    // TODO Different Step depending on state
    tabletop.gameData.state match
      case MenuState => // add Players to GameData
      case PlacingTileState => // add tile to map (see bottom) / skip
      case PlacingLiegemanState => // overwrite tile with itself + liegeman
      case ReviewState => // Tally points


    memento = tabletop.gameData.deepClone()
    // Execute turn logic e.g. turn counter, card not placeable, update player state
    val newMap = tabletop.gameData.map.add(index1, index2, Some(tile))
    // TODO constructor in gamedata that replaces increment and withmap
    tabletop.incrementTurn()
    tabletop.gameData = tabletop.gameData.withMap(newMap)
  }

  override def undoStep(): Unit = {
    val new_memento = tabletop.gameData.deepClone()
    tabletop.gameData = memento
    memento = new_memento
  }

  override def redoStep(): Unit = {
    undoStep(); // swaps states back again
  }
}
