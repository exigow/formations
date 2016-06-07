package interaction.actions

import Camera
import input.Input

object CameraScrollZoomingAction : Action {

  private val zoomAmount = .075f
  private val scroll = Input.Scroll
  private val zoomIn = Input.Event.of {
    Camera.zoomRelative(zoomAmount)
  }
  private val zoomOut = Input.Event.of {
    Camera.zoomRelative(-zoomAmount)
  }

  override fun bind() {
    scroll.onScrollIn.register(zoomIn)
    scroll.onScrollOut.register(zoomOut)
  }

  override fun unbind() {
    scroll.onScrollIn.unregister(zoomIn)
    scroll.onScrollOut.unregister(zoomOut)
  }

  override fun conflictsWith() = emptySet<Action>() // no conflicts

  override fun isWorking() = false // actions are instant (without state)

}