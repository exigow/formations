package ui

import commons.math.FastMath
import commons.math.Vec2

object SelectionAnimation {

  fun render(where: Vec2, step: Float) {
    val size = 32f
    for (i in 0..4) {
      val deg = i * FastMath.pi / 2f
      val q = FastMath.pi / 2f
      val from = where + Vec2(size * FastMath.cos(deg), size * FastMath.sin(deg))
      val to = where + Vec2(size * FastMath.cos(deg + q), size * FastMath.sin(deg + q))
      val center = (from + to) / 2
      line(center, from, step)
      line(center, to, step)
    }
  }

  private fun line(from: Vec2, to: Vec2, amount: Float) {
    fun lazy(x: Float) = Vec2.Calculations.lerp(from, to, x)
    val a = lazy(Math.min(amount, 1f))
    val b = lazy(Math.max(amount - 1, 0f))
    Renderer.renderLine(a, b)
  }

}