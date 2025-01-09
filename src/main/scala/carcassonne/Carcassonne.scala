package carcassonne

import carcassonne._view.{GUI, TextUI}
import javafx.embed.swing.JFXPanel
import util.MusicPlayer
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.Color.*

import scala.collection.immutable.Queue
import carcassonne.controller.controllerComponent.ControllerTrait
import carcassonne.controller.controllerComponent.ControllerTrait
import carcassonne.model.gameDataComponent.TextProviderTrait
import com.google.inject.{Guice, Injector}

object Carcassonne {
    val injector: Injector = Guice.createInjector(new CarcassonneModule())

    val controller: ControllerTrait = injector.getInstance(classOf[ControllerTrait]) // Uses the given instance from CarcassonneModule
    // Use the given TextProviderTrait from CarcassonneModule
    val textProvider: TextProviderTrait = injector.getInstance(classOf[TextProviderTrait])
    private val gui = new GUI(controller)
    private val tui = new TextUI(controller, textProvider)

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