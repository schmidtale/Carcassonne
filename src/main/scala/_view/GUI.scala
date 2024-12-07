package _view

import util.Observer
import controller.Tabletop
import model.Color
import scalafx.application.JFXApp3
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
            // add imageView for next Card
            // TODO add button for rotation of card
            // TODO nextCardImageView inside of StackPane and Button on top
            val stackPane = new StackPane {
              val nextCardImage = new Image("C:\\Software Engineering\\Carcassonne\\src\\main\\resources\\tile-b.png")
              val nextCardImageView = new ImageView(nextCardImage) {
                preserveRatio = true
                fitWidth = viewWidth / 4 // TODO Adjust size as needed
              }
              val rotateButton = new Button {
                maxWidth = Double.MaxValue
                maxHeight = Double.MaxValue
                style = "-fx-background-color: transparent; " +
                  "-fx-border-color: transparent; " +
                  "-fx-text-fill: #000000;"
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
            children = Seq(stackPane, gridPane)
          }
          center = new StackPane {
            val backgroundImage = new Image("C:\\Software Engineering\\Carcassonne\\src\\main\\resources\\Light_Wooden_Background.png")
            val imageView = new ImageView(backgroundImage) {
              preserveRatio = true
              fitWidth = viewWidth
              fitHeight = viewHeight
            }
            // Create a grid (15x15) of StackPanes with ImageViews and transparent buttons on top
            val cardImage = new Image("C:\\Software Engineering\\Carcassonne\\src\\main\\resources\\tile-a.png")
            val fieldGridPane = new GridPane {
            }
            for (i <- 0 until 15) {
              for (j <- 0 until 15) {
                val fieldStackPane = new StackPane {
                  maxWidth = viewWidth / 16
                  maxHeight() = viewHeight / 16

                  val cardImageView = new ImageView(cardImage) {
                    fitWidth = viewWidth / 16
                    fitHeight = viewHeight / 16
                    preserveRatio = true
                  }
                  val fieldButton = new Button {
                    maxWidth = Double.MaxValue
                    maxHeight = Double.MaxValue
                    style = "-fx-background-color: transparent; " +
                      "-fx-border-color: transparent; " +
                      "-fx-text-fill: #000000;"
                  }
                  children = Seq(cardImageView, fieldButton)
                }
                fieldGridPane.add(fieldStackPane, i, j)
              }
            }

            //children = Seq(imageView, gridPane) // TODO add background image
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
  }

  override def update(): Unit = {
    // TODO update scene based on GameData state
  }
}
