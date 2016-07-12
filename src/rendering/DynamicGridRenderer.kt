package rendering

import commons.math.Vec2
import core.Camera


object DynamicGridRenderer {

  fun draw(camera: Camera) {
    val rect = camera.worldVisibilityRectangle(border = 128f)
    //Draw.rectangleDotted(rect, 8f * camera.renderingScale())


    val size = Vec2(rect.width, rect.height) / 2

    var e = 8f
    fun nextScale(): Float {
      e += e + e
      return e
    }

    val alpha = calcAlpha(0f, 1f, camera.renderingScale())
    grid(camera.positionEye(), size, alpha * .25f, nextScale())


    val alpha2 = calcAlpha(1f, 1f, camera.renderingScale())
    grid(camera.positionEye(), size, alpha2 * .25f, nextScale())

    val alpha3 = calcAlpha(2f, 1f, camera.renderingScale())
    grid(camera.positionEye(), size, alpha3 * .25f, nextScale())

     // todo do loop

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
    return (range - fixed) / range;
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