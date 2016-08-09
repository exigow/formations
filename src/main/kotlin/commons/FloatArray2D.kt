package commons

import commons.math.Vec2
import java.util.*


class FloatArray2D(val width: Int, val height: Int) {

  private val array: Array<FloatArray> = Array(width, {FloatArray(height, { i -> 0f})})

  operator fun get(x: Int, y: Int): Float {
    return array[x][y]
  }

  operator fun set(x: Int, y: Int, t: Float) {
    array[x][y] = t
  }

  fun forEach(operation: (Float) -> Unit) = array.forEach {
    it.forEach {
      operation.invoke(it)
    }
  }

  fun forEachIndexed(operation: (x: Int, y: Int, Float) -> Unit) = array.forEachIndexed {
    x, p -> p.forEachIndexed {
      y, t -> operation.invoke(x, y, t)
    }
  }

  fun toSetOfValuePoints(): Array<ValuePoint> {
    val result = ArrayList<ValuePoint>()
    forEachIndexed { x, y, value -> result += ValuePoint(x, y, value) }
    return result.toTypedArray()
  }

  data class ValuePoint(val x: Int, val y: Int, val value: Float) {

    fun scalePosition(scale: Int) = copy(x * scale, y * scale, value)

    fun translatePosition(tx: Int, ty: Int) = copy(x + tx, y + ty, value)

    fun toVec2() = Vec2(x, y)

    fun toSeed() = (x * y) + x

  }

}
