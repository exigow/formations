@Suppress("NOTHING_TO_INLINE")
object FastMath {

  val pi = Math.PI.toFloat()
  val pi2 = pi * 2

  fun sqrt(x: Float) = Math.sqrt(x.toDouble()).toFloat()

  fun cos(x: Float) = Math.cos(x.toDouble()).toFloat()

  // todo http://www.java-gaming.org/topics/fast-math-sin-cos-lookup-tables/24191/view.html
  fun sin(x: Float) = Math.sin(x.toDouble()).toFloat()

  inline fun lerp(from: Float, to: Float, percent: Float) = (1 - percent) * from + percent * to

  fun atan2(x: Float, y: Float) = Math.atan2(y.toDouble(), x.toDouble()).toFloat()

  fun angleDifference(a: Float, b: Float): Float {
    val d = Math.abs(a - b)
    val r: Float
    if (d > pi)
      r = pi2 - d
    else
      r = d
    val sign: Float
    if ((a - b >= 0 && a - b <= pi) || (a - b <=-pi && a- b>= -pi2))
      sign = 1f
    else
      sign = -1f
    return r * sign
  }

  fun loopAngle(angle: Float): Float {
    if (angle > pi)
      return -pi
    if (angle < -pi)
      return pi
    return angle
  }

  fun pow(a: Float, b: Float) = Math.pow(a.toDouble(), b.toDouble()).toFloat()

  inline fun clamp(x: Float, min: Float, max: Float): Float {
    if (x > max)
      return max
    if (x < min)
      return min
    return x
  }

  fun smoothStep(from: Float, to: Float, percent: Float): Float {
    val x = clamp((percent - from) / (to - from), 0f, 1f);
    return x * x * (3 - 2 * x)
  }

  // todo https://en.wikipedia.org/wiki/Smoothstep -> smootherstep
  fun smootherstep(from: Float, to: Float, percent: Float): Float = TODO()

}