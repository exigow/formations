package actions

import com.badlogic.gdx.math.Vector2
import core.Camera
import core.input.EventRegistry
import core.input.mappings.ButtonState
import core.input.mappings.KeyboardButton
import java.util.*

class CameraArrowsMovementAction(private val cameraDep: Camera) : Action {

  private val keyVectorMappings = mapOf(
    KeyboardButton.ARROW_UP to Vector2(0f, 1f),
    KeyboardButton.ARROW_DOWN to Vector2(0f, -1f),
    KeyboardButton.ARROW_LEFT to Vector2(-1f, 0f),
    KeyboardButton.ARROW_RIGHT to Vector2(1f, 0f)
  ).mapValues { entry -> toEvent(entry.value) }

  private val ticks = toMutableMap(keyVectorMappings.keys.map { it to false}.toMap())

  private fun toEvent(vec: Vector2): () -> Unit {
    return {
      val movementFactor = 16f
      val result = Vector2(vec).scl(movementFactor)
      cameraDep.moveRelative(result)
    }
  }

  private fun <K, V> toMutableMap(source: Map<K, V>) : MutableMap<K, V> {
    val map = HashMap<K, V>()
    for (entry in source.entries)
      map.put(entry.key, entry.value)
    return map
  }

  override fun bind(registry: EventRegistry) {
    for (entry in keyVectorMappings) {
      registry.newEventOnKeyboard { button, state ->
        if (keyVectorMappings.containsKey(button)) {
          val isTick = stateToLock(state)
          ticks[button] = isTick
        }
      }
      registry.newEventOnTick {
        if (ticks[entry.key] == true)
          entry.value.invoke()
      }
    }
  }

  private fun stateToLock(state: ButtonState): Boolean = when (state) {
    ButtonState.PRESS -> true
    ButtonState.RELEASE -> false
  }

}