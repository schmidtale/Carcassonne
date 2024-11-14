//I'm trying to define a Type "Index" which only allows integer
// values of 0 to 15 (inclusive) for use in methods which test
//the legality of card placements.
//It seems to work well so far, not allowing out of range indices
//even while typing - even before compile time! I think that is
//better than the solution ChatGPT suggested to me, so I'm a little
//proud. It looks a litte funny though, maybe there is a way to
//condense it:

case class Index (value: Int & (0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15))



def isLegalPlacement(x: Index, y: Index): Boolean = {
  //if cardAt(x, y) is some => false (location occupied already!)
  //find at least one neighbour (otherwise the move is illegal)
  //look at the cardAt(i+-1 'and' j+-1)'s border compatibility

  //There are literal edge cases for indices 0 and 15
  //which could possibly be avoided if we created a 'shadow'
  //layer with shadowIndices -1 and 16 like 'Hilfskopfknoten'.
  //It might also be more work than dealing with the edge cases
  //and unnecessarily abstract. However, I imagine all functions
  //looking for neighbours might profit. Let's think about it
  //at least.
  false //the method does not work yet, but it returns a boolean at least.
}

val desiredRow: Index = Index(15)
val desiredColum: Index = Index(6)

val proceed = isLegalPlacement(desiredRow, desiredColum)

//The cardAt(x, y) method above hints at the thought of a data
//structure which maps an Optional[Tile] (value) to an index pair (key).
//Maybe a TreeMap (a sorted Map)?
//Then, cardAt(x, y) should return None or Some(Tile) from
//that data structure.
//The Tile itself contains the information about its borders etc.

