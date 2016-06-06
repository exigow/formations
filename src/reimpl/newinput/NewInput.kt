package newinput

import Camera
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Vector2
import java.util.*

object NewInput {

  init {
    Gdx.input.inputProcessor = InputWrapper;
  }

  fun update(delta: Float) {
    val position = Mouse.screenPosition()
    Mouse.values().filter { it.isTicking }
      .forEach {
        it.onTickRegistrar.forEach {
          it.invoke(position, delta)
        }
      }
  }

  /*private fun mouseScreenPosition(): Vector2 {
    return Vector2(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())
  }*/

  private object InputWrapper : InputAdapter() {

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, key: Int): Boolean {
      val button = Mouse.findByKey(key) ?: return false
      button.isTicking = false
      val position = wrapToVector2(screenX, screenY)
      button.onReleaseRegistrar.forEach { it.invoke(position) }
      return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, key: Int): Boolean {
      val button = Mouse.findByKey(key) ?: return false
      button.isTicking = true
      val position = wrapToVector2(screenX, screenY)
      button.onPressRegistrar.forEach { it.invoke(position) }
      return true
    }

    private fun wrapToVector2(x: Int, y: Int) = Vector2(x.toFloat(), y.toFloat())

  }

  enum class Mouse(private val gdxKey: Int) {

    LEFT(0),
    RIGHT(1),
    MIDDLE(2);

    val onPressRegistrar = ArrayList<(position: Vector2) -> Unit>()
    val onReleaseRegistrar = ArrayList<(position: Vector2) -> Unit>()
    val onTickRegistrar = ArrayList<(position: Vector2, delta: Float) -> Unit>()
    var isTicking = false

    fun registerOnPress(event: (position: Vector2) -> Unit) =
      onPressRegistrar.add { event.invoke(it) }

    fun registerOnRelease(event: (position: Vector2) -> Unit) =
      onReleaseRegistrar.add { event.invoke(it) }

    fun registerOnPressedTick(event: (position: Vector2, delta: Float) -> Unit) =
      onTickRegistrar.add { position, delta -> event.invoke(position, delta) }

    fun clearRegistrars() {
      onPressRegistrar.clear()
      onReleaseRegistrar.clear()
      onTickRegistrar.clear()
    }

    companion object {

      fun findByKey(gdxKey: Int): Mouse? = Mouse.values().find { it.gdxKey == gdxKey }

      fun screenPosition() = Vector2(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())

      fun position() = Camera.unproject(screenPosition())

    }

  }

}