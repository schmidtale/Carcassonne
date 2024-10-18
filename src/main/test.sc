1 + 2
"n" * 3

def print(): Unit = {
  for (i <- 0 to 3) {
    printf("\n" * 2)
    for (j <- 0 to 3) {
      printf(" " * 3 + i + " " + j + " " * 4)
    }
    printf("\n" * 3)
  }
}

print()

def test1(): Unit = {
  printf("Test123\n")
}
