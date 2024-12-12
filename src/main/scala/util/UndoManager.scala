package util

class UndoManager {
  private var undoStack: List[Command] = Nil
  private var redoStack: List[Command] = Nil

  def doStep(command: Command): Unit = {
    // adds command to top of undoStack
    undoStack = command :: undoStack
    redoStack = Nil
    command.doStep()
  }

  def undoStep(): Unit = {
    undoStack match {
      case Nil => // no commands to undo
      // :: cons operator splits list into head and tail
      case head :: stack => {
        head.undoStep()
        // remove head command
        undoStack = stack
        // add head to redoStack
        redoStack = head :: redoStack
      }
    }
  }

  def redoStep(): Unit = {
    redoStack match
      case Nil => // no commands to redo
      case head :: stack => {
        head.redoStep()
        redoStack = stack
        undoStack = head :: undoStack
      }
  }
}
