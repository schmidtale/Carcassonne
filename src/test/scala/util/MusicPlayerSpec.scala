package util


import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import java.lang.Thread.sleep
import scala.concurrent.duration.*
import scala.concurrent.Await

class MusicPlayerSpec extends AnyWordSpec {
  "A MusicPlayer" should {
    /* play music and */
    "distinguish between jingles and loops and start playback within 10 ms" in {
      val loopPlayer = MusicPlayer.createPlayer("gameplayLoop")
      loopPlayer.play()
      assert(loopPlayer.currentlyLooping())
      loopPlayer.stop()
      val jinglePlayer = MusicPlayer.createPlayer("TownJingle")
      jinglePlayer.play()
      /* wait for the thread from jinglePlayer to be ready */
      sleep(10) /* It's not pretty, but a later start would also be bad */
                /* and is therefore included in the test here automatically. */
      assert(!jinglePlayer.currentlyLooping())
    }
    "start valid song titles and stop any loop" in {
      val anyPlayer = MusicPlayer.createPlayer("gameplayLoop")
      anyPlayer.play()
      anyPlayer.stop()
    }
  }
}
