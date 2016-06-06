package input

import Camera
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import input.adapters.ButtonAdapter
import input.adapters.MouseButtonAdapter

object Input {

  enum class Button(private val adapter: ButtonAdapter) {

    MOUSE_LEFT(MouseButtonAdapter(Input.Buttons.LEFT)),
    MOUSE_RIGHT(MouseButtonAdapter(Input.Buttons.RIGHT)),
    MOUSE_MIDDLE(MouseButtonAdapter(Input.Buttons.MIDDLE));

    fun isPressed() = adapter.isPressed()

    fun isHeld() = adapter.isHeld()

    fun isReleased() = adapter.isReleased();

    fun state() = adapter.state()

    companion object {

      fun updateStates() = Button.values().forEach { it.adapter.update() }

    }

  }

  fun update() {
    Button.updateStates()
  }

  fun getMousePositionOnScreen(): Vector2 {
    return Vector2(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())
  }

  fun getMousePositionInWorld(): Vector2 {
    val screenPosition = getMousePositionOnScreen()
    return Camera.unproject(screenPosition)
  }

}