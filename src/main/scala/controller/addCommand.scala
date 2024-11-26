package controller
import model.{GameState, Index, Tile}
import util.Command

class addCommand(index1: Index, index2: Index, tile: Tile, tabletop: Tabletop) extends Command {
  var memento: GameState = tabletop.gameState
  override def doStep(): Unit = {
    memento = tabletop.gameState

    val newMap = tabletop.gameState.map.add(index1, index2, Some(tile))
    tabletop.gameState = tabletop.gameState.withMap(newMap)
  }

  override def undoStep(): Unit = {
    val new_memento = tabletop.gameState
    tabletop.gameState = memento
    memento = new_memento
  }

  override def redoStep(): Unit = {
    val new_memento = tabletop.gameState
    tabletop.gameState = memento
    memento = new_memento
  }
}
