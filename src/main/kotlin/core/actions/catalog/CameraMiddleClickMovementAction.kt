package core.actions.catalog

import Vec2
import core.Camera
import core.actions.Action
import core.actions.catalog.utils.DraggingTool
import core.input.event.bundles.ThreeStateButtonEventBundle
import core.input.mappings.MouseButton

class CameraMiddleClickMovementAction(private val cameraDep: Camera) : Action {

  private var movementPivot = Vec2.zero()
  private val tool = DraggingTool()

  private val events = object : ThreeStateButtonEventBundle(MouseButton.MOUSE_MIDDLE) {

    override fun onPress() {
      movementPivot = cameraDep.position() + cameraDep.mousePosition();
      tool.pinTo(cameraDep.mouseScreenPosition())
    }

    override fun onRelease() {
      updateLookIfDragging()
      tool.reset()
    }

    override fun onHold(delta: Float) {
      tool.update(cameraDep.mouseScreenPosition(), 4f)
      updateLookIfDragging()
    }

  }.toBundle()

  private fun updateLookIfDragging() {
    if (tool.isDragging())
      cameraDep.lookAt(calculateDifference())
  }

  override fun events() = events

  private fun calculateDifference(): Vec2 {
    return movementPivot - cameraDep.mousePosition()
  }

  override fun isWorking() = tool.isDragging()

  override fun discardOn() = setOf(SelectionAction::class, CameraArrowsMovementAction::class)

}