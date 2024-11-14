import _view.TextUI
import controller.Tabletop
import model.TileMap

import scala.util.Random
@main
def main(): Unit = {
    val tabletop = new Tabletop
//  print(tabletop.constructTabletop())
//
//  val emptyMap = tabletop.emptyMap()
//  print(tabletop.constructTabletopFromMap(emptyMap))
    val sortedStack = tabletop.tileStack()
    val shuffledStack = Random.shuffle(sortedStack)
    var tuiParameters: (TileMap, Int) = (tabletop.initialMap(), 0)
    while (true) {
        tuiParameters = TextUI.exec(tuiParameters, shuffledStack)
    }
}

  


