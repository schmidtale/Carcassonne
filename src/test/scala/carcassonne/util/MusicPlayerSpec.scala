package carcassonne.util

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class MusicPlayerSpec extends AnyWordSpec {
  "A MusicPlayer" should {
    /* play music and */
    "distinguish between jingles and loops" in {
      val loopPlayer = MusicPlayer("gameplayLoop")
      loopPlayer.play()
      assert(loopPlayer.currentlyLooping())
      loopPlayer.stop()
      val jinglePlayer = MusicPlayer("TownJingle")
      jinglePlayer.play()
      assert(!jinglePlayer.shouldLoop)
    }
    "start valid song titles and stop any loop" in {
      val anyPlayer = MusicPlayer("gameplayLoop")
      anyPlayer.play()
      anyPlayer.stop()
    }
    "print some exception info when missing the song file" in {
      val aPlayerWithNoValidSong = new MusicPlayer("non_existing_song", false)
      aPlayerWithNoValidSong.play()
    }
  }
}
