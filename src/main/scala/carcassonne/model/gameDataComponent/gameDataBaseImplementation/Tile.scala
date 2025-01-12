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

import scala.xml.Elem

class Tile(val name: String = "default name", val monastery: Boolean = false, val townConnection: Boolean = false, val borders: Vector[BorderType] = Vector.empty,
           val liegeman: (LiegemanType, LiegemanPosition) = (none, nowhere), val coatOfArms: Boolean = false, val rotation: Int = 0) extends TileTrait
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
      coatOfArms = this.coatOfArms, this.rotation + 1
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
        this.coatOfArms == that.coatOfArms
      case _ => false
    }
  }

  override def hashCode(): Int = (monastery, townConnection, borders, liegeman, coatOfArms).##

  override def toString: String = {
    provider.toText(this)
  }

  def line(l: Int & 0 | 1 | 2 | 3 | 4): String = {
    provider.toText(this).split("\n")(l)
  }

  def toXML: Elem = {
    <tile>
      <name>{name}</name>
      <monastery>{monastery}</monastery>
      <townConnection>{townConnection}</townConnection>
      <borders>{borders.map(border => <border>{border}</border>)}</borders>
      <liegeman>
        <type>{liegeman._1}</type>
        <position>{liegeman._2}</position>
      </liegeman>
      <coatOfArms>{coatOfArms}</coatOfArms>
      <rotation>{rotation}</rotation>
    </tile>
  }

  def fromXML(node: scala.xml.Node) : Tile = {
    val name = (node \ "name").text
    val monastery = (node \ "monastery").text.toBoolean
    val townConnection = (node \ "townConnection").text.toBoolean
    val borders = (node \ "borders" \ "border").map(border => BorderType.valueOf(border.text)).toVector
    val liegemanType = LiegemanType.valueOf((node \ "liegeman" \ "type").text)
    val liegemanPosition = LiegemanPosition.valueOf((node \ "liegeman" \ "position").text)
    val liegeman = (liegemanType, liegemanPosition)
    val coatOfArms = (node \ "coatOfArms").text.toBoolean
    val rotation = (node \ "rotation").text.toInt

    new Tile (
      name,
      monastery,
      townConnection,
      borders,
      liegeman,
      coatOfArms,
      rotation
    )
  }
}
