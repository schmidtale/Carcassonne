package model.gameDataComponent.gameDataBaseImplementation

import model.gameDataComponent.gameDataBaseImplementation.BorderType.*

import scala.collection.immutable.Queue

class TileStack(val startingTile: Tile = new Tile(name = "D", borders = Vector(road, town, road, pasture)).rotate(3),
               val defaultTile: Tile = new Tile(name = "", borders = Vector(pasture, pasture, pasture, pasture))) {
  // Create all Carcassonne Cards A-X
  // Reference: https://brettspiele-report.de/images/carcassonne/Spielanleitung_Carcassonne.pdf
  private val Tile_A = new Tile(name = "A", monastery = true, borders = Vector(pasture, pasture, road, pasture))
  private val Tile_B = new Tile(name = "B", monastery = true, borders = Vector(pasture, pasture, pasture, pasture))
  private val Tile_C = new Tile(name = "C", townConnection = true, borders = Vector(town, town, town, town), coat_of_arms = true)
  // 1x startingTile, 3 in deck
  private val Tile_D = new Tile(name = "D", borders = Vector(road, town, road, pasture))
  private val Tile_E = new Tile(name = "E", borders = Vector(town, pasture, pasture, pasture))
  private val Tile_F = new Tile(name = "F", townConnection = true, borders = Vector(pasture, town, pasture, town), coat_of_arms = true)
  private val Tile_G = new Tile(name = "G", townConnection = true, borders = Vector(town, pasture, town, pasture))
  private val Tile_H = new Tile(name = "H", borders = Vector(pasture, town, pasture, town))
  private val Tile_I = new Tile(name = "I", borders = Vector(pasture, town, town, pasture))
  private val Tile_J = new Tile(name = "J", borders = Vector(town, road, road, pasture))
  private val Tile_K = new Tile(name = "K", borders = Vector(road, town, pasture, road))
  private val Tile_L = new Tile(name = "L", borders = Vector(road, town, road, road))
  private val Tile_M = new Tile(name = "M", townConnection = true, borders = Vector(town, pasture, pasture, town), coat_of_arms = true)
  private val Tile_N = new Tile(name = "N", townConnection = true, borders = Vector(town, pasture, pasture, town))
  private val Tile_O = new Tile(name = "O", townConnection = true, borders = Vector(town, road, road, town), coat_of_arms = true)
  private val Tile_P = new Tile(name = "P", townConnection = true, borders = Vector(town, road, road, town))
  private val Tile_Q = new Tile(name = "Q", townConnection = true, borders = Vector(town, town, pasture, town), coat_of_arms = true)
  private val Tile_R = new Tile(name = "R", townConnection = true, borders = Vector(town, town, pasture, town))
  private val Tile_S = new Tile(name = "S", townConnection = true, borders = Vector(town, town, road, town), coat_of_arms = true)
  private val Tile_T = new Tile(name = "T", townConnection = true, borders = Vector(town, town, road, town))
  private val Tile_U = new Tile(name = "U", borders = Vector(road, pasture, road, pasture))
  private val Tile_V = new Tile(name = "V", borders = Vector(pasture, pasture, road, road))
  private val Tile_W = new Tile(name = "W", borders = Vector(pasture, road, road, road))
  private val Tile_X = new Tile(name = "X", borders = Vector(road, road, road, road))

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
