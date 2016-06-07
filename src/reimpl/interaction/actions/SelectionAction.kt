package interaction.actions

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import input.Input

class SelectionAction {

  private val selectionTool = SelectionRectangle()
  private val key = Input.Mouse.LEFT;
  var isSelecting = false

  fun registerEvent() {
    fun pointer() = Input.Mouse.position()
    /*key.registerOnPress {
      selectionTool.startFrom(pointer())
      isSelecting = true
    }
    key.registerOnPressedTick {
      selectionTool.endTo(pointer())
      //if (selectionTool.distanceFromStartingPoint() > pointerRadius)
      //singleSelectionLock = false
      //if (singleSelectionLock == false) {
      //val squadsInsideSelection = world.findSquadsInside(selectionTool.selectionRectangle())
      //highlightedSquads.clear()
      //highlightedSquads.addAll(squadsInsideSelection)
      //Renderer.renderRectangle(selectionTool.selectionRectangle())
      //}
    }
    key.registerOnRelease {
      selectionTool.endTo(pointer())
      isSelecting = false
    }*/
  }

  fun rectangle() = selectionTool.selectionRectangle()

  private class SelectionRectangle {

    private val startPivot = Vector2()
    private val endPivot = Vector2()

    fun startFrom(where: Vector2) = startPivot.set(where)

    fun endTo(where: Vector2) = endPivot.set(where)

    fun selectionRectangle() = convert(startPivot, endPivot)

    fun distanceFromStartingPoint() = startPivot.dst(endPivot)

    private companion object {

      fun convert(start: Vector2, end: Vector2): Rectangle {
        val width = end.x - start.x
        val height = end.y - start.y
        val rect = Rectangle(start.x, start.y, width, height)
        return fixInvertedVectors(rect)
      }

      fun fixInvertedVectors(source: Rectangle): Rectangle {
        val fixed = Rectangle(source)
        if (fixed.width < 0) {
          fixed.width = Math.abs(fixed.width)
          fixed.x = fixed.x - fixed.width
        }
        if (fixed.height < 0) {
          fixed.height = Math.abs(fixed.height)
          fixed.y = fixed.y - fixed.height
        }
        return fixed
      }

    }

  }

}