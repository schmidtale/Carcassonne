package model
import BorderType._

import scala.collection.immutable.Queue

class TileStack(val startingTile: Tile = new Tile(borders = Vector(road, town, road, pasture))) {

  // Create all Carcassonne Cards A-X
  // Referenz: https://brettspiele-report.de/images/carcassonne/Spielanleitung_Carcassonne.pdf
  private val Tile_A = new Tile(monastery = true, borders = Vector(pasture, pasture, road, pasture))
  private val Tile_B = new Tile(monastery = true, borders = Vector(pasture, pasture, pasture, pasture))
  private val Tile_C = new Tile(townConnection = true, borders = Vector(town, town, town, town), coat_of_arms = true)
  // 1x startingTile, 3 in deck
  private val Tile_D = new Tile(borders = Vector(road, town, road, pasture))
  private val Tile_E = new Tile(borders = Vector(town, pasture, pasture, pasture))
  private val Tile_F = new Tile(townConnection = true, borders = Vector(pasture, town, pasture, town), coat_of_arms = true)
  private val Tile_G = new Tile(townConnection = true, borders = Vector(town, pasture, town, pasture))
  private val Tile_H = new Tile(borders = Vector(pasture, town, pasture, town))
  private val Tile_I = new Tile(borders = Vector(pasture, town, town, pasture))
  private val Tile_J = new Tile(borders = Vector(town, road, road, pasture))
  private val Tile_K = new Tile(borders = Vector(road, town, pasture, road))
  private val Tile_L = new Tile(borders = Vector(road, town, road, road))
  private val Tile_M = new Tile(townConnection = true, borders = Vector(town, pasture, pasture, town), coat_of_arms = true)
  private val Tile_N = new Tile(townConnection = true, borders = Vector(town, pasture, pasture, town))
  private val Tile_O = new Tile(townConnection = true, borders = Vector(town, road, road, town), coat_of_arms = true)
  private val Tile_P = new Tile(townConnection = true, borders = Vector(town, road, road, town))
  private val Tile_Q = new Tile(townConnection = true, borders = Vector(town, town, pasture, town), coat_of_arms = true)
  private val Tile_R = new Tile(townConnection = true, borders = Vector(town, town, pasture, town))
  private val Tile_S = new Tile(townConnection = true, borders = Vector(town, town, road, town), coat_of_arms = true)
  private val Tile_T = new Tile(townConnection = true, borders = Vector(town, town, road, town))
  private val Tile_U = new Tile(borders = Vector(road, pasture, road, pasture))
  private val Tile_V = new Tile(borders = Vector(pasture, pasture, road, road))
  private val Tile_W = new Tile(borders = Vector(pasture, road, road, road))
  private val Tile_X = new Tile(borders = Vector(road, road, road, road))

  // Return deck of all cards as queue
  def construct() : Queue[Tile] = {
    val Deck = Queue.empty.enqueueAll(Seq.fill(2)(Tile_A) ++ Seq.fill(4)(Tile_B) ++ Seq.fill(1)(Tile_C) ++
      Seq.fill(3)(Tile_D) ++ Seq.fill(5)(Tile_E) ++ Seq.fill(2)(Tile_F) ++ Seq.fill(1)(Tile_G) ++ Seq.fill(3)(Tile_H) ++
      Seq.fill(2)(Tile_I) ++ Seq.fill(3)(Tile_J) ++ Seq.fill(3)(Tile_K) ++ Seq.fill(3)(Tile_L) ++ Seq.fill(2)(Tile_M) ++
      Seq.fill(3)(Tile_N) ++ Seq.fill(2)(Tile_O) ++ Seq.fill(3)(Tile_P) ++ Seq.fill(1)(Tile_Q) ++ Seq.fill(3)(Tile_R) ++
      Seq.fill(2)(Tile_S) ++ Seq.fill(1)(Tile_T) ++ Seq.fill(8)(Tile_U) ++ Seq.fill(9)(Tile_V) ++ Seq.fill(4)(Tile_W) ++
      Seq.fill(1)(Tile_X))
    Deck
  }
}
