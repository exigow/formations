package interaction.actions

import Camera
import com.badlogic.gdx.math.Vector2
import input.Input

object CameraMiddleClickMovementAction : Action {

  private val key = Input.Mouse.MIDDLE
  private val movementPivot = Vector2()
  private var lock = false

  private val startSelecting = Input.Event.of {
    movementPivot.set(Camera.position()).add(Input.Mouse.position());
    Camera.zoomTo(.925f)
  }
  private val continueSelectingOnTick = Input.Event.of {
    Camera.lookAt(calculateDifference())
  }
  private val endSelection = Input.Event.of {
    Camera.lookAt(calculateDifference())
    Camera.zoomTo(1f)
  }

  override fun bind() {
    key.registerOnPress(startSelecting)
    key.registerOnPressedTick(continueSelectingOnTick)
    key.registerOnRelease(endSelection)
  }

  override fun unbind() {
    key.unregisterOnPress(startSelecting)
    key.unregisterOnPressedTick(continueSelectingOnTick)
    key.unregisterOnRelease(endSelection)
  }

  override fun conflictsWith() = setOf(CameraArrowsMovementAction)

  override fun isWorking() = lock

  fun calculateDifference(): Vector2 {
    val difference = Vector2()
    difference.set(movementPivot)
    return difference.sub(Input.Mouse.position())
  }


}
