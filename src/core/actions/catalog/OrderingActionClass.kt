package core.actions.catalog

import commons.Logger
import core.actions.Action
import core.input.event.bundles.ThreeStateButtonEventBundle
import core.input.mappings.MouseButton
import game.PlayerContext

class OrderingActionClass(context: PlayerContext) : Action {

  private val events = object : ThreeStateButtonEventBundle(MouseButton.MOUSE_RIGHT) {

    override fun onPress() {
      if (context.hasSelection())
        Logger.ACTION.log("Setting orders.")
      else
        Logger.ACTION.log("Unable to set orders. Empty selection.")
    }

    override fun onRelease() {

    }

    override fun onHold(delta: Float) {

    }

  }.toBundle()

  override fun events() = events

}

