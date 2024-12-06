import _view.{GUI, TextUI}
import controller.Tabletop
import model.{GameData, PlayerState}
import util.MusicPlayer
import model.Color.*

import scala.collection.immutable.Queue

object Carcassonne {
    val tabletop = new Tabletop(GameData(players = Queue(PlayerState(blue), PlayerState(red), PlayerState(yellow), PlayerState(green), PlayerState(black))).initialState())
    private val tui = new TextUI(tabletop)
    private val gui = new GUI(tabletop)
    tabletop.notifyObservers()

    new Thread(() => {
        gui.main(Array.empty)
    }).start()

    private var currentTurn: Int = 0
    @main
    def main(): Unit = {
        val loopPlayer = MusicPlayer("gameplayLoop")
        loopPlayer.play()
        while (currentTurn < tabletop.gameData.stack.size) {
            currentTurn = tui.exec(currentTurn, tabletop.gameData.stack, System.in)
        }
        // TODO Call function that calculates resulting points from GUI
    }
}