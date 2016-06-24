package core.actions.catalog

import commons.Logger
import commons.math.Vec2
import core.Camera
import core.actions.Action
import core.input.event.EventBundle
import core.input.mappings.ButtonState
import core.input.mappings.KeyboardButton
import core.input.mappings.MouseButton
import game.PlayerContext
import game.Squad
import game.World
import game.orders.MoveOrder

class OrderingActionClass(val camera: Camera, val context: PlayerContext, val world: World) : Action {

  private var isAlting = false
  private val events = object : EventBundle {

    override fun onMouse(): (MouseButton, ButtonState) -> Unit = {
      button, state ->
      if (isLeftPressed(button, state))
        onPress()
    }

    override fun onKeyboard(): (KeyboardButton, ButtonState) -> Unit = {
      button, state ->
      if (button == KeyboardButton.SHIFT)
        when (state) {
          ButtonState.PRESS -> isAlting = true
          ButtonState.RELEASE -> isAlting = false
        }
    }

  }

  private fun isLeftPressed(button: MouseButton, state: ButtonState) = button == MouseButton.MOUSE_RIGHT && state == ButtonState.PRESS

  private fun onPress() {
    if (context.hasSelection())
      if (context.isHovering())
        orderOn(context.hovered!!)
      else
        orderBackground()
    else
      doNothing()
  }

  fun orderOn(target: Squad) {
    Logger.ACTION.log("Setting orders on hover: $target.")
  }

  fun orderBackground() {
    if (isAlting)
      Logger.ACTION.log("Is alting.")
    Logger.ACTION.log("Move order.")
    val new = world.joinToNewCollective(context.selected)
    val order = MoveOrder(camera.mousePosition(), 0f)

    // todo remove this shit soon
    new.squads.flatMap { it.ships }.forEach { it.movementTarget = camera.mousePosition() }

    new.orders.add(order)
    new.orders.add(MoveOrder(camera.mousePosition() + Vec2(128, 0), 0f))
  }

  fun doNothing() {
    Logger.ACTION.log("Unable to set orders. Empty selection.")
  }

  override fun events() = events

  override fun discardOn() = setOf(SelectionAction::class)

}

