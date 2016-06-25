package commons.math


@Suppress("NOTHING_TO_INLINE")
object FastMath {

  val pi = Math.PI.toFloat()

  fun sqrt(x: Float) = Math.sqrt(x.toDouble()).toFloat()

  fun cos(x: Float) = Math.cos(x.toDouble()).toFloat()

  fun sin(x: Float) = Math.sin(x.toDouble()).toFloat()

  inline fun lerp(from: Float, to: Float, percent: Float) = (1 - percent) * from + percent * to

  fun atan2(x: Float, y: Float) = Math.atan2(y.toDouble(), x.toDouble()).toFloat()

  fun angleDifference(a: Float, b: Float) = (a - b + pi) % (pi * 2f) - pi

  fun pow(a: Float, b: Float) = Math.pow(a.toDouble(), b.toDouble()).toFloat()

  inline fun clamp(x: Float, min: Float, max: Float): Float {
    if (x > max)
      return max
    if (x < min)
      return min
    return x
  }

}