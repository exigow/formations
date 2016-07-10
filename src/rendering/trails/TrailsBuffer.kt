package rendering.trails

import commons.math.Vec2

class TrailsBuffer(val positionCapacity: Int, val connectionCapacity: Int) {

  val xBuffer = Array(positionCapacity, {0f})
  val yBuffer = Array(positionCapacity, {0f})
  val connectionFromBuffer = Array(connectionCapacity, {0})
  val connectionToBuffer = Array(connectionCapacity, {0})
  val connectionFromAngle = Array(connectionCapacity, {0f})
  val connectionToAngle = Array(connectionCapacity, {0f})
  private val connectionEnabled = Array(connectionCapacity, {false})
  private var connectionPivot = 0
  private val usage = Array(positionCapacity, {0})

  fun store(position: Vec2, index: Int) {
    xBuffer[index] = position.x
    yBuffer[index] = position.y
  }
  private fun increaseUsage() {
    for (index in 0..(usage.size - 1))
      usage[index] = usage[index] + 1
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
    for (i in 0..(connectionCapacity - 1))
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
    if (connectionPivot > connectionCapacity - 1)
      connectionPivot = 0
    return connectionPivot
  }

  fun restore(index: Int) = Vec2(xBuffer[index], yBuffer[index])

  fun forEachPosition(f: (position: Vec2) -> Unit) {
    for (i in 0..(positionCapacity - 1)) {
      val vector = restore(i)
      f.invoke(vector)
    }
  }

  fun forEachConnection(f: (from: Vec2, to: Vec2, fromAngle: Float, toAngle: Float) -> Unit) {
    for (i in 0..(connectionCapacity - 1))
      if (connectionEnabled[i] == true) {
        val fromIndex = connectionFromBuffer[i]
        val toIndex = connectionToBuffer[i]
        val fromAngle = connectionFromAngle[i]
        val toAngle = connectionToAngle[i]
        f.invoke(restore(fromIndex), restore(toIndex), fromAngle, toAngle)
      }
  }

}