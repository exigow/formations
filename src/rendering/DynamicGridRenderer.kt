package rendering

import commons.math.FastMath
import commons.math.Vec2
import core.Camera


object DynamicGridRenderer {

  fun draw(camera: Camera) {
    val rect = camera.worldVisibilityRectangle(border = 128f)
    Draw.rectangleDotted(rect, 8f * camera.renderingScale())
    val size = Vec2(rect.width, rect.height) / 2
    var epsilon = 64f
    var pivot = 1f
    var range = .5f
    for (i in 1..5) {
      val alpha = calcAlpha(pivot, range, camera.renderingScale())
      if (alpha >= .0125f)
        grid(camera.positionEye(), size, alpha * .25f, epsilon)
      epsilon *= 2
      pivot *= 2
      range *= 2
    }
  }

  private fun grid(eye: Vec2, size: Vec2, alpha: Float, epsilon: Float) {
    fun line(from: Vec2, to: Vec2) = Draw.line(from, to, Color.WHITE, alpha)
    asd(epsilon, eye, size.x, { e, i ->
      line(Vec2(e.x - i, e.y - size.y), Vec2(e.x - i, e.y + size.y))
      if (i != 0f)
        line(Vec2(e.x + i, e.y - size.y), Vec2(e.x + i, e.y + size.y))
    })
    asd(epsilon, eye, size.y, { e, i ->
      line(Vec2(e.x - size.x, e.y - i), Vec2(e.x + size.x, e.y - i))
      if (i != 0f)
        line(Vec2(e.x - size.x, e.y + i), Vec2(e.x + size.x, e.y + i))
    })
  }

  private fun calcAlpha(pivot: Float, range: Float, zoom: Float): Float {
    val dist = Math.abs(zoom - pivot)
    val fixed = Math.min(dist, range)
    val linear = (range - fixed) / range;
    return FastMath.smoothStep(0f, 1f, linear);
  }

  private fun asd(epsilon: Float, eye: Vec2, max: Float, iteration: (e: Vec2, i: Float) -> Unit) {
    val mod = eye % epsilon
    var x = 0f
    do {
      val e = eye - mod
      iteration.invoke(e, x)
      x += epsilon
    } while (x < max)
  }

}