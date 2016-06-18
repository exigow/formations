package core.actions.catalog

import commons.Logger
import core.Camera
import core.actions.Action
import core.actions.catalog.utils.DraggingTool
import core.input.event.bundles.ThreeStateButtonEventBundle
import core.input.mappings.MouseButton
import game.PlayerContext
import game.Squad
import game.World
import game.orders.MoveOrder
import kotlin.reflect.KClass

class OrderingActionClass(val camera: Camera, val context: PlayerContext, val world: World) : Action {

  private val draggingTool = DraggingTool()
  private val events = object : ThreeStateButtonEventBundle(MouseButton.MOUSE_RIGHT) {

    override fun onPress() {
      if (context.hasSelection())
        if (context.isHovering())
          orderOn(context.hovered!!)
        else
          orderBackground()
      else
        doNothing()
    }

    override fun onRelease() {

    }

    override fun onHold(delta: Float) {
      draggingTool.update(camera.mousePosition(), camera.scaledClickRadius())
    }

  }.toBundle()

  fun orderOn(target: Squad) {
    Logger.ACTION.log("Setting orders on hover: $target.")
  }

  fun orderBackground() {
    val pointer = camera.mousePosition()
    draggingTool.pinTo(pointer)
    Logger.ACTION.log("Move order to $pointer for ${context.selected}.")


    val new = world.joinToNewCollective(context.selected)
    val order = MoveOrder(pointer, 0f)
    new.orders.add(order)
  }

  fun doNothing() {
    Logger.ACTION.log("Unable to set orders. Empty selection.")
  }

  override fun events() = events

  override fun discardOn() = setOf(SelectionAction::class)

}

