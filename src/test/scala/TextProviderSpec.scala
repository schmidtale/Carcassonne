import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import model.Card
import model.BorderType._
import model.LiegemanPosition.north
import model.LiegemanType.knight

class TextProviderSpec extends AnyWordSpec {
  val card2 = new Card(false, true, borders = Vector(town, pasture, pasture, town), (knight, north))
  val textProvider = new TextProvider
  val expectedStringCard2 = "* B B B *\nB B . . .\nB . . . .\nB . . . .\n* . . . *"

  "A TextProvider" should {
    "provide a string representation of a whole Card" in {
      assert(textProvider.toText(card2) == expectedStringCard2)
    }
    
    "translate the data of a card to Strings used by the TUI" in {
      assert(textProvider.line(card2,0) == "* B B B *")
      assert(textProvider.line(card2,1) == "B B . . .")
      assert(textProvider.line(card2,2) == "B . . . .")
      assert(textProvider.line(card2,3) == "B . . . .")
      assert(textProvider.line(card2,4) == "* . . . *")
    }

    
  }

}
