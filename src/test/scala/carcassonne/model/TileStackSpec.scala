package carcassonne.model

import carcassonne.model.gameDataComponent.gameDataBaseImplementation.{Tile, TileStack}
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.immutable.Queue


class TileStackSpec extends AnyWordSpec {

  val stack = new TileStack()

  "A model.CardStack" should {
    "return a Queue of all cards" in {
      assert(stack.construct().isInstanceOf[Queue[Tile]])
    }
    "have a size of 71 cards" in {
      assert(stack.construct().size == 71)
    }
  }
}
