package interaction.actions

import Camera
import com.badlogic.gdx.math.Vector2
import input.Input

object CameraArrowsMovementAction : Action {

  val keyVectorMappings = mapOf(
    Input.Button.ARROW_UP to Vector2(0f, 1f),
    Input.Button.ARROW_DOWN to Vector2(0f, -1f),
    Input.Button.ARROW_LEFT to Vector2(-1f, 0f),
    Input.Button.ARROW_RIGHT to Vector2(1f, 0f)
  ).mapValues { entry ->
    val vec = entry.value
    Input.Event.of {
      moveCamera(vec)
    }
  }

  fun moveCamera(relative: Vector2) {
    val movementFactor = 16f
    val result = Vector2(relative).scl(movementFactor)
    Camera.moveRelative(result)
  }

  override fun bind() {
    keyVectorMappings.keys.forEach { it.onTick.register(keyVectorMappings[it]!!) }
  }

  override fun unbind() {
    keyVectorMappings.keys.forEach { it.onTick.unregister(keyVectorMappings[it]!!) }
  }

  override fun conflictsWith() = setOf(CameraMiddleClickMovementAction)

  override fun isWorking(): Boolean {

    return false
  }

}