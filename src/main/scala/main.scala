  @main
  def main(): Unit = {
    printTabletop()
  }

  def printTabletop(): Unit = {
    for (i <- 0 to 3) {
      printf("\n" * 2)
      for (j <- 0 to 3) {
        printf(" " * 3 + i + " " + j + " " * 4)
      }
      printf("\n" * 3)
    }
  }
  // als Funktion/Methode


