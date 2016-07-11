package core.actions.catalog

import commons.Logger
import core.Camera
import core.actions.Action
import core.input.event.bundles.ThreeStateButtonEventBundle
import core.input.mappings.MouseButton
import game.PlayerContext
import game.Squad


class CameraShipLockAction(private val cameraDep: Camera, private val context: PlayerContext) : Action {

  private val events = object : ThreeStateButtonEventBundle(MouseButton.MOUSE_MIDDLE) {

    override fun onPress() {
    }

    override fun onRelease() {
      if (context.isHovering())
        lock(context.hovered!!)
    }

    override fun onHold(delta: Float) {
    }

  }.toBundle()

  override fun events() = events

  private fun lock(squad: Squad) {
    cameraDep.lockOn(squad)
    cameraDep.zoomTo(.5f)
    Logger.ACTION.log("Locking camera on $squad")
  }

}
