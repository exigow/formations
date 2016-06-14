package core.actions

import core.input.event.EventBundle
import core.input.event.EventRegistry
import core.input.mappings.ButtonState
import core.input.mappings.KeyboardButton
import core.input.mappings.MouseButton
import core.input.mappings.ScrollDirection
import java.util.*
import kotlin.reflect.KClass


class ActionsRegistry {

  private val eventRegistry = EventRegistry()

  fun addAction(action: Action) {
    actions.add(action)
    val wrappedEvents = wrapWithLock(action, lock = {
      originalFun ->
      if (!hasWorkingConflicts(action))
        originalFun.invoke()
    })
    eventRegistry.registerBundle(wrappedEvents)
  }

  fun update(delta: Float) = eventRegistry.updatePressingTicks(delta)

  private fun wrapWithLock(action: Action, lock: (a: () -> Unit) -> Unit): EventBundle {
    val bundle = action.events()
    return object : EventBundle {

      override fun onTick(): (Float) -> Unit = {
        delta -> lock { bundle.onTick().invoke(delta) }
      }

      override fun onMouseScroll(): (ScrollDirection) -> Unit = {
        direction -> lock { bundle.onMouseScroll().invoke(direction) }
      }

      override fun onKeyboard(): (KeyboardButton, ButtonState) -> Unit = {
        button, state -> lock { bundle.onKeyboard().invoke(button, state) }
      }

      override fun onMouse(): (MouseButton, ButtonState) -> Unit = {
        button, state -> lock { bundle.onMouse().invoke(button, state) }
      }

    }
  }

  private val actions = ArrayList<Action>()

  private fun hasWorkingConflicts(action: Action): Boolean {
    if (action.discardOn().isEmpty())
      return false
    for (conflict in action.discardOn()) {
      val instance = findInstanceOfKClass(conflict)
      if (instance.isWorking())
        return true
    }
    return false
  }

  fun findInstanceOfKClass(clazz: KClass<*>): Action {
    for (action in actions)
      if (action.javaClass.kotlin == clazz)
        return action
    throw RuntimeException("Action instance (class: $clazz) not found in registry")
  }

}