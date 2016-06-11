package actions

import core.Camera
import core.input.EventRegistry
import core.input.mappings.ScrollDirection

class CameraScrollZoomAction(private val cameraDep: Camera) : Action {

  override fun bind(registry: EventRegistry) {
    registry.newEventOnScroll { direction ->
      val amount = directionToCameraZoomAmount(direction)
      cameraDep.zoomRelative(amount)
    }
  }

  private fun directionToCameraZoomAmount(direction: ScrollDirection): Float {
    val strength = .125f
    return when (direction) {
      ScrollDirection.SCROLLING_UP -> strength
      ScrollDirection.SCROLLING_DOWN -> -strength
    }
  }

  override fun discardOn() = setOf(CameraMiddleClickMovementAction::class)

}