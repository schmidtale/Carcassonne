import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import Orientation._
import BorderType._

class CardSpec extends AnyWordSpec {
  val card = new Card(borders = Array(road, town, road, pasture))
  val card2 = new Card(borders = Array(town, pasture, pasture, town))
  val card3 = new Card(false, borders = Array(road, road, road, pasture))

  "A card" should {
    "return the type of its northern border" in {
      assert(card.borderType(northern) == road |
        card.borderType(northern) == pasture |
        card.borderType(northern) == town)
    }
    "return a boolean when asked for the presence of a monastery" in {
      assert(!card.monastery | card.monastery)
    }
    "return how its inner fields are connected" in {
      assert(card.fieldConnections.isInstanceOf[Array[Boolean]])
      assert(card.fieldConnections.length == 4)
      assert(card.fieldConnections sameElements Array(false, true, false, true))
      assert(card2.fieldConnections sameElements Array(true, true, true, true))
      assert(card3.fieldConnections sameElements Array(false, false, false, true))
    }
  }
}
