package rendering

import commons.math.Vec2
import core.Camera


object DynamicGridRenderer {

  val epsilon = 32f

  fun draw(camera: Camera) {
    val rect = camera.worldVisibilityRectangle(border = -256f)
    Draw.rectangleDotted(rect, 8f * camera.renderingScale())
    val eye = camera.positionEye()
    val w = rect.width / 2
    val h = rect.height / 2
    asd(eye, w, { e, i ->
      line(Vec2(e.x - i, e.y - h), Vec2(e.x - i, e.y + h))
      if (i != 0f)
        line(Vec2(e.x + i, e.y - h), Vec2(e.x + i, e.y + h))
    })
    asd(eye, h, { e, i ->
      line(Vec2(e.x - w, e.y - i), Vec2(e.x + w, e.y - i))
      if (i != 0f)
        line(Vec2(e.x - w, e.y + i), Vec2(e.x + w, e.y + i))
    })

  }

  private fun line(from: Vec2, to: Vec2) {
    Draw.line(from, to, Color.WHITE, .25f)
  }

  private fun asd(eye: Vec2, max: Float, iteration: (e: Vec2, i: Float) -> Unit) {
    val mod = eye % epsilon
    var x = 0f
    do {
      val e = eye - mod
      iteration.invoke(e, x)
      x += epsilon
    } while (x < max)
  }

}