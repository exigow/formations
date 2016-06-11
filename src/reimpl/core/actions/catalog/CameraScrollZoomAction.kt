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
      cameraDep.zoomRelative(amount)
    }

  }

  override fun events() = events

  private fun directionToCameraZoomAmount(direction: ScrollDirection): Float {
    val strength = .125f
    return when (direction) {
      ScrollDirection.SCROLLING_UP -> strength
      ScrollDirection.SCROLLING_DOWN -> -strength
    }
  }

  override fun conflictsWith() = setOf(CameraMiddleClickMovementAction::class)

}