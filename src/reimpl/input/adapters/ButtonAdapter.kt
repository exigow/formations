package input.adapters

abstract class ButtonAdapter(val key: Int) {

  private enum class State {
    WAIT, PRESS, HOLD, RELEASE
  }

  private var actualStep = State.WAIT;
  private var changed = false;

  abstract fun getSignal(): Boolean

  fun update() {
    val source = getSignal()
    changed = false
    if (source) {
      if (actualStep == State.PRESS)
        changeTo(State.HOLD)
      if (actualStep == State.WAIT)
        changeTo(State.PRESS)
    } else {
      if (actualStep == State.RELEASE)
        changeTo(State.WAIT)
      if (actualStep == State.HOLD)
        changeTo(State.RELEASE)
    }
  }

  private fun changeTo(state: State) {
    actualStep = state
    changed = true
  }

  fun isPressed() = actualStep == State.PRESS

  fun isHeld() = actualStep == State.HOLD

  fun isReleased() = actualStep == State.RELEASE

}