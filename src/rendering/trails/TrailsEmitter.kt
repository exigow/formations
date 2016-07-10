package rendering.trails

import commons.math.FastMath
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
    updateAngles()
    if (isTooDistant())
      emitNew(position)
  }

  private fun updateNext(position: Vec2) = buffer.store(position, nextPivot)

  private fun isTooDistant() = buffer.restore(prevPivot).distanceTo(buffer.restore(nextPivot)) > maxDistance

  private fun emitNew(position: Vec2) {
    buffer.connectionToAngle[prevPivot] = buffer.connectionToAngle[nextPivot]
    prevPivot = nextPivot
    nextPivot = requestAndStore(position)
    connectCurrent()
  }

  private fun updateAngles() {
    buffer.connectionFromAngle[nextPivot] = calcDirection()
    buffer.connectionToAngle[nextPivot] = buffer.connectionFromAngle[nextPivot]
    buffer.connectionToAngle[prevPivot] = buffer.connectionFromAngle[nextPivot]
  }

  private fun connectCurrent() = buffer.connect(prevPivot, nextPivot)

  private fun calcDirection() = buffer.restore(prevPivot).directionTo(buffer.restore(nextPivot)) + FastMath.pi / 2

}