class Tabletop {
  def constructTabletop(): String = {
    val strBuilder = new StringBuilder()
    for (i <- 0 to 3) {
      strBuilder.append("\n" * 2)
      for (j <- 0 to 3) {
        strBuilder.append(" " * 3 + i + " " + j + " " * 4)
      }
      strBuilder.append("\n" * 3)
    }
    strBuilder.toString()
  }
}
