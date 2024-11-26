package controller
import model.{GameState, Index, Tile}
import util.Command

class addCommand(index1: Index, index2: Index, tile: Tile, tabletop: Tabletop) extends Command {
  //var memento: GameState = tabletop.gameState
  override def doStep(): Unit = {
    //memento = tabletop.gameState
    
    //tabletop.tileMap = tabletop.tileMap.add(index1, index2, Some(tile))
  }

  override def undoStep(): Unit = {
    //val new_memento = controller.gameState
    //controller.gameState = memento
    //memento = new_memento
    
    //tabletop.tileMap = tabletop.tileMap.add(index1, index2, Option.empty[Tile])
  }

  override def redoStep(): Unit = {
    //val new_memento = controller.gameState
    //controller.gameState = memento
    //memento = new_memento
    
    //tabletop.tileMap = tabletop.tileMap.add(index1, index2, Some(tile))
  }
}
