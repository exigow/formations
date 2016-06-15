package commons.math


object FastMath {

  fun sqrt(x: Float) = Math.sqrt(x.toDouble()).toFloat()

  fun cos(x: Float) = Math.cos(x.toDouble()).toFloat()

  fun sin(x: Float) = Math.sin(x.toDouble()).toFloat()

  fun lerp(from: Float, to: Float, percent: Float) = from * (1 - percent) + to * percent

  fun atan2(x: Float, y: Float) = Math.atan2(y.toDouble(), x.toDouble()).toFloat()

}