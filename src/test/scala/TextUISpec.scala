import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import Orientation.*
import BorderType.*
import LiegemanType.*
import LiegemanPosition.*
import scala.collection.immutable.SortedMap
import java.io.{ByteArrayInputStream, InputStream}

class TextUISpec extends AnyWordSpec {
  val card2 = new Card(false, true, borders = Vector(town, pasture, pasture, town), (knight, north))
  val tabletop = new Tabletop
  val oldMap: SortedMap[(Index, Index), Option[Card]] = tabletop.emptyMap()

  "The TextUI" should {
    "add a card to the selected place and return the new mapping" in {
      assert(updateMap(true, Index(0), Index(0), card2, tabletop, oldMap).
        equals(tabletop.addCardToMap(Index(0), Index(0), card2, tabletop.emptyMap())))
    }
//    "convert a user's command into placement information" in {
//      val originalIn: InputStream = System.in
//      val input1 = "0 5 14"
//      val input2 = "3 15 2" //not valid, index 15 does not exist
//      try {
//        System.setIn(new ByteArrayInputStream(input1.getBytes))
//        assert(readPlacement == (true, 0, Index(5), Index(14)))
//
//        System.setIn(new ByteArrayInputStream(input2.getBytes))
//        assert(readPlacement == (false, 0, Index(0), Index(0)))
//      } finally {
//        System.setIn(originalIn)
//      }
//    }
  }
}
