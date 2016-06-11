package actions

import core.Camera
import core.input.EventRegistry
import core.input.mappings.ButtonState
import core.input.mappings.MouseButton
import game.Ship
import game.World

class CameraUnitZoomDoubleClickAction(private val worldDep: World, val cameraDep: Camera) : Action {

  private val minimumDoubleClickTime = .25f // in seconds
  private var timer: Float = minimumDoubleClickTime
  private var previousShip: Ship? = null

  override fun bind(registry: EventRegistry) {
    registry.newEventOnMouse { button, state ->
      if (isLeftMouseClicked(button, state))
        doOnClick()
    }
    registry.newEventOnTick { delta ->
      countTime(delta)
    }
  }

  private fun isLeftMouseClicked(button: MouseButton, state: ButtonState) = button == MouseButton.MOUSE_LEFT && state == ButtonState.PRESS

  private fun doOnClick() {
    val radius = cameraDep.scaledClickRadius()
    val ship = worldDep.findClosestShipInMaxRadius(cameraDep.mousePosition(), radius)
    if (timer < minimumDoubleClickTime && ship == previousShip && ship != null) {
      cameraDep.lookAt(ship.position)
      cameraDep.zoomTo(.75f)
    }
    previousShip = ship
    timer = 0f
  }

  private fun countTime(delta: Float) {
    timer += delta
  }

}