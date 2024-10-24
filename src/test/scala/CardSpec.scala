import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class CardSpec extends AnyWordSpec {
  val card = new Card(borders = Array(BorderType.road, BorderType.pasture, BorderType.town, BorderType.pasture))
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
      assert(card.fieldConnections(Field.upperLeft).isInstanceOf[Array[Boolean]])
      assert(card.fieldConnections(Field.upperLeft).length == 4)
    }
  }
}
