package rendering.oldtrails

import commons.math.Vec2

class OldTrailsBuffer(val capacity: Int) {

  val xBuffer = Array(capacity, {0f})
  val yBuffer = Array(capacity, {0f})
  val connectionFromBuffer = Array(capacity, {0})
  val connectionToBuffer = Array(capacity, {0})
  val connectionFromAngle = Array(capacity, {0f})
  val connectionToAngle = Array(capacity, {0f})
  val connectionFromAlpha = Array(capacity, {0f})
  val connectionToAlpha = Array(capacity, {0f})
  private val connectionEnabled = Array(capacity, {false})
  private var connectionPivot = 0
  private val usage = Array(capacity, {0})

  fun store(position: Vec2, index: Int) {
    xBuffer[index] = position.x
    yBuffer[index] = position.y
  }

  private fun increaseUsage() {
    for (index in 0..(usage.size - 1))
      usage[index] = usage[index] + 1
  }

  fun update(delta: Float) {
    for (index in 0..(usage.size - 1)) {
      val amount = delta * .75f
      connectionFromAlpha[index] -= amount
      connectionToAlpha[index] -= amount
      if (connectionFromAlpha[index] < 0 || connectionToAlpha[index] < 0) {
        connectionEnabled[index] = false
      }
    }
  }

  fun connect(from: Int, to: Int) {
    val index = requestConnection()
    connectionFromBuffer[index] = from
    connectionToBuffer[index] = to
    connectionEnabled[index] = true
  }

  fun requestPosition(): Int {
    increaseUsage()
    val found = fetchUseless()
    fixConnections(found)
    return found
  }

  private fun fixConnections(index: Int) {
    for (i in 0..(capacity - 1))
      if (connectionContainsPivot(i, index))
        connectionEnabled[i] = false
  }

  private fun connectionContainsPivot(index: Int, pivot: Int) = connectionFromBuffer[index] == pivot || connectionToBuffer[index] == pivot

  private fun fetchUseless(): Int {
    var retIndex = 0
    var max = usage[retIndex]
    for (index in 0..(usage.size - 1)) {
      val new = usage[index]
      if (new > max) {
        max = new
        retIndex = index
      }
    }
    usage[retIndex] = 0
    return retIndex
  }

  fun requestConnection(): Int {
    connectionPivot += 1
    if (connectionPivot > capacity - 1)
      connectionPivot = 0
    return connectionPivot
  }

  fun restore(index: Int) = Vec2(xBuffer[index], yBuffer[index])

  fun forEachPosition(f: (position: Vec2) -> Unit) {
    for (i in 0..(capacity - 1)) {
      val vector = restore(i)
      f.invoke(vector)
    }
  }

  fun forEachConnection(f: (from: Vec2, to: Vec2, fromAngle: Float, toAngle: Float, fromAlpha: Float, toAlpha: Float) -> Unit) {
    for (i in 0..(capacity - 1))
      if (connectionEnabled[i] == true) {
        val fromIndex = connectionFromBuffer[i]
        val toIndex = connectionToBuffer[i]
        val fromAngle = connectionFromAngle[fromIndex]
        val toAngle = connectionToAngle[toIndex]
        val fromIndexedAlpha = connectionFromAlpha[fromIndex]
        val toIndexedAlpha = connectionFromAlpha[toIndex]
        f.invoke(restore(fromIndex), restore(toIndex), fromAngle, toAngle, fromIndexedAlpha, toIndexedAlpha)
      }
  }

}