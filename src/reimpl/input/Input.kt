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
    Mouse.values().filter { it.isTicking }
      .forEach {
        it.onTickRegistrar.forEach {
          it.performEvent()
        }
      }
  }

  private object InputWrapper : InputAdapter() {

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, key: Int): Boolean {
      val button = Mouse.findByKey(key) ?: return false
      button.isTicking = false
      button.onReleaseRegistrar.forEach { it.performEvent() }
      return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, key: Int): Boolean {
      val button = Mouse.findByKey(key) ?: return false
      button.isTicking = true
      button.onPressRegistrar.forEach { it.performEvent() }
      return true
    }

  }

  enum class Mouse(private val gdxKey: Int) {

    LEFT(0),
    RIGHT(1),
    MIDDLE(2);

    val onPressRegistrar = HashSet<Event>() // todo wez to jakos zprywatyzuj
    val onReleaseRegistrar = HashSet<Event>() // todo wez to jakos zprywatyzuj
    val onTickRegistrar = HashSet<Event>() // todo wez to jakos zprywatyzuj
    var isTicking = false // todo wez to jakos zprywatyzuj

    fun registerOnPress(event: Event) = onPressRegistrar.add(event)

    fun registerOnRelease(event: Event) = onReleaseRegistrar.add(event)

    fun registerOnPressedTick(event: Event) = onTickRegistrar.add(event)

    fun unregisterOnPress(event: Event) = onPressRegistrar.remove(event)

    fun unregisterOnRelease(event: Event) = onReleaseRegistrar.remove(event)

    fun unregisterOnPressedTick(event: Event) = onTickRegistrar.remove(event)

    // todo unregister method for unbind action feature

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

  abstract  class Event {

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