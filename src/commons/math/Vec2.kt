package commons.math

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2

data class Vec2(val x: Float, val y: Float) {

  constructor(x: Int, y: Int) : this(x.toFloat(), y.toFloat())
  constructor(x: Float, y: Int) : this(x, y.toFloat())
  constructor(x: Int, y: Float) : this(x.toFloat(), y)

  operator fun plus(other: Vec2) = copy(x + other.x, y + other.y)

  operator fun minus(other: Vec2) = copy(x - other.x, y - other.y)

  operator fun times(other: Vec2) = copy(x * other.x, y * other.y)

  operator fun times(multiplier: Float) = copy(x * multiplier, y * multiplier)

  operator fun times(multiplier: Int) = times(multiplier.toFloat())

  operator fun div(other: Vec2) = copy(x / other.x, y / other.y)

  operator fun div(divider: Float) = copy(x / divider, y / divider)

  operator fun div(divider: Int) = div(divider.toFloat())

  fun length() = FastMath.sqrt(x * x + y * y)

  fun normalize(): Vec2 {
    val l = length()
    if (l != 0f)
      return copy(x / l, y / l)
    return zero()
  }

  fun distanceTo(other: Vec2) = (this - other).length()

  fun angleInRadians() = FastMath.atan2(y, x)

  fun isZero() = x == 0f && y == 0f

  fun toVector2(): Vector2 = Vector2(x, y)

  companion object {

    @JvmStatic fun zero() = Vec2(0f, 0f)

    @JvmStatic fun one() = Vec2(1f, 1f)

    @JvmStatic fun random(): Vec2 {
      fun rand() = MathUtils.random(-1f, 1f)
      return Vec2(rand(), rand())
    }

  }

  object Calculations {

    fun average(list: List<Vec2>): Vec2 {
      val iterator = list.iterator()
      val first = iterator.next()
      var result = first
      while (iterator.hasNext())
        result += iterator.next()
      return result / list.size.toFloat()
    }

    fun closest(list: List<Vec2>, around: Vec2): Vec2 {
      val iterator = list.iterator()
      val first = iterator.next()
      var result = first
      fun calcDist(to: Vec2) = to.distanceTo(around)
      var distance = calcDist(result)
      while (iterator.hasNext()) {
        val next = iterator.next()
        val newDistance = calcDist(next)
        if (newDistance < distance) {
          distance = newDistance
          result = next
        }
      }
      return result
    }

  }

}