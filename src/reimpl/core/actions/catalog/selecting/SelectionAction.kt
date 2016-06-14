package core.actions.catalog.selecting

import core.Camera
import core.Logger
import core.actions.Action
import core.input.event.bundles.ThreeStateButtonEventBundle
import core.input.mappings.MouseButton
import game.World

class SelectionAction(val cameraDep: Camera, val world: World) : Action {

  // TODO zrób tak, jak w mechanics.md
  //private val selection: MutableList<Squad> = ArrayList()
  private var time = 0f
  private var clickCounter = 0

  private val events = object : ThreeStateButtonEventBundle(MouseButton.MOUSE_LEFT) {

    override fun onPress() {
      if (isHoveringSomething()) {
        clickCounter += 1
        time = 0f
        when (clickCounter) {
          1 -> selectSingleSquad()
          2 -> selectVisibleSquads()
          3 -> selectAllSquads()
        }
        if (!hasStillTime()) {
          time = 0f;
          clickCounter = 0
        }
      } else {
        onBackgroundClick()
      }
    }

    override fun onRelease() {
    }

    override fun onHold(delta: Float) {
    }

    override fun onTick(delta: Float) {
      time += delta
      if (!hasStillTime())
        clickCounter = 0
      //Logger.ACTION.log("$clickCounter     $time")
    }

  }.toBundle()

  fun onBackgroundClick() {
    Logger.ACTION.log("background click")
  }

  fun selectSingleSquad() {
    Logger.ACTION.log("select SINGLE squad")
  }

  fun selectVisibleSquads() {
    Logger.ACTION.log("select VISIBLE squads")
  }

  fun selectAllSquads() {
    Logger.ACTION.log("select ALL squads")
  }

  private fun isHoveringSomething() = world.findClosestShipInMaxRadius(cameraDep.mousePosition(), cameraDep.scaledClickRadius()) != null

  private fun hasStillTime() = time < .25f

  override fun events() = events

}