import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import BorderType._
import LiegemanPosition.north
import LiegemanType.knight

class TextProviderSpec extends AnyWordSpec {
  val card2 = new Card(false, true, borders = Vector(town, pasture, pasture, town), (knight, north))

  "A TextProvider" should {
    "translate the data of a card to Strings used by the TUI" in {
      assert(card2 line 0 == "B B B B B")
      assert(card2 line 1 == "B B . . .")
      assert(card2 line 2 == "B . . . .")
      assert(card2 line 3 == "B . . . .")
      assert(card2 line 4 == "B . . . .")
    }
  }

}
