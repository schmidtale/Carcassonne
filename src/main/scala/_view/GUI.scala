package _view

import util.Observer
import controller.Tabletop
import model.{Color, Index, Tile}
import scalafx.application.{JFXApp3, Platform}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.stage.Screen
import scalafx.scene.effect.DropShadow
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{BorderPane, GridPane, HBox, Pane, StackPane, VBox}
import scalafx.scene.paint.Color.*
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.{Font, Text}

// Function to convert enum Color to scalafx Color
def getColorFromEnum(playerColor: model.Color): scalafx.scene.paint.Color = {
  playerColor match {
    case Color.blue => scalafx.scene.paint.Color.SkyBlue
    case Color.red => scalafx.scene.paint.Color.MediumVioletRed
    case Color.green => scalafx.scene.paint.Color.ForestGreen
    case Color.yellow => scalafx.scene.paint.Color.Yellow
    case Color.black => scalafx.scene.paint.Color.Gray
  }
}

class GUI(tabletop: Tabletop) extends JFXApp3 with Observer {
  tabletop.add(this)

  // Store tileImages 15x15 grid for tiles
  private val tileImages: Array[Array[ImageView]] = Array.ofDim[ImageView](15, 15)

  private var nextCardImageView: ImageView = _

  // current rotation of nextCardImageView tile
  var currentRotation = 0

  /* Viewport sizes */
  private var viewHeight: Double = 0
  private var viewWidth: Double = 0

  override def start(): Unit = {
    viewHeight = Screen.primary.visualBounds.height
    viewWidth = Screen.primary.visualBounds.width
    stage = new JFXApp3.PrimaryStage {
      title = "Carcasonne"
      width = viewWidth
      height = viewHeight
      resizable = false

      scene = new Scene {
        fill = Black
        content = new BorderPane {
          left = new VBox {
            // add imageView for next Card and Button for rotation on top
            // TODO add button for rotation of card
            val nextTileStackPane = new StackPane {
              val nextCardImage = new Image(getImagePath(tabletop.gameData.currentTile()))
              nextCardImageView = new ImageView(nextCardImage) {
                preserveRatio = true
                fitWidth = viewWidth / 4 // Adjust size as needed
              }
              val rotateButton = new Button {
                maxWidth = Double.MaxValue
                maxHeight = Double.MaxValue
                style = "-fx-background-color: transparent; " +
                  "-fx-border-color: transparent; " +
                  "-fx-text-fill: #000000;"
                // Event handler to rotate the nextCardImageView by 90 degrees when clicked
                onAction = _ => {
                  currentRotation = (currentRotation + 90) % 360 // Increment rotation by 90 degrees and loop after 360
                  nextCardImageView.rotate = currentRotation
                }
              }
              children = Seq(nextCardImageView, rotateButton)
            }


            // add BorderPane for buttons to select placement of liegeman
            val gridPane = new GridPane {
              // add buttons to BorderPane at Top/Left/Bottom/Right
              alignment = Pos.Center // Centering the buttons in the gri

              // add 3x3 GridPain of Buttons to Center


            }
            val buttonDetails = List(
              (0, 2, "K"), // button02
              (1, 1, "P"), // button11
              (1, 2, "W"), // button12
              (1, 3, "P"), // button13
              (2, 0, "K"), // button20
              (2, 1, "W"), // button21
              (2, 2, "M"), // button22
              (2, 3, "W"), // button23
              (2, 4, "K"), // button24
              (3, 1, "P"), // button31
              (3, 2, "W"), // button32
              (3, 3, "P"), // button33
              (4, 2, "K") // button42
            )
            buttonDetails.foreach { case (row, col, label) =>
              val button = new Button(label)
              // Set the button size to be uniform
              button.setMinWidth(60)
              button.setMinHeight(60)
              gridPane.add(button, col, row)
            }
            children = Seq(nextTileStackPane, gridPane)
          }
          center = new StackPane {
            // Create a grid (15x15) of StackPanes with ImageViews and transparent buttons on top
            val initialCardImage = new Image(getClass.getClassLoader.getResource("background_tile.png").toString)
            val fieldGridPane = new GridPane {
            }
            for (row <- 0 until 15) {
              for (column <- 0 until 15) {
                val fieldStackPane = new StackPane {
                  maxWidth = viewWidth / 16
                  maxHeight() = viewHeight / 16

                  val cardImageView = new ImageView(initialCardImage) {
                    fitWidth = viewWidth / 16
                    fitHeight = viewHeight / 16
                    preserveRatio = true
                  }
                  tileImages(row)(column) = cardImageView
                  val fieldButton = new Button {
                    maxWidth = Double.MaxValue
                    maxHeight = Double.MaxValue
                    style = "-fx-background-color: transparent; " +
                      "-fx-border-color: transparent; " +
                      "-fx-text-fill: #000000;"

                    // Event handler to call add from tabletop
                    //val newTile = tabletop.gameData.currentTile() // Fetch the next tile from game data
                    //cardImageView.image = new Image(getImagePath(newTile)) // Update the image
                    onAction = _ => {
                      tabletop.addTileToMap(Index(row), Index(column), tabletop.gameData.currentTile().rotate(currentRotation / 90))
                      currentRotation = 0
                      nextCardImageView.rotate = 0
                    }
                  }
                  children = Seq(cardImageView, fieldButton)
                }
                fieldGridPane.add(fieldStackPane, column, row)
              }
            }
            children = Seq(fieldGridPane)
          }
          right = new VBox {
            // add VBoxes depending on tabletop.players list size
            // add player info labels to VBoxes
            tabletop.gameData.players.zipWithIndex.foreach { case (player, index) =>
              val playerVBox = new VBox {
                alignment = Pos.Center
                children = Seq(
                  new Text(s"Player ${index + 1}") {
                    fill = getColorFromEnum(player.color)
                    font = Font.font("Century", 24)
                  },
                  new Text(s"Points ${player.points}") {
                    fill = getColorFromEnum(player.color)
                    font = Font.font("Century", 24)
                  },
                  new Text(s"Liegemen ${player.meepleCount}") {
                    fill = getColorFromEnum(player.color)
                    font = Font.font("Century", 24)
                  }
                )
              }
              children.add(playerVBox)
            }
          }
        }
      }
    }
    update()
  }

  override def update(): Unit = {
    // update scene based on GameData state
    Platform.runLater {
      for (row <- 0 to 14) {
        for (column <- 0 to 14) {
          tabletop.gameData.map.data.get(Index(row), Index(column)).flatten match {
            case Some(tile) =>
              if (tileImages(row)(column) != null) {
                tileImages(row)(column).image = new Image(getImagePath(tile)) // Update the corresponding image view
                tileImages(row)(column).rotate = tile.rotation * 90
              }
            case None =>
              if (tileImages(row)(column) != null) {
                tileImages(row)(column).image = new Image(getClass.getClassLoader.getResource("background_tile.png").toString) // Update the corresponding image view
              }
          }
        }
      } // Get tile from game data
      // Update the next card image when the current tile changes

      // TODO use option instead of checking for null exception
      if (nextCardImageView != null) {
        val nextTile = tabletop.gameData.currentTile() // Fetch the new current tile
        val nextCardImage = new Image(getImagePath(nextTile)) // Get the image for the new tile
        nextCardImageView.image = nextCardImage // Update the ImageView
      }
    }
  }

  def getImagePath(tile: Tile): String = {
    val filename = tile.name match {
      case "A" => "tile-a.png"
      case "B" => "tile-b.png"
      case "C" => "tile-c.png"
      case "D" => "tile-d.png"
      case "E" => "tile-e.png"
      case "F" => "tile-f.png"
      case "G" => "tile-g.png"
      case "H" => "tile-h.png"
      case "I" => "tile-i.png"
      case "J" => "tile-j.png"
      case "K" => "tile-k.png"
      case "L" => "tile-l.png"
      case "M" => "tile-m.png"
      case "N" => "tile-n.png"
      case "O" => "tile-o.png"
      case "P" => "tile-p.png"
      case "Q" => "tile-q.png"
      case "R" => "tile-r.png"
      case "S" => "tile-s.png"
      case "T" => "tile-t.png"
      case "U" => "tile-u.png"
      case "V" => "tile-v.png"
      case "W" => "tile-w.png"
      case "X" => "tile-x.png"
      case _ => "default_tile.png"
    }
    val imagePath = getClass.getClassLoader.getResource(filename)
    // If the resource is found, return its path, otherwise return a default or error string
    Option(imagePath) match {
      case Some(path) => path.toString // Return the resource URL as a string
      case None => "Resource not found" // Handle the case where the resource doesn't exist
    }
  }
}
