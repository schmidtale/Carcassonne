package carcassonne.model.gameDataComponent.gameDataBaseImplementation

enum Orientation:
  case northern, eastern, southern, western

enum BorderType:
  case road, pasture, town

enum Field:
  case upperLeft, upperRight, lowerRight, lowerLeft

enum LiegemanType:
  case none, waylayer, monk, knight, peasant

enum LiegemanPosition:
  case nowhere, north, east, south, west, middle, northWest, northEast, southEast, southWest

import carcassonne.model.gameDataComponent.gameDataBaseImplementation.BorderType.*
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.LiegemanPosition.*
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.LiegemanType.*
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.Orientation.*
import carcassonne.model.gameDataComponent.*

class Tile(val name: String = "default name", val monastery: Boolean = false, val townConnection: Boolean = false, val borders: Vector[BorderType] = Vector.empty,
           val liegeman: (LiegemanType, LiegemanPosition) = (none, nowhere), val coat_of_arms: Boolean = false, val rotation: Int = 0) extends TileTrait
{
  private val provider = new TextProvider()
  def borderType(o: Orientation): BorderType = {
    o match
      case Orientation.northern => borders(0)
      case Orientation.eastern => borders(1)
      case Orientation.southern => borders(2)
      case Orientation.western => borders(3)
  }

  def fieldConnections: Vector[Boolean] = {
    val fc = Vector(borderType(northern) != road,
      borderType(eastern) != road,
      borderType(southern) != road,
      borderType(western) != road)
      fc
  }

  def rotate: Tile = {
    val newBorders = Vector(this.borderType(western), this.borderType(northern),
      this.borderType(eastern),this.borderType(southern))
    val newCard = new Tile(this.name, this.monastery, this.townConnection, newBorders,
      coat_of_arms = this.coat_of_arms, this.rotation + 1
      /* other arguments must be on default if rotation is possible */ )
    newCard
  }
  def rotate(r: Int): Tile = {
    val n = r % 4
    n match
      case 0 => this
      case 1 => this.rotate
      case 2 => this.rotate.rotate
      case 3 => this.rotate.rotate.rotate
  }

  // Rotated cards are different cards
  override def equals(obj: Any): Boolean = {
    obj match {
      case that: Tile =>
        /* No comparison of names */
        this.monastery == that.monastery &&
        this.townConnection == that.townConnection &&
        this.borders == that.borders &&
        this.liegeman == that.liegeman &&
        this.coat_of_arms == that.coat_of_arms
      case _ => false
    }
  }

  override def hashCode(): Int = (monastery, townConnection, borders, liegeman, coat_of_arms).##

  override def toString: String = {
    provider.toText(this)
  }

  def line(l: Int & 0 | 1 | 2 | 3 | 4): String = {
    provider.toText(this).split("\n")(l)
  }

}