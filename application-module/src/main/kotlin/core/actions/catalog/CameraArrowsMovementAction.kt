package core.actions.catalog

import Vec2
import core.Camera
import core.actions.Action
import core.input.event.EventBundle
import core.input.mappings.ButtonState
import core.input.mappings.KeyboardButton
import java.util.*

class CameraArrowsMovementAction(private val cameraDep: Camera) : Action {

  private val keyVectorMappings = mapOf(
    KeyboardButton.ARROW_UP to Vec2(0f, 1f),
    KeyboardButton.ARROW_DOWN to Vec2(0f, -1f),
    KeyboardButton.ARROW_LEFT to Vec2(-1f, 0f),
    KeyboardButton.ARROW_RIGHT to Vec2(1f, 0f)
  ).mapValues { entry -> toEvent(entry.value) }
  private val ticks = toMutableMap(keyVectorMappings.keys.map { it to false}.toMap())
  private val events = object : EventBundle {

    override fun onKeyboard(): (KeyboardButton, ButtonState) -> Unit = {
      button, state ->
      for (entry in keyVectorMappings) {
        if (keyVectorMappings.containsKey(button)) {
          val isTick = stateToLock(state)
          ticks[button] = isTick
        }
      }
    }

    override fun onTick(): (Float) -> Unit = {
      for (entry in keyVectorMappings) {
        if (ticks[entry.key] == true)
          entry.value.invoke()
      }
    }

  }

  override fun events() = events

  private fun toEvent(vec: Vec2): () -> Unit {
    return {
      val result = vec * 8f * cameraDep.renderingScale()
      cameraDep.moveRelative(result)
    }
  }

  private fun <K, V> toMutableMap(source: Map<K, V>) : MutableMap<K, V> {
    val map = HashMap<K, V>()
    for (entry in source.entries)
      map.put(entry.key, entry.value)
    return map
  }

  private fun stateToLock(state: ButtonState): Boolean = when (state) {
    ButtonState.PRESS -> true
    ButtonState.RELEASE -> false
  }

  override fun discardOn() = setOf(CameraMiddleClickMovementAction::class)

}