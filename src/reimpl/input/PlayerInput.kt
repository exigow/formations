package input

import Camera
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2


object PlayerInput {

  val mouseLeft = MouseButton(Input.Buttons.LEFT)
  val mouseRight = MouseButton(Input.Buttons.RIGHT)

  fun updateStates() {
    mouseLeft.update()
    mouseRight.update()
  }

  fun getMousePositionOnScreen(): Vector2 {
    return Vector2(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())
  }

  fun getMousePositionInWorld(): Vector2 {
    val screenPosition = getMousePositionOnScreen()
    return Camera.unproject(screenPosition)
  }

}