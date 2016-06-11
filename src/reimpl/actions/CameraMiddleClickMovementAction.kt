package actions

import com.badlogic.gdx.math.Vector2
import core.Camera
import core.input.EventRegistry
import core.input.mappings.ButtonState
import core.input.mappings.MouseButton

class CameraMiddleClickMovementAction(private val cameraDep: Camera) : Action {

  private var isMoving = false
  private val movementPivot = Vector2()
  private val zoomBounceAmount = .1f

  override fun bind(registry: EventRegistry) {
    registry.newEventOnMouse { button, state ->
      if (isMiddleClick(button)) {
        when (state) {
          ButtonState.PRESS -> onStart()
          ButtonState.RELEASE -> onEnd()
        }
      }
    }
    registry.newEventOnTick { if (isMoving) onTick() }
  }

  private fun isMiddleClick(button: MouseButton) = button == MouseButton.MOUSE_MIDDLE

  override fun discardOn() = setOf(CameraScrollZoomAction::class)

  private fun onStart() {
    isMoving = true
    movementPivot.set(cameraDep.position()).add(cameraDep.mousePosition());
    cameraDep.zoomRelative(-zoomBounceAmount)
  }

  private fun onTick() {
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
}