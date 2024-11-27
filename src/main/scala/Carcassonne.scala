import _view.TextUI
import controller.Tabletop
import model.GameState
import util.MusicPlayer

object Carcassonne {
    val tabletop = new Tabletop(GameState().initialState())
    private val tui = new TextUI(tabletop)
    tabletop.notifyObservers()
    private var currentTurn: Int = 0
    @main
    def main(): Unit = {
        val loopPlayer = MusicPlayer.createPlayer("gameplayLoop")
        loopPlayer.play()
        while (currentTurn < tabletop.gameState.stack.size) {
            currentTurn = tui.exec(currentTurn, tabletop.gameState.stack, System.in)
        }
        // TODO Call function that calculates resulting points from GUI
    }
}