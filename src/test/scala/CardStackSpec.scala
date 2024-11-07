import org.scalatest.wordspec.AnyWordSpec

import scala.collection.mutable
import scala.collection.immutable.Queue


class CardStackSpec extends AnyWordSpec {

  val stack = new CardStack()

  "A CardStack" should {
    "return a Queue of all cards" in {
      assert(stack.construct().isInstanceOf[Queue[Card]])
    }
    "have a size of 71" in {
      assert(stack.construct().size == 71)
    }
  }
}
