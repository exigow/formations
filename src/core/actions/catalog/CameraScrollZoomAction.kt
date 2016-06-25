package core.actions.catalog

import core.Camera
import core.actions.Action
import core.input.event.EventBundle
import core.input.mappings.ScrollDirection

class CameraScrollZoomAction(private val cameraDep: Camera) : Action {

  private val events = object : EventBundle {

    override fun onMouseScroll(): (ScrollDirection) -> Unit = {
      direction ->
      val amount = directionToCameraZoomAmount(direction)
      val strength = amount * .25f * cameraDep.renderingScale()
      cameraDep.zoomRelative(strength)
    }

  }

  override fun events() = events

  private fun directionToCameraZoomAmount(direction: ScrollDirection): Float {
    return when (direction) {
      ScrollDirection.SCROLLING_UP -> 1f
      ScrollDirection.SCROLLING_DOWN -> -1f
    }
  }

  override fun discardOn() = setOf(CameraMiddleClickMovementAction::class)

}