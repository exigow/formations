package rendering.shapes

import commons.math.Vec2

object ShapeFactory {

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

  fun cross(): Shape {
    val up = Vec2(0, -1)
    val down = Vec2(0, 1)
    val left = Vec2(-1, 0)
    val right = Vec2(1, 0)
    val vertical = Path(listOf(up, down))
    val horizontal = Path(listOf(left, right))
    return Shape(listOf(vertical, horizontal))
  }

  private fun singleton(vararg elements: Vec2) = Shape.singleton(elements.asList())

}