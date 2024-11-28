import _view.TextUI
import controller.Tabletop
import model.GameData
import util.MusicPlayer

object Carcassonne {
    val tabletop = new Tabletop(GameData().initialState())
    private val tui = new TextUI(tabletop)
    tabletop.notifyObservers()
    private var currentTurn: Int = 0
    @main
    def main(): Unit = {
        val loopPlayer = MusicPlayer.createPlayer("gameplayLoop")
        loopPlayer.play()
        while (currentTurn < tabletop.gameData.stack.size) {
            currentTurn = tui.exec(currentTurn, tabletop.gameData.stack, System.in)
        }
        // TODO Call function that calculates resulting points from GUI
    }
}