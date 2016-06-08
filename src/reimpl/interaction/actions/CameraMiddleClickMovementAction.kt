package interaction.actions

import Camera
import com.badlogic.gdx.math.Vector2
import input.Input

class CameraMiddleClickMovementAction : Action {

  private val key = Input.Button.MOUSE_MIDDLE
  private val movementPivot = Vector2()
  private var lock = false
  private val zoomBounceAmount = .1f

  private val startSelecting = Input.Event.of {
    movementPivot.set(Camera.position()).add(Input.Button.position());
    Camera.zoomRelative(-zoomBounceAmount)
    lock = true
  }
  private val continueSelectingOnTick = Input.Event.of {
    Camera.lookAt(calculateDifference())
  }
  private val endSelection = Input.Event.of {
    Camera.lookAt(calculateDifference())
    Camera.zoomRelative(zoomBounceAmount)
    lock = false
  }

  override fun bind() {
    key.onPress.register(startSelecting)
    key.onTick.register(continueSelectingOnTick)
    key.onRelease.register(endSelection)
  }

  override fun unbind() {
    key.onPress.unregister(startSelecting)
    key.onTick.unregister(continueSelectingOnTick)
    key.onRelease.unregister(endSelection)
  }

  override fun isWorking() = lock

  private fun calculateDifference(): Vector2 {
    val difference = Vector2()
    difference.set(movementPivot)
    return difference.sub(Input.Button.position())
  }


}
