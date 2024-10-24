enum Orientation:
  case northern, eastern, southern, western

enum BorderType:
  case road, pasture, town

class Card(val monastery: Boolean = false, val borders: Array[BorderType]) {

  def borderType(o: Orientation): BorderType = {
    o match
      case Orientation.northern => borders(0)
      case Orientation.eastern => borders(1)
      case Orientation.southern => borders(2)
      case Orientation.western => borders(3)
  }


}
