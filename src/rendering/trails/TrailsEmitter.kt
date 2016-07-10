package rendering.trails

import commons.math.Vec2

class TrailsEmitter(private val maxDistance: Float, private val buffer: TrailsBuffer, private val initialPosition: Vec2) {

  private var prevPivot = requestAndStore(initialPosition)
  private var nextPivot = requestAndStore(initialPosition)

  init {
    connectCurrent()
  }

  private fun requestAndStore(position: Vec2): Int {
    val index = buffer.requestPosition()
    buffer.store(position, index)
    return index
  }

  fun emit(position: Vec2) {
    updateNext(position)
    if (isTooDistant())
      emitNew(position)
  }

  private fun updateNext(position: Vec2) = buffer.store(position, nextPivot)

  private fun isTooDistant() = buffer.restore(prevPivot).distanceTo(buffer.restore(nextPivot)) > maxDistance

  private fun emitNew(position: Vec2) {
    prevPivot = nextPivot
    nextPivot = requestAndStore(position)
    connectCurrent()
  }

  private fun connectCurrent() = buffer.connect(prevPivot, nextPivot)

}