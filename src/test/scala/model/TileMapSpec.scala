package model

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import scala.collection.immutable.SortedMap

class TileMapSpec extends AnyWordSpec {
  private val map = TileMap()
  "A tile map" should {
    "be able to construct a map that maps all indexes to empty values" in {
      assert(map.data.isInstanceOf[SortedMap[(Index, Index), Option[Tile]]])
      assert(map.data(Index(0), Index(0)).isEmpty)
    }
    "be able to return a string representation of itself" in {
      val output = map.toString
      assert(output.isInstanceOf[String])
    }
    "be able to return a string representation of itself containing cards" in {
      val startingTile = TileStack().startingTile
      val emptyMap = TileMap()
      val mapWithCard = TileMap(emptyMap.data + ((Index(0), Index(0)) -> Some(startingTile)))
      val expectedOutput = startingTile.line(0)
      assert(mapWithCard.toString.contains(expectedOutput))
    }
    "provide an unchanging reference to its current data" in {
      var map = TileMap()
      val clone = map.deepClone()
      map = TileMap(map.data + ((Index(1), Index(1)) -> Some(TileStack().startingTile)))
      assert(map != clone)
    }
    "let a tile be added to the tile map" in {
      val tile = TileStack().startingTile
      val map = TileMap().add(Index(7), Index(7), Some(tile))
      val tileFromMap = map.data((Index(7), Index(7))).get
      assert(tileFromMap.equals(tile))
    }
  }

  "An index" should {
    val idx = Index(0)
    "have reflexive equals" in {
      assert(idx.equals(idx))
    }
    "have symmetric equals" in {
      val idx1 = Index(0)
      assert(idx.equals(idx1) && idx1.equals(idx))
    }
    "have transitive equals" in {
      val idx1 = Index(0)
      val idx2 = Index(0)
      assert(idx.equals(idx1) && idx1.equals(idx2) && idx.equals(idx2))
    }
    "return false if you compare an index to null" in {
      assert(!idx.equals(null))
    }
    "return false if you compare an index to something else" in {
      val that = "empty"
      assert(!idx.equals(that))
    }
    "return false if you compare two different indices" in {
      assert(!idx.equals(Index(1)))
    }
    "return the same hashcode for equal indices" in {
      assert(idx.hashCode() == Index(0).hashCode())
    }
  }
}

// TODO Check String Output if possible, but big string is hard to test

//    "construct 15*15 tabletop grid from an empty map" in {
//      val expectedOutput = (
//        "\n\n   0 0       0 1       0 2       0 3       0 4       0 5       0 6       0 7       0 8       0 9       0 a       0 b       0 c       0 d       0 e    \n\n\n" +
//          "\n\n   1 0       1 1       1 2       1 3       1 4       1 5       1 6       1 7       1 8       1 9       1 a       1 b       1 c       1 d       1 e    \n\n\n" +
//          "\n\n   2 0       2 1       2 2       2 3       2 4       2 5       2 6       2 7       2 8       2 9       2 a       2 b       2 c       2 d       2 e    \n\n\n" +
//          "\n\n   3 0       3 1       3 2       3 3       3 4       3 5       3 6       3 7       3 8       3 9       3 a       3 b       3 c       3 d       3 e    \n\n\n" +
//          "\n\n   4 0       4 1       4 2       4 3       4 4       4 5       4 6       4 7       4 8       4 9       4 a       4 b       4 c       4 d       4 e    \n\n\n" +
//          "\n\n   5 0       5 1       5 2       5 3       5 4       5 5       5 6       5 7       5 8       5 9       5 a       5 b       5 c       5 d       5 e    \n\n\n" +
//          "\n\n   6 0       6 1       6 2       6 3       6 4       6 5       6 6       6 7       6 8       6 9       6 a       6 b       6 c       6 d       6 e    \n\n\n" +
//          "\n\n   7 0       7 1       7 2       7 3       7 4       7 5       7 6       7 7       7 8       7 9       7 a       7 b       7 c       7 d       7 e    \n\n\n" +
//          "\n\n   8 0       8 1       8 2       8 3       8 4       8 5       8 6       8 7       8 8       8 9       8 a       8 b       8 c       8 d       8 e    \n\n\n" +
//          "\n\n   9 0       9 1       9 2       9 3       9 4       9 5       9 6       9 7       9 8       9 9       9 a       9 b       9 c       9 d       9 e    \n\n\n" +
//          "\n\n   a 0       a 1       a 2       a 3       a 4       a 5       a 6       a 7       a 8       a 9       a a       a b       a c       a d       a e    \n\n\n" +
//          "\n\n   b 0       b 1       b 2       b 3       b 4       b 5       b 6       b 7       b 8       b 9       b a       b b       b c       b d       b e    \n\n\n" +
//          "\n\n   c 0       c 1       c 2       c 3       c 4       c 5       c 6       c 7       c 8       c 9       c a       c b       c c       c d       c e    \n\n\n" +
//          "\n\n   d 0       d 1       d 2       d 3       d 4       d 5       d 6       d 7       d 8       d 9       d a       d b       d c       d d       d e    \n\n\n" +
//          "\n\n   e 0       e 1       e 2       e 3       e 4       e 5       e 6       e 7       e 8       e 9       e a       e b       e c       e d       e e    \n\n\n"
//        )
//      assert (tabletop.constructTabletopFromMap(emptyMap) == expectedOutput)
//    }