package interaction.actions

import com.badlogic.gdx.math.Vector2
import input.Input

object CameraArrowsMovementAction : Action {

  // todo implement this shit

  val keyVectorMappings = mapOf(
    Input.Button.ARROW_UP to Vector2(0f, 1f),
    Input.Button.ARROW_DOWN to Vector2(0f, -1f),
    Input.Button.ARROW_LEFT to Vector2(-1f, 0f),
    Input.Button.ARROW_RIGHT to Vector2(1f, 0f)
  )

  override fun bind() {
    //throw UnsupportedOperationException()
  }

  override fun unbind() {
    //throw UnsupportedOperationException()
  }

  override fun conflictsWith() = setOf(CameraMiddleClickMovementAction)

  override fun isWorking(): Boolean {
    //throw UnsupportedOperationException()
    return false
  }

}