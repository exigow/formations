package rendering.trails

import commons.math.FastMath
import commons.math.Vec2
import rendering.Draw

object TrailsDebugRenderer {

  fun render(buffer: TrailsBuffer) {
    buffer.forEachPosition { Draw.cross(it, 4f) }
    buffer.forEachConnection { from, to, fromAngle, toAngle, fromAlpha, toAlpha ->
      Draw.line(from, to)
      val fa = Vec2.rotated(fromAngle + FastMath.pi) * 16
      val ta = Vec2.rotated(toAngle + FastMath.pi) * 16
      Draw.line(from + fa, from - fa)
      Draw.line(to + ta, to - ta)
    }
  }
}