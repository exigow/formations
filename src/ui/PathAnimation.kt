package ui

import commons.math.Vec2
import rendering.Renderer

object PathAnimation {

  fun render(lines: Map<Vec2, Vec2>, step: Float, divided: Boolean = true) {
    val epsilon = .0125f
    if (step > 2 - epsilon || step < 0 + epsilon)
      return
    for ((from, to) in lines) {
      if (divided) {
        val center = (from + to) / 2
        line(center, from, step)
        line(center, to, step)
      } else
        line(from, to, step)
    }
  }

  private fun line(from: Vec2, to: Vec2, amount: Float) {
    fun lazy(x: Float) = Vec2.Calculations.lerp(from, to, x)
    val a = lazy(Math.min(amount, 1f))
    val b = lazy(Math.max(amount - 1, 0f))
    Renderer.renderLine(a, b)
  }

}