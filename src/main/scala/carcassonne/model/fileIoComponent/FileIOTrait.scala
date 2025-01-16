package carcassonne.model.fileIoComponent

import carcassonne.model.gameDataComponent.GameDataTrait

trait FileIOTrait {
  def load: GameDataTrait
  def save(gameData: GameDataTrait): Unit
}
