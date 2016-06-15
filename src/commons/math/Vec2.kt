package commons.math

data class Vec2(val x: Float, val y: Float) {

  operator fun plus(other: Vec2) = copy(x + other.x, y + other.y)

  operator fun minus(other: Vec2) = copy(x - other.x, y - other.y)

  operator fun times(other: Vec2) = copy(x * other.x, y * other.y)

  operator fun times(multiplier: Float) = copy(x * multiplier, y * multiplier)

  operator fun div(other: Vec2) = copy(x / other.x, y / other.y)

  operator fun div(divider: Float) = copy(x / divider, y / divider)

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

  companion object {

    @JvmStatic fun zero() = Vec2(0f, 0f)

    @JvmStatic fun one() = Vec2(1f, 1f)

    //@JvmStatic fun random() = Vec2(1f, 1f) // todo

  }

}