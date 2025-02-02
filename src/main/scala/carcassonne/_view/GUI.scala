package carcassonne._view

import carcassonne.controller.controllerComponent.ControllerTrait
import carcassonne.model.gameDataComponent.gameDataBaseImplementation.{Color, Index}
import carcassonne.util.Observer

import scalafx.Includes.*
import scalafx.application.{JFXApp3, Platform}
import scalafx.event.ActionEvent
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.*
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.input.{KeyCode, KeyCombination, KeyEvent}
import scalafx.scene.layout.*
import scalafx.scene.{Scene, paint}
import scalafx.scene.paint.Color.*
import scalafx.scene.text.{Font, Text}
import scalafx.stage.Screen

// Function to convert enum Color to scalafx Color
def getColorFromEnum(playerColor: Color): scalafx.scene.paint.Color = {
  playerColor match {
    case Color.blue => scalafx.scene.paint.Color.rgb(56, 58, 107)
    case Color.red => scalafx.scene.paint.Color.rgb(203, 31, 115)
    case Color.green => scalafx.scene.paint.Color.rgb(224, 58, 60)
    case Color.yellow => scalafx.scene.paint.Color.rgb(234, 109, 61)
    case Color.black => scalafx.scene.paint.Color.rgb(252, 199, 45)
  }
}

class GUI(using tabletop: ControllerTrait) extends JFXApp3 with Observer {
  tabletop.add(this)

  // Store tileImages 15x15 grid for tiles
  private val tileImages: Array[Array[ImageView]] = Array.ofDim[ImageView](15, 15)
  private val fieldButtons: Array[Array[Button]] = Array.ofDim[Button](15, 15)

  private var nextCardImageView: ImageView = _

  private val fieldGridPane = new GridPane

  // current rotation of nextCardImageView tile
  private var currentRotation = 0

  /* Viewport sizes */
  private var viewHeight: Double = 0
  private var viewWidth: Double = 0

  // Cache for tiles
  private var tileCache: Map[String, Image] = Map()

  override def start(): Unit = {
    viewHeight = Screen.primary.visualBounds.height
    viewWidth = Screen.primary.visualBounds.width

    val backgroundTile = new Image(
      getClass.getClassLoader.getResource("background_tile.png").toString,
      requestedWidth = viewWidth / 16,
      requestedHeight = viewHeight / 16,
      preserveRatio = true,
      smooth = true
    )
    /* Initialize tile cache */
    tileCache = Map("background" -> backgroundTile) ++
      (for (tileName <- List(
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X"
      )) yield {
        tileName -> new Image(
          getClass.getClassLoader.getResource(s"tile-$tileName.png").toString,
        )
      }).toMap

    stage = new JFXApp3.PrimaryStage {
      title = "Carcasonne"
      width = viewWidth
      height = viewHeight
      resizable = false

      val taskbar_icon = new Image(getClass.getClassLoader.getResource("icon.png").toString)
      icons.add(taskbar_icon)
      scene = new Scene {
        fill = Black
        root = new BorderPane {
          top = new MenuBar {
            menus = Seq(
              new Menu("File") {
                items = Seq(
                  new MenuItem("Reset") {
                    graphic = new ImageView(new Image(getClass.getClassLoader.getResource("new_icon.png").toString)) {
                      fitWidth = 16
                      fitHeight = 16
                      preserveRatio = true
                    }
                    accelerator = KeyCombination.keyCombination("Ctrl+R")
                    onAction = (e: ActionEvent) => {
                      tabletop.resetGameData()
                    }
                  },
                  new MenuItem("Save") {
                    graphic = new ImageView(new Image(getClass.getClassLoader.getResource("save_icon.png").toString)) {
                      fitWidth = 16
                      fitHeight = 16
                      preserveRatio = true
                    }
                    accelerator = KeyCombination.keyCombination("Ctrl+S")
                    onAction = (e: ActionEvent) => {
                      tabletop.save()
                    }
                  },
                  new MenuItem("Load") {
                    graphic = new ImageView(new Image(getClass.getClassLoader.getResource("load_icon.png").toString)) {
                      fitWidth = 16
                      fitHeight = 16
                      preserveRatio = true
                    }
                    accelerator = KeyCombination.keyCombination("Ctrl+L")
                    onAction = (e: ActionEvent) => {
                      tabletop.load()
                    }
                  },
                  new MenuItem("Quit") {
                    graphic = new ImageView(new Image(getClass.getClassLoader.getResource("quit_icon.png").toString)) {
                      fitWidth = 16
                      fitHeight = 16
                      preserveRatio = true
                    }
                    accelerator = KeyCombination.keyCombination("Ctrl+Q")
                    onAction = (e: ActionEvent) => {
                      Platform.exit()
                      System.exit(0)
                    }
                  }
                )
              }
            )
          }
          left = new VBox {
            style = s"-fx-background-color:black"
            // add imageView for next Card and Button for rotation on top
            val nextTileStackPane = new StackPane {
              nextCardImageView = new ImageView() {
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

            // add BorderPane for buttons to select placement of liegeman/undo/redo
            val controlPanelGridPane = new GridPane {
              // add buttons to BorderPane at Top/Left/Bottom/Right
              alignment = Pos.Center // Centering the buttons in the grid
              // add 3x3 GridPane of Buttons to Center
            }

            val undoButton = new Button() {
              minWidth = 60
              minHeight = 60
              style = "-fx-background-color: #FF6347; -fx-text-fill: white;" // Red background, white text
              onAction = _ => tabletop.undo()
              // Set the image for the button
              graphic = new ImageView(new Image(getClass.getClassLoader.getResource("undo_button.png").toString)) {
                fitWidth = 20
                fitHeight = 20
                preserveRatio = true
              }
              // Add a tooltip to the button
              tooltip = new Tooltip("Undo")
            }
            val redoButton = new Button() {
              minWidth = 60
              minHeight = 60
              style = "-fx-background-color: #4682B4; -fx-text-fill: white;" // Blue background, white text
              onAction = _ => tabletop.redo()

              graphic = new ImageView(new Image(getClass.getClassLoader.getResource("redo_button.png").toString)) {
                fitWidth = 20
                fitHeight = 20
                preserveRatio = true
              }
              // Add a tooltip to the button
              tooltip = new Tooltip("Redo")
            }
            controlPanelGridPane.add(undoButton, 0, 0)
            controlPanelGridPane.add(redoButton, 4, 0)

            val buttonDetails = List(
              (0, 2, "Knight"), // button02
              (1, 1, "Peasant"), // button11
              (1, 2, "Waylayer"), // button12
              (1, 3, "Peasant"), // button13
              (2, 0, "Knight"), // button20
              (2, 1, "Waylayer"), // button21
              (2, 2, "Monk"), // button22
              (2, 3, "Waylayer"), // button23
              (2, 4, "Knight"), // button24
              (3, 1, "Peasant"), // button31
              (3, 2, "Waylayer"), // button32
              (3, 3, "Peasant"), // button33
              (4, 2, "Knight") // button42
            )
            buttonDetails.foreach { case (row, col, label) =>
              val stackPane = new StackPane {
                background = new Background(Array(new BackgroundFill(White, CornerRadii.Empty, Insets.Empty)))
                val imageView = label match {
                  case "Peasant" =>
                    val image = new Image(getClass.getClassLoader.getResource("peasant_button.png").toString)
                    new ImageView(image) {
                      fitWidth = 60
                      fitHeight = 60
                      preserveRatio = true
                    }
                  case "Knight" =>
                    val image = new Image(getClass.getClassLoader.getResource("knight_button.png").toString)
                    new ImageView(image) {
                      fitWidth = 60
                      fitHeight = 60
                      preserveRatio = true
                    }
                  case "Monk" =>
                    val image = new Image(getClass.getClassLoader.getResource("monk_button.png").toString)
                    new ImageView(image) {
                      fitWidth = 60
                      fitHeight = 60
                      preserveRatio = true
                    }
                  case "Waylayer" =>
                    val image = new Image(getClass.getClassLoader.getResource("waylayer_button.png").toString)
                    new ImageView(image) {
                      fitWidth = 60
                      fitHeight = 60
                      preserveRatio = true
                    }
                  case _ =>
                    new ImageView(new Image(getClass.getClassLoader.getResource("default_tile.png").toString)) {
                      fitWidth = 60
                      fitHeight = 60
                      preserveRatio = true
                    }
                }

                val button = new Button() {
                  // Set the button size to be uniform
                  minWidth = 60
                  minHeight = 60
                  style = "-fx-background-color: transparent; -fx-border-color: transparent;"
                  //onAction = _ => println(s"Button $label clicked!")
                  tooltip = new Tooltip(s"$label")
                }
                children = Seq(imageView, button)
              }
              controlPanelGridPane.add(stackPane, col, row)
            }
            children = Seq(nextTileStackPane, controlPanelGridPane)
          }
          center = new StackPane {
            // Create a grid (15x15) of StackPanes with ImageViews and transparent buttons on top
            val initialCardImage = new Image(getClass.getClassLoader.getResource("background_tile.png").toString)
            // for fieldGridPane
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
                      tabletop.addCurrentTile(Index(row), Index(column), currentRotation / 90)
                      currentRotation = 0
                      nextCardImageView.rotate = 0
                    }
                  }
                  fieldButtons(row)(column) = fieldButton
                  children = Seq(cardImageView, fieldButton)
                }
                fieldGridPane.add(fieldStackPane, column, row)
                fieldGridPane.style = s"-fx-background-color:black"
              }
            }
            children = Seq(fieldGridPane)
          }
          right = new VBox {
            style = s"-fx-background-color:black"
            alignment = Pos.Center
            this.setPrefSize(viewHeight * 0.4, viewWidth)
            spacing = 10
            // add VBoxes depending on tabletop.players list size
            // add player info labels to VBoxes

            tabletop.gameData.players.zipWithIndex.foreach { case (player, index) =>
              val playerVBox = new VBox {
                alignment = Pos.Center
                padding = Insets(10)
                spacing = 5
                maxWidth = viewWidth * 0.15
                val colorHex = colorToHex(getColorFromEnum(player.color))
                style =
                  s"""-fx-background-color: ${colorHex};
                       -fx-background-radius: 15;
                       -fx-border-radius: 15;
                       -fx-border-width: 2;""" // Rounded corners

                children = Seq(
                  new Text(s"Player ${index + 1}") {
                    font = Font.font("Century", 24)
                  },
                  new Text(s"Points ${player.points}") {
                    font = Font.font("Century", 18)
                  },
                  new Text(s"Liegemen ${player.meepleCount}") {
                    font = Font.font("Century", 18)
                  }
                )
              }
              children.add(playerVBox)
            }
          }
        }
        onKeyPressed = (event: KeyEvent) => {
          if (event.code == KeyCode.Z) {
            tabletop.undo()
          }
          if (event.code == KeyCode.Y) {
            tabletop.redo()
          }
          if (event.code == KeyCode.N) {
            tabletop.resetGameData()
          }
        }
        // Handle the close button
        onCloseRequest = _ => {
          println("Closing application...")
          Platform.exit()
          System.exit(0)
        }
      }
    }
    update()
  }

  override def update(): Unit = {
    // update scene based on GameData state
    Platform.runLater {
      if (tabletop.gameData.turn >= tabletop.gameData.stack.size) {
        // Game has ended, show review scene
        showReviewScene()
      }
      else {
        for (row <- 0 to 14) {
          for (column <- 0 to 14) {
            tabletop.gameData.map.data.get(Index(row), Index(column)).flatten match {
              case Some(tile) =>
                Option(tileImages(row)(column)).foreach { ImageView =>
                  tileImages(row)(column).image = getTileImage(tile.name) // Update the corresponding image view
                  tileImages(row)(column).rotate = tile.rotation * 90
                }
                // Disable the button for filled tiles
                Option(fieldButtons(row)(column)).foreach(_.disable = true)
              case None =>
                Option(tileImages(row)(column)).foreach(_.image = tileCache("background")) // Update the corresponding image view
                // Enable the button for empty tiles
                Option(fieldButtons(row)(column)).foreach(_.disable = false)
            }
          }
        } // Get tile from game data
        // Update the next card image when the current tile changes

        Option(nextCardImageView).foreach { imageView =>
          val nextTile = tabletop.gameData.currentTile() // Fetch the new current tile
          val nextCardImage = getTileImage(nextTile.name) // Get the image for the new tile
          nextCardImageView.image = nextCardImage // Update the ImageView
        }
      }

    }
  }

  private def showReviewScene(): Unit = {
    stage.scene = new Scene {
      fill = Black
      root = new VBox {
        background = new Background(Array(new BackgroundFill(Black, CornerRadii.Empty, Insets.Empty)))
        fill = Black
        alignment = Pos.Center
        prefWidth = stage.width.value
        prefHeight = stage.height.value
        spacing = 20
        children = Seq(
          new Text("Game Over") {
            fill = White
            font = Font.font("Century", 36)
          },
          new Text("Final Scores") {
            fill = White
            font = Font.font("Century", 24)
          },
          new VBox {
            spacing = 10
            alignment = Pos.Center
            children = tabletop.gameData.players.map {
              player =>
                new Text(s"Player ${tabletop.gameData.players.indexOf(player) + 1}: ${player.points} points") {
                  fill = getColorFromEnum(player.color)
                  font = Font.font("Century", 20)
                }
            }
          },
          new Button("Exit to Main Menu") {
            onAction = _ => {
              // TODO switch to Menu scene
              Platform.exit()
              System.exit(0)
            }
          }
        )
      }
    }
  }

  private def colorToHex(color: scalafx.scene.paint.Color): String = {
    val red = (color.red * 255).toInt
    val green = (color.green * 255).toInt
    val blue = (color.blue * 255).toInt
    f"#$red%02X$green%02X$blue%02X" // Format it as #RRGGBB
  }

  // Function to get the image from the cache
  private def getTileImage(tileName: String): Image = {
    tileCache.getOrElse(tileName, new Image(getClass.getClassLoader.getResource("default_tile.png").toString))
  }
}
