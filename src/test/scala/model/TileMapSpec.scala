package model

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import scala.collection.immutable.SortedMap

class TileMapSpec extends AnyWordSpec{
  private val map = TileMap()
  "A tile map" should {
    "be able to construct a map that maps all indexes to empty values" in {
      assert(map.data.isInstanceOf[SortedMap[(Index, Index), Option[Card]]])
      assert(map.data(Index(0), Index(0)).isEmpty)
    }
  }
}