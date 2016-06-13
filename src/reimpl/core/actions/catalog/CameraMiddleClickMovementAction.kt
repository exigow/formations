package core.actions.catalog

import com.badlogic.gdx.math.Vector2
import core.Camera
import core.actions.Action
import core.input.event.EventBundle
import core.input.mappings.ButtonState
import core.input.mappings.MouseButton

class CameraMiddleClickMovementAction(private val cameraDep: Camera) : Action {

  private var isMoving = false
  private val movementPivot = Vector2()
  private val zoomBounceAmount = .1f
  private val events = object : EventBundle {

    override fun onMouse(): (MouseButton, ButtonState) -> Unit = { button, state ->
      if (isMiddleClick(button)) {
        when (state) {
          ButtonState.PRESS -> onStart()
          ButtonState.RELEASE -> onEnd()
        }
      }
    }

    override fun onTick(): (Float) -> Unit = {
      if (isMoving)
        updatePositionOnTick()
    }

  }

  override fun events() = events

  private fun isMiddleClick(button: MouseButton) = button == MouseButton.MOUSE_MIDDLE

  private fun onStart() {
    isMoving = true
    movementPivot.set(cameraDep.position()).add(cameraDep.mousePosition());
    cameraDep.zoomRelative(-zoomBounceAmount)
  }

  private fun updatePositionOnTick() {
    cameraDep.lookAt(calculateDifference())
  }

  private fun onEnd() {
    isMoving = false
    cameraDep.lookAt(calculateDifference())
    cameraDep.zoomRelative(zoomBounceAmount)
  }

  private fun calculateDifference(): Vector2 {
    val difference = Vector2()
    difference.set(movementPivot)
    return difference.sub(cameraDep.mousePosition())
  }

  override fun isWorking() = isMoving

}