import _view.{GUI, TextUI}
import controller.Tabletop
import javafx.embed.swing.JFXPanel
import model.{GameData, PlayerState}
import util.MusicPlayer
import model.Color.*

import scala.collection.immutable.Queue

object Carcassonne {
    val tabletop = new Tabletop(GameData(players = Queue(PlayerState(blue), PlayerState(red), PlayerState(yellow), PlayerState(green), PlayerState(black))).initialState())
    private val gui = new GUI(tabletop)
    private val tui = new TextUI(tabletop)

    new JFXPanel(); // this will prepare JavaFX toolkit and environment

    @main
    def main(): Unit = {
        val loopPlayer = MusicPlayer("gameplayLoop")
        loopPlayer.play()

        new Thread(() => {
            gui.main(Array.empty)
        }).start()
        tabletop.notifyObservers()


        while (tabletop.gameData.turn < tabletop.gameData.stack.size) {
             tui.exec(System.in)
        }
        // TODO Call function that calculates resulting points from GUI
    }
}