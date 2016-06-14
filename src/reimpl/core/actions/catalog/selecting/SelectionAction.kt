package core.actions.catalog.selecting

import core.Camera
import core.Logger
import core.actions.Action
import core.input.event.bundles.ThreeStateButtonEventBundle
import core.input.mappings.MouseButton
import game.Squad
import game.World
import java.util.*

class SelectionAction(val cameraDep: Camera, val world: World) : Action {

  // TODO zrób tak, jak w mechanics.md
  private val selection: MutableList<Squad> = ArrayList()

  private val events = object : ThreeStateButtonEventBundle(MouseButton.MOUSE_LEFT) {

    override fun onPress() {
      Logger.ACTION.log("onPress")
    }

    override fun onRelease() {
      Logger.ACTION.log("onRelease")
    }

    override fun onHold(delta: Float) {
      Logger.ACTION.log("onHold")
    }

  }.toBundle()

  override fun events() = events

}