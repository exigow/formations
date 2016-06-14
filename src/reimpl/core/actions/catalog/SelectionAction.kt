package core.actions.catalog

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import core.Camera
import core.actions.Action
import core.input.event.EventBundle
import core.input.mappings.ButtonState
import core.input.mappings.MouseButton
import game.Squad
import game.World
import java.util.*

class SelectionAction(val cameraDep: Camera, val world: World) : Action {

  //private var doubleClickLock = false
  //private var doubleClickTime = 0f
  private val selectionTool = SelectionRectangle()
  private var isSelecting = false
  private val selected: MutableList<Squad> = ArrayList()
  private val events = object : EventBundle {

    override fun onMouse(): (MouseButton, ButtonState) -> Unit = {
      button, state ->
      if (isLeftMouse(button))
        when (state) {
          ButtonState.PRESS -> startSelection()
          ButtonState.RELEASE -> endSelection()
        }
    }

    override fun onTick(): (Float) -> Unit = {
      if (isSelecting)
        updateSelection()
    }

  }

  private fun isLeftMouse(button: MouseButton) = button == MouseButton.MOUSE_LEFT

  private fun updateSelection() {
    selectionTool.endTo(pointer())
    selected.clear()
    selected.addAll(world.findSquadsInside(enfoldedSelectionRectangle()))
  }

  private fun startSelection() {
    selectionTool.startFrom(pointer())
    updateSelection()
    isSelecting = true
    println("selection starts")
  }

  private fun endSelection() {
    updateSelection()
    isSelecting = false
    selected.clear()
    println("selection ends")
  }

  override fun events() = events

  private fun pointer() = cameraDep.mousePosition()

  fun rectangle(): Rectangle? {
    if (!isSelecting)
      return null
    return enfoldedSelectionRectangle()
  }

  fun selectedSquads() = selected

  private fun hasDragged() = selectionTool.distanceFromStartingPoint() > cameraDep.scaledClickRadius()

  private fun enfoldedSelectionRectangle() = enfold(selectionTool.selectionRectangle(), cameraDep.scaledClickRadius() * 2)

  private fun enfold(rect: Rectangle, border: Float): Rectangle {
    val r =  Rectangle(rect)
    r.x -= border / 2f
    r.y -= border / 2f
    r.width += border
    r.height += border
    return r
  }

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