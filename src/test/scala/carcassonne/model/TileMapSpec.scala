package carcassonne.model

import carcassonne.model.gameDataComponent.gameDataBaseImplementation.{Index, Tile, TileMap, TileStack}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.Json

import scala.collection.immutable.SortedMap
import scala.xml.Elem

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
    "be convertible to XML" in {
      var tileMap = new TileMap()
      tileMap = tileMap.add(Index(0), Index(0), Some(TileStack().startingTile))
      tileMap = tileMap.add(Index(1), Index(0), Some(TileStack().startingTile))
      // Create an instance of PrettyPrinter with the desired settings
      val xmlOutput = tileMap.toXML
      val expectedXml: Elem =
        <tileMap>
          <entry>
            <position>
              <x>
                0
              </x>
              <y>
                0
              </y>
            </position> <tile>
            <name>
              D
            </name>
            <monastery>
              false
            </monastery>
            <townConnection>
              false
            </townConnection>
            <borders>
              <border>
                town
              </border> <border>
              road
            </border> <border>
              pasture
            </border> <border>
              road
            </border>
            </borders>
            <liegeman>
              <type>
                none
              </type>
              <position>
                nowhere
              </position>
            </liegeman>
            <coatOfArms>
              false
            </coatOfArms>
            <rotation>
              3
            </rotation>
          </tile>
          </entry>
          <entry>
            <position>
              <x>
                1
              </x>
              <y>
                0
              </y>
            </position> <tile>
            <name>
              D
            </name>
            <monastery>
              false
            </monastery>
            <townConnection>
              false
            </townConnection>
            <borders>
              <border>
                town
              </border> <border>
              road
            </border> <border>
              pasture
            </border> <border>
              road
            </border>
            </borders>
            <liegeman>
              <type>
                none
              </type>
              <position>
                nowhere
              </position>
            </liegeman>
            <coatOfArms>
              false
            </coatOfArms>
            <rotation>
              3
            </rotation>
          </tile>
          </entry>
        </tileMap>
      val normalizedXmlOutput = xmlOutput.toString.replaceAll("\\s+", "")
      val normalizedExpectedXml = expectedXml.toString.replaceAll("\\s+", "")
      assert(normalizedXmlOutput.equals(normalizedExpectedXml))
    }
    "be constructable from XML" in {
      var tileMap = new TileMap()
      tileMap = tileMap.add(Index(0), Index(0), Some(TileStack().startingTile))
      val XML = tileMap.toXML
      println(XML)
      val XMLMap = tileMap.fromXML(XML)
      assert(tileMap.data.equals(XMLMap.data))
    }
    "be convertible to JSON" in {
      var tileMap = new TileMap()
      tileMap = tileMap.add(Index(0), Index(0), Some(TileStack().startingTile))
      tileMap = tileMap.add(Index(1), Index(0), Some(TileStack().startingTile))

      val jsonOutput = Json.toJson(tileMap)
      val expectedJson = Json.obj(
        "tileMap" -> Json.obj(
          "entries" -> Json.arr(
            Json.obj(
              "position" -> Json.obj(
                "x" -> 0,
                "y" -> 0
              ),
              "tile" -> Json.obj(
                "name" -> "D",
                "monastery" -> false,
                "townConnection" -> false,
                "borders" -> Json.arr("town", "road", "pasture", "road"),
                "liegeman" -> Json.obj(
                  "type" -> "none",
                  "position" -> "nowhere"
                ),
                "coatOfArms" -> false,
                "rotation" -> 3
              )
            ),
            Json.obj(
              "position" -> Json.obj(
                "x" -> 1,
                "y" -> 0
              ),
              "tile" -> Json.obj(
                "name" -> "D",
                "monastery" -> false,
                "townConnection" -> false,
                "borders" -> Json.arr("town", "road", "pasture", "road"),
                "liegeman" -> Json.obj(
                  "type" -> "none",
                  "position" -> "nowhere"
                ),
                "coatOfArms" -> false,
                "rotation" -> 3
              )
            )
          )
        )
      )
      assert(jsonOutput.equals(expectedJson))
    }
    "be constructable from JSON" in {
      var expectedTileMap = new TileMap()
      expectedTileMap = expectedTileMap.add(Index(0), Index(0), Some(TileStack().startingTile))
      expectedTileMap = expectedTileMap.add(Index(1), Index(0), Some(TileStack().startingTile))

      val json = Json.obj(
        "tileMap" -> Json.obj(
          "entries" -> Json.arr(
            Json.obj(
              "position" -> Json.obj(
                "x" -> 0,
                "y" -> 0
              ),
              "tile" -> Json.obj(
                "name" -> "D",
                "monastery" -> false,
                "townConnection" -> false,
                "borders" -> Json.arr("town", "road", "pasture", "road"),
                "liegeman" -> Json.obj(
                  "type" -> "none",
                  "position" -> "nowhere"
                ),
                "coatOfArms" -> false,
                "rotation" -> 3
              )
            ),
            Json.obj(
              "position" -> Json.obj(
                "x" -> 1,
                "y" -> 0
              ),
              "tile" -> Json.obj(
                "name" -> "D",
                "monastery" -> false,
                "townConnection" -> false,
                "borders" -> Json.arr("town", "road", "pasture", "road"),
                "liegeman" -> Json.obj(
                  "type" -> "none",
                  "position" -> "nowhere"
                ),
                "coatOfArms" -> false,
                "rotation" -> 3
              )
            )
          )
        )
      )
      val tileMapFromJson = json.as[TileMap]
      assert(tileMapFromJson.data.equals(expectedTileMap.data))
    }
  }
}