package carcassonne

import carcassonne._view.{GUI, TextUI}
import javafx.embed.swing.JFXPanel
import util.MusicPlayer
import carcassonne.CarcassonneModule.given
import carcassonne.controller.controllerComponent.ControllerTrait
import carcassonne.model.fileIoComponent.FileIOTrait
import carcassonne.model.gameDataComponent.TextProviderTrait

object Carcassonne {
    // Uses the given instances from CarcassonneModule
    val controller: ControllerTrait = summon[ControllerTrait]
    val textProvider: TextProviderTrait = summon[TextProviderTrait]
    private val gui = new GUI(using controller)
    private val tui = new TextUI(using controller, textProvider)

    // TODO remove example usage of fileIO
    val fileIO: FileIOTrait = summon[FileIOTrait]
    fileIO.save(controller.gameData)

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
    }
}