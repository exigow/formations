package core.actions.catalog

import com.badlogic.gdx.math.Vector2
import core.Camera
import core.actions.Action
import core.input.event.bundles.ThreeStateButtonEventBundle
import core.input.mappings.MouseButton

class CameraMiddleClickMovementAction(private val cameraDep: Camera) : Action {

  private var isDragging = false
  private val movementPivot = Vector2()
  private val zoomBounceAmount = .1f

  private val events = object : ThreeStateButtonEventBundle(MouseButton.MOUSE_MIDDLE) {

    override fun onPress() {
      movementPivot.set(cameraDep.position()).add(cameraDep.mousePosition());
      cameraDep.zoomRelative(-zoomBounceAmount)
      isDragging = true
    }

    override fun onRelease() {
      cameraDep.lookAt(calculateDifference())
      cameraDep.zoomRelative(zoomBounceAmount)
      isDragging = false
    }

    override fun onHold(delta: Float) {
      cameraDep.lookAt(calculateDifference())
    }

  }.toBundle()

  override fun events() = events

  private fun calculateDifference(): Vector2 {
    val difference = Vector2()
    difference.set(movementPivot)
    return difference.sub(cameraDep.mousePosition())
  }

  override fun isWorking() = isDragging

}