package rendering.shapes

import commons.math.Vec2

object ShapesFactory {

  fun rectangle(size: Vec2) = square().scale(size)

  fun square(): Shape {
    val a = Vec2.zero()
    val b = Vec2.one().onlyX()
    val c = Vec2.one()
    val d = Vec2.one().onlyY()
    return singleton(a, b, c, d, a)
  }

  fun diamond(size: Vec2) = diamond().scale(size)

  fun diamond(): Shape {
    val c = Vec2.zero()
    val o = Vec2.one()
    val up = c - o.onlyY()
    val down = c + o.onlyY()
    val left = c - o.onlyX()
    val right = c + o.onlyX()
    return singleton(up, right, down, left, up)
  }

  private fun singleton(vararg elements: Vec2) = Shape.singleton(Path(elements.asList()))

}