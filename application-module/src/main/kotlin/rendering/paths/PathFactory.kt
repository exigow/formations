package rendering.Paths

import FastMath
import Vec2
import rendering.paths.Path

object PathFactory {

  fun line(from: Vec2, to: Vec2) = Path.of(from, to)

  fun square() = Path.of(Vec2.zero(), Vec2.one().onlyX(), Vec2.one(), Vec2.one().onlyY(), Vec2.zero())

  fun diamond(): Path {
    val c = Vec2.zero()
    val o = Vec2.one()
    val up = c - o.onlyY()
    val down = c + o.onlyY()
    val left = c - o.onlyX()
    val right = c + o.onlyX()
    return Path.of(up, right, down, left, up)
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

  fun grid(density: Int) = (0..density).map {
    val texel = it / density.toFloat()
    val vertical = Path.of(Vec2(texel, 0f), Vec2(texel, 1f))
    val horizontal = Path.of(Vec2(0f, texel), Vec2(1f, texel))
    listOf(vertical, horizontal)
  }.flatMap { it }

  fun circle(quality: Int) = arc(0f, FastMath.pi * 2, quality)

  fun arc(start: Float, end: Float, quality: Int) = Path(generateArc(start, end, quality))

  fun cone(start: Float, end: Float, quality: Int): Path {
    val startVec = Vec2.rotated(start)
    val endVec = Vec2.rotated(end)
    val arc = generateArc(start, end, quality)
    val zero = listOf(Vec2.zero())
    val sum = zero + listOf(startVec) + arc + listOf(endVec) + zero
    return Path(sum)
  }

  fun dart() = Path.of(
    Vec2(-.5f, 0),
    Vec2(-1, .5f),
    Vec2(1, 0),
    Vec2(-1, -.5f),
    Vec2(-.5f, 0)
  )

  private fun generateArc(start: Float, end: Float, quality: Int) = (0..quality).map {
    val sample = it / quality.toFloat()
    val angle = FastMath.lerp(start, end, sample)
    Vec2.rotated(angle)
  }


}