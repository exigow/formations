package ui


class AnimationSequence {

  private var timer: Float = 0f
  private var step: Float = 0f
  private var state: State = State.IN
  private var changeTo: State = State.IN

  fun show() {
    changeTo = AnimationSequence.State.VISIBLE
  }

  fun hide() {
    changeTo = AnimationSequence.State.IN
  }

  fun update(delta: Float) {
    timer += delta
    if (canChange())
      goToNextState()
    step += (state.target - step) * .375f
  }

  fun animaionStep() = step

  private fun goToNextState() {
    timer = 0f
    val map = mapOf(
      State.IN to State.VISIBLE,
      State.VISIBLE to State.OUT,
      State.OUT to State.IN
    )
    if (state == State.OUT)
      step = 0f
    state = map[state]!!
  }

  private fun canChange(): Boolean {
    if (state != changeTo && timer > .1f)
      return true
    return false
  }

  private enum class State(val target: Float) {

    IN(0f),
    VISIBLE(1f),
    OUT(2f)

  }

}