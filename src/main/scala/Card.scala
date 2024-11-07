enum Orientation:
  case northern, eastern, southern, western

enum BorderType:
  case road, pasture, town

enum Field:
  case upperLeft, upperRight, lowerRight, lowerLeft

enum LiegemanType:
  case none, waylayer, monk, knight, peasant // TODO Tuple type + position

enum LiegemanPosition:
  case nowhere, north, east, south, west, middle, northWest, northEast, southEast, southWest

import Orientation._
import BorderType._
import LiegemanType._
import LiegemanPosition._
class Card(val monastery: Boolean = false, val townConnection: Boolean = false, val borders: Array[BorderType],
           val liegeman: LiegemanType = none, val position: LiegemanPosition = nowhere) {
  def borderType(o: Orientation): BorderType = {
    o match
      case Orientation.northern => borders(0)
      case Orientation.eastern => borders(1)
      case Orientation.southern => borders(2)
      case Orientation.western => borders(3) // TODO use or oder Tuple for border orientation
  }

  // TODO use Vector instead of Array
  def fieldConnections: Array[Boolean] = {
    val fc = Array(borderType(northern) != road,
      borderType(eastern) != road,
      borderType(southern) != road,
      borderType(western) != road)
    fc
  }

  def rotate: Card = {
    val newBorders = Array(this.borderType(western), this.borderType(northern),
      this.borderType(eastern),this.borderType(southern))
    val newCard = new Card(this.monastery, this.townConnection, newBorders
      /* other arguments must be on default if rotation is possible */ )
    newCard
  }

}
