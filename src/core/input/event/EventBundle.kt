package core.input.event

import core.input.mappings.ButtonState
import core.input.mappings.KeyboardButton
import core.input.mappings.MouseButton
import core.input.mappings.ScrollDirection


interface EventBundle {

  fun onTick(): (delta: Float) -> Unit = {}

  fun onMouseScroll(): (delta: ScrollDirection) -> Unit = {}

  fun onKeyboard(): (button: KeyboardButton, state: ButtonState) -> Unit = { button, state -> }

  fun onMouse(): (button: MouseButton, state: ButtonState) -> Unit = { button, state -> }

}