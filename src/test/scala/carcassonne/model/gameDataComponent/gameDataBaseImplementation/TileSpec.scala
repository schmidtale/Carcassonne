package carcassonne.model.gameDataComponent.gameDataBaseImplementation

import carcassonne.model.gameDataComponent.gameDataBaseImplementation.BorderType.*
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.LiegemanPosition.*
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.LiegemanType.*
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.Orientation.*

import scala.xml.Elem

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.*

class TileSpec extends AnyWordSpec {
  val tile = new Tile(borders = Vector(road, town, road, pasture))
  val tile2 = new Tile(monastery = false, townConnection = true, borders = Vector(town, pasture, pasture, town), (knight, north))
  val tile3 = new Tile(monastery = false, borders = Vector(road, road, road, pasture))

  val tileR1 = new Tile(monastery = false, townConnection = false, Vector(pasture, road, town, road))
  val tileOrig = new Tile(monastery = false, townConnection = false, Vector(town, road, road, road), (none, nowhere), true)
  val tileOrigR2 = new Tile(monastery = false, townConnection = false, Vector(road, road, town, road), (none, nowhere), true)
  val tileOrigR3 = new Tile(monastery = false, townConnection = false, Vector(road, road, road, town), (none, nowhere), true)

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
    "return a suitably rotated tile with shifted borders and structures" in {
      assert(tile.rotate(0).equals(tile))
      assert(tile.rotate(4).equals(tile))
      assert(tile.rotate(1).equals(tileR1))
      assert(tileOrig.rotate(2).equals(tileOrigR2))
      assert(tileOrig.rotate(3).equals(tileOrigR3))
    }
    "not be required to rotate Liegemen, as this is not possible in game flow" in {
      true
    }
    "have reflexive equals" in {
      assert(tile.equals(tile))
    }
    "have symmetric equals" in {
      val t1 = new Tile(borders = Vector(road, town, road, pasture))
      assert(tile.equals(t1) && t1.equals(tile))
    }
    "have transitive equals" in {
      val t1 = new Tile(borders = Vector(road, town, road, pasture))
      val t2 = new Tile(borders = Vector(road, town, road, pasture))
      assert(tile.equals(t1) && t1.equals(t2) && tile.equals(t2))
    }
    "return false if you compare a tile to null" in {
      assert(!tile.equals(null))
    }
    "return false if you compare a tile to something else" in {
      val that = "empty"
      assert(!tile.equals(that))
    }
    "return false if you compare two different tiles" in {
      assert(!tile.equals(tile2))
    }
    "return the same hashcode for equal tiles" in {
      val t1 = new Tile(borders = Vector(road, town, road, pasture))
      assert(tile.hashCode() == t1.hashCode())
    }
    "return the String it receives internally from TextProvider" in {
      val textProvider = new TextProvider
      assert(tile.toString.equals(textProvider.toText(tile)))
    }
    "return the specified line within the textual representation of a tile" in {
      assert(tile.line(0).equals("* . H . *"))
    }
    "be convertible to XML" in {
      val tile = new Tile(name = "XML Tile", monastery = true, townConnection = false,
        borders = Vector(pasture, road, town, road), liegeman = (monk, middle), coatOfArms = false, rotation = 0)
      // Create an instance of PrettyPrinter with the desired settings
      val xmlOutput = tile.toXML
      val expectedXml: Elem =
        <tile>
          <name>XML Tile</name>
          <monastery>true</monastery>
          <townConnection>false</townConnection>
          <borders>
            <border>pasture</border>
            <border>road</border>
            <border>town</border>
            <border>road</border>
          </borders>
          <liegeman>
            <type>monk</type>
            <position>middle</position>
          </liegeman>
          <coatOfArms>false</coatOfArms>
          <rotation>0</rotation>
        </tile>
      val normalizedXmlOutput = xmlOutput.toString.replaceAll("\\s+", "")
      val normalizedExpectedXml = expectedXml.toString.replaceAll("\\s+", "")
      assert(normalizedXmlOutput.equals(normalizedExpectedXml))
    }
    "be constructable from XML" in {
      val tile = new Tile(name = "XML Tile", monastery = true, townConnection = false,
        borders = Vector(pasture, road, town, road), liegeman = (monk, middle), coatOfArms = false, rotation = 0)
      val XML = tile.toXML
      print(XML)
      val XMLTile = tile.fromXML(XML)
      assert(tile.equals(XMLTile))
    }
    "be convertible to JSON" in {
      val tile = new Tile(name = "XML Tile", monastery = true, townConnection = false,
        borders = Vector(pasture, road, town, road), liegeman = (monk, middle), coatOfArms = false, rotation = 0)
      val jsonOutput = Json.toJson(tile)
      val expectedJson = Json.obj(
        "name" -> "XML Tile",
        "monastery" -> true,
        "townConnection" -> false,
        "borders" -> Json.arr("pasture", "road", "town", "road"),
        "liegeman" -> Json.obj(
          "type" -> "monk",
          "position" -> "middle"
        ),
        "coatOfArms" -> false,
        "rotation" -> 0
      )
      assert(jsonOutput.equals(expectedJson))
    }
    "be constructable from JSON" in {
      val expectedTile = new Tile(name = "JSON Tile", monastery = true, townConnection = false,
        borders = Vector(pasture, road, town, road), liegeman = (monk, middle), coatOfArms = false, rotation = 0)

      val json = Json.obj(
        "name" -> "JSON Tile",
        "monastery" -> true,
        "townConnection" -> false,
        "borders" -> Json.arr("pasture", "road", "town", "road"),
        "liegeman" -> Json.obj(
          "type" -> "monk",
          "position" -> "middle"
        ),
        "coatOfArms" -> false,
        "rotation" -> 0
      )
      val tileFromJson = json.as[Tile]
      assert(tileFromJson.equals(expectedTile))
    }
  }
}

