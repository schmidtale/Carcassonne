package carcassonne

import carcassonne.CarcassonneModule.given
import carcassonne._view.{GUI, TextUI}
import carcassonne.controller.controllerComponent.ControllerTrait
import carcassonne.model.gameDataComponent.TextProviderTrait
import carcassonne.util.MusicPlayer

import javafx.embed.swing.JFXPanel

object Carcassonne {
  // Uses the given instances from CarcassonneModule
  val controller: ControllerTrait = summon[ControllerTrait]
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
  }
}