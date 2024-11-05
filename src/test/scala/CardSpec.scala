import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import Orientation._
import BorderType._
import LiegemanType._
import LiegemanPosition._

class CardSpec extends AnyWordSpec {
  val card = new Card(borders = Array(road, town, road, pasture))
  val card2 = new Card(borders = Array(town, pasture, pasture, town), knight, north)
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
    "return if town borders are connected or not as a Boolean" +
      "which must be false when less than 2 town borders are present" in {
      assert(card.townConnection == false)
      assert(card2.townConnection == true)
      assert(card3.townConnection == false)
    }
    "return the type of the possibly present liegeman" in {
      assert(card.liegeman == none | card.liegeman == waylayer |
        card.liegeman == monk | card.liegeman == knight | card.liegeman == peasant)
      assert(card2.liegeman == knight)
    }
    "return the position of a present liegeman" in {
      assert(card2.position == north)
    }
  }
  
  "The functions operating on cards" should {
    "return a card with suitably shifted borders and structures (90° clockwise)" in {
      cardRotated = card.rotate
      assert(cardRotated.borderType(northern) == pasture)
      assert(cardRotated.borderType(eastern) == road)
      assert(cardRotated.borderType(southern) == town)
      assert(cardRotated.borderType(western) == road)
      card2Rotated = card2.rotate
      assert(card2Rotated.borderType(northern) == town)
      assert(card2Rotated.borderType(eastern) == town)
      assert(card2Rotated.borderType(southern) == pasture)
      assert(card2Rotated.borderType(western) == pasture)
      cardRotatedTwice = cardRotated.rotate
      assert(cardRotated.borderType(northern) == road)
      assert(cardRotated.borderType(eastern) == pasture)
      assert(cardRotated.borderType(southern) == road)
      assert(cardRotated.borderType(western) == town)
    }
  }
}
