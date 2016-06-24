package core.actions.catalog

import commons.math.Vec2
import core.Camera
import core.actions.Action
import core.input.event.bundles.ThreeStateButtonEventBundle
import core.input.mappings.MouseButton

class CameraMiddleClickMovementAction(private val cameraDep: Camera) : Action {

  private var isDragging = false
  private var movementPivot = Vec2.zero()
  private val zoomBounceAmount = .025f

  private val events = object : ThreeStateButtonEventBundle(MouseButton.MOUSE_MIDDLE) {

    override fun onPress() {
      movementPivot = cameraDep.position() + cameraDep.mousePosition();
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

  private fun calculateDifference(): Vec2 {
    return movementPivot - cameraDep.mousePosition()
  }

  override fun isWorking() = isDragging

  override fun discardOn() = setOf(SelectionAction::class)
}