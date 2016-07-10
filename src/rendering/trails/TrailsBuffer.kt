package rendering.trails

import commons.math.Vec2

class TrailsBuffer {

  //private val DISABLED_CONNECTION_VALUE = -1
  val positionCapacity = 32
  val connectionCapacity = 256
  val xBuffer = Array(positionCapacity, {i -> 0f})
  val yBuffer = Array(positionCapacity, {i -> 0f})
  val connectionFromBuffer = Array(connectionCapacity, {i -> 0 })
  val connectionToBuffer = Array(connectionCapacity, {i -> 0 })
  private var positionPivot = 0
  private var connectionPivot = 0

  fun store(position: Vec2, index: Int) {
    xBuffer[index] = position.x
    yBuffer[index] = position.y
  }

  fun connect(from: Int, to: Int) {
    val index = requestConnection()
    connectionFromBuffer[index] = from
    connectionToBuffer[index] = to
  }

  fun requestPosition(): Int {
    positionPivot += 1
    if (positionPivot > positionCapacity - 1)
      positionPivot = 0
    return positionPivot
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

  fun forEachConnection(f: (from: Vec2, to: Vec2) -> Unit) {
    for (i in 0..(connectionCapacity - 1)) {
      val fromIndex = connectionFromBuffer[i]
      val toIndex = connectionToBuffer[i]
      f.invoke(restore(fromIndex), restore(toIndex))
    }
  }

}