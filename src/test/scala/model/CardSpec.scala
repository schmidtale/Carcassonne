package model

import BorderType.*
import LiegemanPosition.*
import LiegemanType.*
import Orientation.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class CardSpec extends AnyWordSpec {
  val card = new Card(borders = Vector(road, town, road, pasture))
  val card2 = new Card(false, true, borders = Vector(town, pasture, pasture, town), (knight, north))
  val card3 = new Card(false, borders = Vector(road, road, road, pasture))

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
      assert(card.fieldConnections.isInstanceOf[Vector[Boolean]])
      assert(card.fieldConnections.length == 4)
      assert(card.fieldConnections == Vector(false, true, false, true))
      assert(card2.fieldConnections == Vector(true, true, true, true))
      assert(card3.fieldConnections == Vector(false, false, false, true))
    }
    "return if town borders are connected (boolean) which must be false when < 2 town borders are present" in {
      assert(card.townConnection == false)
      assert(card2.townConnection == true)
      assert(card3.townConnection == false)
    }
    "return the type of the possibly present liegeman" in {
      assert(card.liegeman(0) == none | card.liegeman(0) == waylayer |
        card.liegeman(0) == monk | card.liegeman(0) == knight | card.liegeman(0) == peasant)
      assert(card2.liegeman(0) == knight)
    }
    "return the position of a present liegeman" in {
      assert(card2.liegeman(1) == north)
    }
    "return a card with suitably shifted borders and structures (90° clockwise)" in {
      val cardRotated = card.rotate
      assert(cardRotated.borderType(northern) == pasture)
      assert(cardRotated.borderType(eastern) == road)
      assert(cardRotated.borderType(southern) == town)
      assert(cardRotated.borderType(western) == road)
      val card2Rotated = card2.rotate
      assert(card2Rotated.borderType(northern) == town)
      assert(card2Rotated.borderType(eastern) == town)
      assert(card2Rotated.borderType(southern) == pasture)
      assert(card2Rotated.borderType(western) == pasture)
      val cardRotatedTwice = cardRotated.rotate
      assert(cardRotatedTwice.borderType(northern) == road)
      assert(cardRotatedTwice.borderType(eastern) == pasture)
      assert(cardRotatedTwice.borderType(southern) == road)
      assert(cardRotatedTwice.borderType(western) == town)
    }
    "check if two cards are equal" in {
      assert(card.equals(card))
      assert(!card.equals(card2))
    }
    "return false if you compare a card to something else" in {
      val that = "empty"
      assert(!card.equals(that))
    }
  }
}

