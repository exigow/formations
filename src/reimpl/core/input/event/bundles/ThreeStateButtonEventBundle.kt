package core.input.event.bundles

import core.input.event.EventBundle
import core.input.mappings.ButtonState
import core.input.mappings.MouseButton

abstract class ThreeStateButtonEventBundle(private val button: MouseButton) {

  private var isBetween = false

  abstract fun onPress()

  abstract fun onRelease()

  abstract fun onHold(delta: Float)

  fun toBundle() = object : EventBundle {

    override fun onMouse(): (MouseButton, ButtonState) -> Unit = {
      eventButton, state ->
      if (button == eventButton)
        when (state) {
          ButtonState.PRESS -> {
            isBetween = true
            onPress()
          }
          ButtonState.RELEASE -> {
            isBetween = false
            onRelease()
          }
        }
    }

    override fun onTick(): (Float) -> Unit = {
      delta ->
      if (isBetween)
        onHold(delta)
    }

  }

}