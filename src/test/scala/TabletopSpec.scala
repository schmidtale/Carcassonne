
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class TabletopSpec extends AnyWordSpec {
  val tabletop = new Tabletop
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
  }
}