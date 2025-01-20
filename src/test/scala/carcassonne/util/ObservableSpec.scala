package carcassonne.util

import org.scalatest.wordspec.AnyWordSpec

class ObservableSpec extends AnyWordSpec {
  class TestObserver() extends Observer {
    var updated = false

    def isUpdated: Boolean = updated

    override def update(): Unit = updated = true
  }

  "An Observable" when {
    "created" should {
      "notify all added observers" in {
        val observable = new Observable
        val observer1 = new TestObserver
        val observer2 = new TestObserver

        observable.add(observer1)
        observable.add(observer2)

        observable.notifyObservers()

        assert(observer1.isUpdated)
        assert(observer2.isUpdated)
      }
      "not notify removed observers" in {
        val observable = new Observable
        val observer1 = new TestObserver

        observable.add(observer1)
        observable.remove(observer1)

        observable.notifyObservers()

        assert(!observer1.isUpdated)
      }
    }
  }

}
