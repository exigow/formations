package rendering.oldtrails

import commons.math.FastMath
import commons.math.Vec2

class OldTrailsEmitter(private val maxDistance: Float, private val buffer: OldTrailsBuffer, private val initialPosition: Vec2) {

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

  fun emit(position: Vec2, angle: Float) {
    updateNext(position)
    updateAngles(angle)
    if (isTooDistant())
      emitNew(position)
  }

  private fun updateNext(position: Vec2) {
    buffer.store(position, nextPivot)
    buffer.connectionToAlpha[nextPivot] = 1f
    buffer.connectionFromAlpha[nextPivot] = 1f
  }

  private fun isTooDistant() = buffer.restore(prevPivot).distanceTo(buffer.restore(nextPivot)) > maxDistance

  private fun emitNew(position: Vec2) {
    prevPivot = nextPivot
    nextPivot = requestAndStore(position)
    connectCurrent()
  }

  private fun updateAngles(sourceAngle: Float) {
    val head = calcDirection()
    buffer.connectionToAngle[nextPivot] = sourceAngle
    buffer.connectionFromAngle[nextPivot] = sourceAngle
    buffer.connectionToAngle[prevPivot] = head
    buffer.connectionFromAngle[prevPivot] = head
  }

  private fun connectCurrent() = buffer.connect(prevPivot, nextPivot)

  private fun calcDirection() = buffer.restore(prevPivot).directionTo(buffer.restore(nextPivot)) + FastMath.pi / 2

}