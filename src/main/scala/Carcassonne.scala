import _view.TextUI
import controller.Tabletop
import model.TileMap

import scala.util.Random
object Carcassonne {
    val tabletop = new Tabletop(new TileMap)
    tabletop.initialMap()
    private val tui = new TextUI(tabletop)
    tabletop.notifyObservers()
    private val sortedStack = tabletop.tileStack()
    private val shuffledStack = Random.shuffle(sortedStack)
    private var currentTurn: Int = 0
    @main
    def main(): Unit = {
        
        while (currentTurn < shuffledStack.size) {
            currentTurn = tui.exec(currentTurn, shuffledStack, System.in)
        }
        // TODO Call function that calculates resulting points from GUI
    }
}