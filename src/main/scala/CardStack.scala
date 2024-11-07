import Orientation._
import BorderType._
import LiegemanType._
import LiegemanPosition._
import scala.collection.immutable.Queue

class CardStack(val starting_card: Card = new Card(borders = Vector(road, town, road, pasture))) {

  // Create all Carcassonne Cards A-X
  // Referenz: https://brettspiele-report.de/images/carcassonne/Spielanleitung_Carcassonne.pdf
  private val Card_A = new Card(monastery = true, borders = Vector(pasture, pasture, road, pasture))
  private val Card_B = new Card(monastery = true, borders = Vector(pasture, pasture, pasture, pasture))
  private val Card_C = new Card(townConnection = true, borders = Vector(town, town, town, town), coat_of_arms = true)
  // 1x starting_card, 3 in deck
  private val Card_D = new Card(borders = Vector(road, town, road, pasture))
  private val Card_E = new Card(borders = Vector(town, pasture, pasture, pasture))
  private val Card_F = new Card(townConnection = true, borders = Vector(pasture, town, pasture, town), coat_of_arms = true)
  private val Card_G = new Card(townConnection = true, borders = Vector(town, pasture, town, pasture))
  private val Card_H = new Card(borders = Vector(pasture, town, pasture, town))
  private val Card_I = new Card(borders = Vector(pasture, town, town, pasture))
  private val Card_J = new Card(borders = Vector(town, road, road, pasture))
  private val Card_K = new Card(borders = Vector(road, town, pasture, road))
  private val Card_L = new Card(borders = Vector(road, town, road, road))
  private val Card_M = new Card(townConnection = true, borders = Vector(town, pasture, pasture, town), coat_of_arms = true)
  private val Card_N = new Card(townConnection = true, borders = Vector(town, pasture, pasture, town))
  private val Card_O = new Card(townConnection = true, borders = Vector(town, road, road, town), coat_of_arms = true)
  private val Card_P = new Card(townConnection = true, borders = Vector(town, road, road, town))
  private val Card_Q = new Card(townConnection = true, borders = Vector(town, town, pasture, town), coat_of_arms = true)
  private val Card_R = new Card(townConnection = true, borders = Vector(town, town, pasture, town))
  private val Card_S = new Card(townConnection = true, borders = Vector(town, town, road, town), coat_of_arms = true)
  private val Card_T = new Card(townConnection = true, borders = Vector(town, town, road, town))
  private val Card_U = new Card(borders = Vector(road, pasture, road, pasture))
  private val Card_V = new Card(borders = Vector(pasture, pasture, road, road))
  private val Card_W = new Card(borders = Vector(pasture, road, road, road))
  private val Card_X = new Card(borders = Vector(road, road, road, road))

  // Return deck of all cards as queue
  def construct() : Queue[Card] = {
    val Deck = Queue.empty.enqueueAll(Seq.fill(2)(Card_A) ++ Seq.fill(4)(Card_B) ++ Seq.fill(1)(Card_C) ++
      Seq.fill(3)(Card_D) ++ Seq.fill(5)(Card_E) ++ Seq.fill(2)(Card_F) ++ Seq.fill(1)(Card_G) ++ Seq.fill(3)(Card_H) ++
      Seq.fill(2)(Card_I) ++ Seq.fill(3)(Card_J) ++ Seq.fill(3)(Card_K) ++ Seq.fill(3)(Card_L) ++ Seq.fill(2)(Card_M) ++
      Seq.fill(3)(Card_N) ++ Seq.fill(2)(Card_O) ++ Seq.fill(3)(Card_P) ++ Seq.fill(1)(Card_Q) ++ Seq.fill(3)(Card_R) ++
      Seq.fill(2)(Card_S) ++ Seq.fill(1)(Card_T) ++ Seq.fill(8)(Card_U) ++ Seq.fill(9)(Card_V) ++ Seq.fill(4)(Card_W) ++
      Seq.fill(1)(Card_X))
    Deck
  }
}
