import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class CardSpec extends AnyWordSpec {
  val card = new Card(borders = Array(BorderType.road, BorderType.town, BorderType.road, BorderType.pasture))
  val card2 = new Card(borders = Array(BorderType.town, BorderType.pasture, BorderType.pasture, BorderType.town))
  val card3 = new Card(false, borders = Array(BorderType.road, BorderType.road, BorderType.road, BorderType.pasture))
  
  "A card" should {
    "return the type of its northern border" in {
      assert(card.borderType(Orientation.northern) == BorderType.road |
        card.borderType(Orientation.northern) == BorderType.pasture |
        card.borderType(Orientation.northern) == BorderType.town)
    }
    "return a boolean when asked for the presence of a monastery" in {
      assert(!card.monastery | card.monastery)
    }
    "return how its inner fields are connected" in {
      assert(card.fieldConnections.isInstanceOf[Array[Boolean]])
      assert(card.fieldConnections.length == 4)
      assert(card.fieldConnections == Array(false, true, false, true))
      assert(card2.fieldConnections == Array(true, true, true, true))
      assert(card3.fieldConnections == Array(false, false, false, true))
    }
  }
}
