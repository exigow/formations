package rendering.shapes

import commons.math.FastMath
import commons.math.Vec2

object ShapeFactory {

  fun rectangle(size: Vec2) = square().map { it.scale(size) }

  fun square(): List<Path> {
    val a = Vec2.zero()
    val b = Vec2.one().onlyX()
    val c = Vec2.one()
    val d = Vec2.one().onlyY()
    return Path.asListOf(a, b, c, d, a)
  }

  fun diamond(size: Vec2) = diamond().map { it.scale(size) }

  fun diamond(): List<Path> {
    val c = Vec2.zero()
    val o = Vec2.one()
    val up = c - o.onlyY()
    val down = c + o.onlyY()
    val left = c - o.onlyX()
    val right = c + o.onlyX()
    return Path.asListOf(up, right, down, left, up)
  }

  fun cross(): List<Path> {
    val up = Vec2(0, -1)
    val down = Vec2(0, 1)
    val left = Vec2(-1, 0)
    val right = Vec2(1, 0)
    val vertical = Path(listOf(up, down))
    val horizontal = Path(listOf(left, right))
    return listOf(vertical, horizontal)
  }

  fun circle(quality: Int) = arc(0f, FastMath.pi * 2, quality)

  fun arc(start: Float, end: Float, quality: Int): List<Path> {
    val path = Path(generateArc(start, end, quality))
    return listOf(path)
  }

  fun cone(start: Float, end: Float, quality: Int): List<Path> {
    val startVec = Vec2.rotated(start)
    val endVec = Vec2.rotated(end)
    val arc = generateArc(start, end, quality)
    val zero = listOf(Vec2.zero())
    val sum = zero + listOf(startVec) + arc + listOf(endVec) + zero
    return listOf(Path(sum))
  }

  private fun generateArc(start: Float, end: Float, quality: Int) = (0..quality).map {
    val sample = it / quality.toFloat()
    val angle = FastMath.lerp(start, end, sample)
    Vec2.rotated(angle)
  }


}