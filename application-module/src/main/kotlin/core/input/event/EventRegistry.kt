package core.input.event

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import core.input.mappings.ButtonState
import core.input.mappings.KeyboardButton
import core.input.mappings.MouseButton
import core.input.mappings.ScrollDirection
import java.util.*


class EventRegistry {

  init {
    overrideGdxInputProcessor()
  }

  private fun overrideGdxInputProcessor() {
    Gdx.input.inputProcessor = GdxInputWrapper
  }

  fun registerBundle(bundle: EventBundle) {
    keyboardEvents += bundle.onKeyboard()
    mouseEvents += bundle.onMouse()
    scrollEvents += bundle.onMouseScroll()
    tickEvents += bundle.onTick()
  }

  fun clearRegister() = listOf(keyboardEvents, mouseEvents, scrollEvents, tickEvents).forEach { it.clear() }

  fun updatePressingTicks(delta: Float) {
    tickEvents.forEach { it.invoke(delta) }
  }

  private companion object GdxInputWrapper : InputAdapter() {

    val keyboardEvents: MutableSet<(button: KeyboardButton, state: ButtonState) -> Unit> = HashSet()
    val mouseEvents: MutableSet<(button: MouseButton, state: ButtonState) -> Unit> = HashSet()
    val scrollEvents: MutableSet<(ScrollDirection) -> Unit> = HashSet()
    val tickEvents: MutableSet<(delta: Float) -> Unit> = HashSet()

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, key: Int) = performTouch(key, ButtonState.RELEASE)

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, key: Int) = performTouch(key, ButtonState.PRESS)

    override fun keyUp(keycode: Int) = performKey(keycode, ButtonState.RELEASE)

    override fun keyDown(keycode: Int) = performKey(keycode, ButtonState.PRESS)

    override fun scrolled(amount: Int): Boolean {
      val direction = amountToDirection(amount)
      scrollEvents.forEach { it.invoke(direction) }
      return true
    }

    private fun performTouch(key: Int, state: ButtonState): Boolean {
      val button = findMouseButtonByKey(key)!!
      mouseEvents.forEach { it.invoke(button, state) }
      return true
    }

    private fun performKey(key: Int, state: ButtonState): Boolean {
      val button = findKeyboardButtonByKey(key) ?: return false
      keyboardEvents.forEach { it.invoke(button, state) }
      return true
    }

    private fun amountToDirection(amount: Int): ScrollDirection = when (amount) {
      1 -> ScrollDirection.SCROLLING_UP
      -1 -> ScrollDirection.SCROLLING_DOWN
      else -> throw RuntimeException("Unsupported scroll amount: $amount")
    }

    private fun findKeyboardButtonByKey(key: Int) = KeyboardButton.values().find { it.gdxKey == key }

    private fun findMouseButtonByKey(key: Int) = MouseButton.values().find { it.gdxKey == key }

  }

}