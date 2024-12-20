package carcassonne.model.gameDataComponent.gameDataBaseImplementation

import carcassonne.model.gameDataComponent.{TextProviderTrait, TileTrait}
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.{BorderType, Tile}

class TextProvider extends TextProviderTrait {
  val prototype = "* a A a *|d e i f b|D l M j B|d h k g b|* c C c *"

  def line(c: Tile, l: Int & 0 | 1 | 2 | 3 | 4): String = {
    toText(c).split("\n")(l)
  }
  
  def toText(c: TileTrait): String = {
    val cardString = prototype.map(char => replaceChar(char, c.asInstanceOf[Tile]))
    cardString
  }

  def replaceChar(char: Char, c: Tile): Char = {
    (char, c.borders(0), c.borders(1), c.borders(2), c.borders(3), c.townConnection, c.monastery) match {

      case ('|', _, _, _, _, _, _) => '\n'
      case ('*', _, _, _, _, _, _) => '*'

      /* border tile cases and inner roads */
      case ('a', BorderType.town, _, _, _, _, _) => 'B'
      case ('a', BorderType.pasture, _, _, _, _, _) => '.'
      case ('a', BorderType.road, _, _, _, _, _) => '.'
      case ('A', BorderType.town, _, _, _, _, _) => 'B'
      case ('A', BorderType.pasture, _, _, _, _, _) => '.'
      case ('A'|'i', BorderType.road, _, _, _, _, _) => 'H'

      case ('b', _, BorderType.town, _, _, _, _) => 'B'
      case ('b', _, BorderType.pasture, _, _, _, _) => '.'
      case ('b', _, BorderType.road, _, _, _, _) => '.'
      case ('B', _, BorderType.town, _, _, _, _) => 'B'
      case ('B', _, BorderType.pasture, _, _, _, _) => '.'
      case ('B'|'j', _, BorderType.road, _, _, _, _) => 'H'

      case ('c', _, _, BorderType.town, _, _, _) => 'B'
      case ('c', _, _, BorderType.pasture, _, _, _) => '.'
      case ('c', _, _, BorderType.road, _, _, _) => '.'
      case ('C', _, _, BorderType.town, _, _, _) => 'B'
      case ('C', _, _, BorderType.pasture, _, _, _) => '.'
      case ('C'|'k', _, _, BorderType.road, _, _, _) => 'H'

      case ('d', _, _, _, BorderType.town, _, _) => 'B'
      case ('d', _, _, _, BorderType.pasture, _, _) => '.'
      case ('d', _, _, _, BorderType.road, _, _) => '.'
      case ('D', _, _, _, BorderType.town, _, _) => 'B'
      case ('D', _, _, _, BorderType.pasture, _, _) => '.'
      case ('D'|'l', _, _, _, BorderType.road, _, _) => 'H'

      case ('e'|'f'|'g'|'h'|'i'|'j'|'k'|'l'|'M',
        BorderType.town, BorderType.town, BorderType.town, BorderType.town, _, _) => 'B'

      /* three town inner cases */
      case ('i'|'f'|'j'|'g'|'k'|'M', BorderType.town, BorderType.town, BorderType.town, _, _, _) => 'B'
      case ('l'|'e'|'i'|'f'|'j'|'M', BorderType.town, BorderType.town, _, BorderType.town, _, _) => 'B'
      case ('k'|'h'|'l'|'e'|'i'|'M', BorderType.town, _, BorderType.town, BorderType.town, _, _) => 'B'
      case ('j'|'g'|'k'|'h'|'l'|'M', _, BorderType.town, BorderType.town, BorderType.town, _, _) => 'B'

      /* two town inner corner cases */
      case ('e', BorderType.town, _, _, BorderType.town, true, _) => 'B'
      case ('e', BorderType.town, _, _, BorderType.town, false, _) => '.'
      case ('f', BorderType.town, BorderType.town, _, _, true, _) => 'B'
      case ('f', BorderType.town, BorderType.town, _, _, false, _) => '.'
      case ('g', _, BorderType.town, BorderType.town, _, true, _) => 'B'
      case ('g', _, BorderType.town, BorderType.town, _, false, _) => '.'
      case ('h', _, _, BorderType.town, BorderType.town, true, _) => 'B'
      case ('h', _, _, BorderType.town, BorderType.town, false, _) => '.'
      
      /* monastery cases */
      case ('M', _, _, _, _, false, true) => 'C'
      case ('e'|'f'|'g'|'h'|'i'|'j'|'k'|'l', BorderType.pasture, BorderType.pasture,
        BorderType.pasture, BorderType.pasture, false, true) => '.'

      /* middle road cases */
      case ('M', BorderType.road, BorderType.road, _, _, _, _) => 'H'
      case ('M', _, BorderType.road, BorderType.road, _, _, _) => 'H'
      case ('M', _, _, BorderType.road, BorderType.road, _, _) => 'H'
      case ('M', BorderType.road, _, _, BorderType.road, _, _) => 'H'
      case ('M', _, BorderType.road, _, BorderType.road, _, _) => 'H'
      case ('M', BorderType.road, _, BorderType.road, _, _, _) => 'H'

      /* once all special cases are handled remaining inner letters can become grass */
      case ('e'|'f'|'g'|'h'|'i'|'j'|'k'|'l'|'M', _, _, _, _, _, _) => '.'

      /* print # to indicate missed cases */
      case (' ', _, _, _, _, _, _) => ' '

      /* print # to indicate missed cases */
      case (_, _, _, _, _, _, _) => '#'
    }
  }
}
