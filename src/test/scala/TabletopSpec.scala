
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

import scala.collection.immutable.SortedMap

class TabletopSpec extends AnyWordSpec {
  val tabletop = new Tabletop
  private val card = CardStack().starting_card
  private val emptyCard = Option.empty[Card]

  "A tabletop" should {
    "construct the correct tabletop grid as a String" in {
      val expectedOutput = (
          "\n\n   0 0       0 1       0 2       0 3    \n\n\n" +
          "\n\n   1 0       1 1       1 2       1 3    \n\n\n" +
          "\n\n   2 0       2 1       2 2       2 3    \n\n\n" +
          "\n\n   3 0       3 1       3 2       3 3    \n\n\n"
        )
      assert(tabletop.constructTabletop() == expectedOutput)
    }
    "construct 15*15 tabletop grid from a map" in {
      val expectedOutput = (
        ""
        )
      assert(tabletop.constructTabletopFromMap() == expectedOutput)
    }
    "let a card be added to the card map" in {
      val newMap = tabletop.addCardToMap(Index(8), Index(8), card)
      assert(newMap.isInstanceOf[SortedMap[(Index, Index), Option[Card]]])
      val cardFromMap = newMap((Index(8), Index(8))).get
      assert(cardFromMap.equals(card))
    }
    "return an empty Card when none is in the map" in {
      val newMap = tabletop.addCardToMap(Index(8), Index(8), card)
      val cardFromMap = newMap((Index(0), Index(0)))
      assert(cardFromMap == emptyCard)
    }
  }
}
