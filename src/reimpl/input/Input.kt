package input

import Camera
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Vector2
import java.util.*

object Input {

  init {
    Gdx.input.inputProcessor = InputWrapper;
  }

  fun update() {
    Button.values().filter { it.isTicking }
      .forEach {
        it.onTick.performAllEvents()
      }
  }

  private object InputWrapper : InputAdapter() {

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, key: Int)= buttonUp(key)

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, key: Int) = buttonDown(key)

    override fun keyUp(keycode: Int) = buttonUp(keycode)

    override fun keyDown(keycode: Int) = buttonDown(keycode)

    override fun scrolled(amount: Int): Boolean {
      when (amount) {
        1 -> Scroll.onScrollIn.performAllEvents()
        -1 -> Scroll.onScrollOut.performAllEvents()
      }
      return true
    }

    private fun buttonUp(gdxKey: Int): Boolean {
      val button = Button.findByKey(gdxKey) ?: return false
      button.isTicking = false
      button.onRelease.performAllEvents()
      return true
    }

    private fun buttonDown(gdxKey: Int): Boolean {
      val button = Button.findByKey(gdxKey) ?: return false
      button.isTicking = true
      button.onPress.performAllEvents()
      return true
    }

  }

  enum class Button(private val gdxKey: Int) {

    MOUSE_LEFT(com.badlogic.gdx.Input.Buttons.LEFT),
    MOUSE_RIGHT(com.badlogic.gdx.Input.Buttons.RIGHT),
    MOUSE_MIDDLE(com.badlogic.gdx.Input.Buttons.MIDDLE),

    SPACE(com.badlogic.gdx.Input.Keys.SPACE),
    ARROW_UP(com.badlogic.gdx.Input.Keys.UP),
    ARROW_DOWN(com.badlogic.gdx.Input.Keys.DOWN),
    ARROW_LEFT(com.badlogic.gdx.Input.Keys.LEFT),
    ARROW_RIGHT(com.badlogic.gdx.Input.Keys.RIGHT);

    val onPress = Registrar()
    val onRelease = Registrar()
    val onTick = Registrar()
    var isTicking = false

    companion object {

      fun findByKey(gdxKey: Int): Button? = Button.values().find { it.gdxKey == gdxKey }

      fun screenPosition() = Vector2(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())

      fun position() = Camera.unproject(screenPosition())

    }

  }

  object Scroll {

    val onScrollIn = Registrar()
    val onScrollOut = Registrar()

  }

  class Registrar {

    private val registers = HashSet<Event>()

    fun register(event: Event) = registers.add(event)

    fun unregister(event: Event) = registers.remove(event)

    fun performAllEvents() = registers.forEach { it.performEvent() }

  }

  abstract class Event {

    abstract fun performEvent()

    companion object {

      fun of(lam: () -> Unit): Event {
        val obj = object : Event() {

          override fun performEvent() = lam.invoke()

        }
        return obj
      }

    }

  }


}