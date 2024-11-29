package util

import javazoom.jl.player.Player

import java.io.{BufferedInputStream, FileInputStream}
import scala.annotation.tailrec

class MusicPlayer(resourcePath: String, shouldLoop: Boolean) {
  @volatile private var isLooping = true

  def play(): Unit = {
    new Thread(() => {
      try {
        while (isLooping) {
          if (!shouldLoop) {
            isLooping = false;
          }
          val inputStream = getClass.getResourceAsStream(resourcePath)
          if (inputStream == null) {
            println(s"Error playing '$resourcePath' not found!")
            isLooping = false
          } else {
            val bufferedInputStream = BufferedInputStream(inputStream)
            val player = new Player(bufferedInputStream)
            player.play()
          }
        }
      } catch {
        case ex: Exception => println(s"Error playing file: ${ex.getMessage}")
      }
    }).start()
  }

  def stop(): Unit = {
    isLooping = false
  }
}

object MusicPlayer {
  def apply(kind: String): MusicPlayer = {
    kind match
      case "gameplayLoop" => new MusicPlayer("/loop0.mp3", true)
      case "TownJingle" => new MusicPlayer("/finished_town_jig.mp3", false)
  }
}
