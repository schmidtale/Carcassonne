package model

import BorderType.*
import LiegemanPosition.*
import LiegemanType.*
import Orientation.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class TileSpec extends AnyWordSpec {
  val tile = new Tile(borders = Vector(road, town, road, pasture))
  val tile2 = new Tile(false, true, borders = Vector(town, pasture, pasture, town), (knight, north))
  val tile3 = new Tile(false, borders = Vector(road, road, road, pasture))

  "A tile" should {
    "return the type of its northern border" in {
      assert(tile.borderType(northern) == road |
        tile.borderType(northern) == pasture |
        tile.borderType(northern) == town)
    }
    "return a boolean when asked for the presence of a monastery" in {
      assert(!tile.monastery | tile.monastery)
    }
    "return how its inner fields are connected" in {
      assert(tile.fieldConnections.isInstanceOf[Vector[Boolean]])
      assert(tile.fieldConnections.length == 4)
      assert(tile.fieldConnections == Vector(false, true, false, true))
      assert(tile2.fieldConnections == Vector(true, true, true, true))
      assert(tile3.fieldConnections == Vector(false, false, false, true))
    }
    "return if town borders are connected (boolean) which must be false when < 2 town borders are present" in {
      assert(tile.townConnection == false)
      assert(tile2.townConnection == true)
      assert(tile3.townConnection == false)
    }
    "return the type of the possibly present liegeman" in {
      assert(tile.liegeman(0) == none | tile.liegeman(0) == waylayer |
        tile.liegeman(0) == monk | tile.liegeman(0) == knight | tile.liegeman(0) == peasant)
      assert(tile2.liegeman(0) == knight)
    }
    "return the position of a present liegeman" in {
      assert(tile2.liegeman(1) == north)
    }
    "return a tile with suitably shifted borders and structures (90° clockwise)" in {
      val tileRotated = tile.rotate
      assert(tileRotated.borderType(northern) == pasture)
      assert(tileRotated.borderType(eastern) == road)
      assert(tileRotated.borderType(southern) == town)
      assert(tileRotated.borderType(western) == road)
      val tile2Rotated = tile2.rotate
      assert(tile2Rotated.borderType(northern) == town)
      assert(tile2Rotated.borderType(eastern) == town)
      assert(tile2Rotated.borderType(southern) == pasture)
      assert(tile2Rotated.borderType(western) == pasture)
      val tileRotatedTwice = tileRotated.rotate
      assert(tileRotatedTwice.borderType(northern) == road)
      assert(tileRotatedTwice.borderType(eastern) == pasture)
      assert(tileRotatedTwice.borderType(southern) == road)
      assert(tileRotatedTwice.borderType(western) == town)
    }
    "check if two tiles are equal" in {
      assert(tile.equals(tile))
      assert(!tile.equals(tile2))
    }
    "return false if you compare a tile to something else" in {
      val that = "empty"
      assert(!tile.equals(that))
    }
  }
}

