package carcassonne

import carcassonne._view.{GUI, TextUI}
import javafx.embed.swing.JFXPanel
import util.MusicPlayer
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.Color.*

import scala.collection.immutable.Queue
import carcassonne.CarcassonneModule.given
import carcassonne.controller.controllerComponent.ControllerTrait
import carcassonne.controller.controllerComponent.ControllerTrait
import carcassonne.model.gameDataComponent.TextProviderTrait

object Carcassonne {
    val controller: ControllerTrait = summon[ControllerTrait]  // Uses the given instance from CarcassonneModule
    // Use the given TextProviderTrait from CarcassonneModule
    val textProvider: TextProviderTrait = summon[TextProviderTrait]
    private val gui = new GUI(using controller)
    private val tui = new TextUI(using controller, textProvider)

    new JFXPanel(); // this will prepare JavaFX toolkit and environment

    @main
    def main(): Unit = {
        val loopPlayer = MusicPlayer("gameplayLoop")
        loopPlayer.play()

        new Thread(() => {
            gui.main(Array.empty)
        }).start()
        controller.notifyObservers()


        while (controller.gameData.turn < controller.gameData.stack.size) {
             tui.exec(System.in)
        }
        // TODO Call function that calculates resulting points from TUI
    }
}