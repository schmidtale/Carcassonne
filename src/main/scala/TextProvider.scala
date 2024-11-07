import Orientation._
import BorderType._
import LiegemanType._
import LiegemanPosition._


class TextProvider {
  private val prototype = "* a A a *|d e i f b|D l M j B|d h k g b|* c C c *"

  def toText(c: Card): String = {
    val cardString = prototype.map(char => replaceChar(char, c))
    println(cardString) // Outputs the modified string
    cardString
  }

  def replaceChar(char: Char, c: Card): Char = {
    (char, c.borders(0), c.borders(1), c.borders(2), c.borders(3), c.townConnection, c.monastery) match {
      //case ('|', _, _, _) => '\n'
      case ('a', town, _, _, _, _, _) => 'B'
      case ('A', town, _, _, _, _, _) => 'B'
      case ()
      case other => other // Default to original character if no match
    }
  }





  def line(c: Card, l: Int & 0 | 1 | 2 | 3 | 4): String = {
    "x x x x x"
  }


}



// following are a lot of bad attempts at the line method
// I cant yet bring myself to delete it all
/*
private def threeToTwo(i: Int): Int = {
  if (i == 3) return 2
  i
}

private def row(i: Int, l: Int): String = {
  "."
}

private def spot(i: Int, l: Int): String = {
  " "
}
def line(c: Card, l: Int & 0 | 1 | 2 | 3 | 4): String = {


  row(0, l) + spot(0, l) + row(1, l) + spot(1, l) + row(2, l) + spot(2, l) + row(3, l) + spot(3, l) + row(4, l)
}
*/

/*i match
  case 0 => c.borders(0) match
    case BorderType.town =>     "* B B B *"
    case BorderType.pasture =>  "* . . . *"
    case BorderType.road =>     "* . H . *"
  case 4 => c.borders(2) match
    case BorderType.town =>     "* B B B *"
    case BorderType.pasture =>  "* . . . *"
    case BorderType.road =>     "* . H . *"
  case 1 => (c.borders(3),c.borders(1),c.borders(0),c.townConnection) match
    case (BorderType.town, BorderType.town, BorderType.town, true)     =>   "B B B B B"
    case (BorderType.town, BorderType.town, BorderType.pasture, true)  =>   "B B B B B"
    case (BorderType.town, BorderType.town, BorderType.pasture, false) =>   "B . . . B"
    case (BorderType.town, BorderType.pasture, BorderType.pasture, _ ) =>   "B . . . ."
    case (BorderType.town, BorderType.pasture, BorderType.road, _ )    =>   "B . H . ."
    case (BorderType.town, BorderType.road, BorderType.pasture, _ )    =>   "B . . . ."
    case (BorderType.pasture, BorderType.town, BorderType.pasture, _ ) =>   ". . . . B"
    case (BorderType.pasture, BorderType.road, BorderType.pasture, _)  =>   ". . H . ."
    case (BorderType.pasture, BorderType.pasture, BorderType.pasture, _) => ". . . . ."
    case (BorderType.road, BorderType.town, BorderType.pasture, _) =>       ". . . . B"
    case (BorderType.road, BorderType.town, BorderType.pasture, _)       => ". . . . B"

 */
/*
if(i == 0 | i == 3) {
  val A: String = "*"
  val B: String = c.borders(threeToTwo(i)) match
    case BorderType.town    => "B"
    case BorderType.pasture => "."
    case BorderType.road    => "."

  val C: String = c.borders(threeToTwo(i)) match
    case BorderType.town    => "B"
    case BorderType.pasture => "."
    case BorderType.road    => "H"

  val D: String = c.borders(threeToTwo(i)) match
    case BorderType.town    => "B"
    case BorderType.pasture => "."
    case BorderType.road    => "."

  val E: String = "*"
  return A + B + C + D + E
}
if(i == 1) {
  if(c.borders(0) == town) {
    if(c.borders(1) == town) {
      if(c.townConnection) {
        A = ""
      }
    }
  }
}
*/
