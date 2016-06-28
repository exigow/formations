package rendering

import com.badlogic.gdx.math.Rectangle
import commons.math.FastMath
import commons.math.Vec2
import core.Camera
import rendering.shapes.ShapeFactory
import rendering.shapes.ShapeRenderer

object Draw {

  private val defaultColor = Color.WHITE
  private val renderer = ShapeRenderer()

  fun update(camera: Camera) = renderer.update(camera.projectionMatrix())

  fun line(from: Vec2, to: Vec2, color: Color = defaultColor) = renderer.render(ShapeFactory.line(from, to), color)

  fun lineDotted(from: Vec2, to: Vec2, dotLength: Float, color: Color = defaultColor) {
    var passed = 0f
    val rotatedVector = Vec2.rotated(from.directionTo(to))
    val dist = from.distanceTo(to)
    while (passed < dist) {
      val alpha = rotatedVector * passed
      val delta = rotatedVector * Math.min((passed + dotLength), dist)
      line(from + alpha, from + delta, color)
      passed += dotLength * 2
    }
  }

  fun cross(center: Vec2, size: Float, color: Color = defaultColor) {
    line(center - Vec2(size, 0f), center + Vec2(size, 0f), color)
    line(center - Vec2(0f, size), center + Vec2(0f, size), color)
  }

  fun grid(center: Vec2 = Vec2.zero(), size: Vec2, density: Int, color: Color = defaultColor) {
    for (d in 0..density) {
      val texel = d / density.toFloat()
      val x = FastMath.lerp(-size.x, size.x, texel)
      val y = FastMath.lerp(-size.y, size.y, texel)
      line(center + Vec2(x, -size.y), center + Vec2(x, size.y), color)
      line(center + Vec2(-size.x, y), center + Vec2(size.x, y), color)
    }
  }

  fun arc(center: Vec2, radius: Float, start: Float, end: Float, quality: Int = 8, color: Color = defaultColor) {
    fun sample(angle: Float) = center + Vec2.rotated(angle) * radius
    var prev = sample(start)
    for (iteration in 1..quality) {
      val angle = start + (iteration.toFloat() / quality) * (end - start)
      val next = sample(angle)
      line(prev, next, color)
      prev = next
    }
  }

  fun rectangle(rect: Rectangle, color: Color = defaultColor) {
    val leftUp = Vec2(rect.x, rect.y)
    val rightUp = leftUp + Vec2(rect.width, 0)
    val leftDown = leftUp + Vec2(0, rect.height)
    val rightDown = leftUp + Vec2(rect.width, rect.height)
    val sequence = arrayOf(leftUp, rightUp, rightDown, leftDown)
    fromEachToEachLooped(sequence, { from, to -> lineDotted(from, to, 8f, color)})
  }

  fun cone(center: Vec2, radius: Float, start: Float, end: Float, quality: Int = 8, color: Color = defaultColor) {
    arc(center, radius, start, end, quality, color)
    fun angledLine(angle: Float) = line(center, center + Vec2.rotated(angle) * radius, color)
    angledLine(start)
    angledLine(end)
  }

  fun diamond(center: Vec2, scale: Float, color: Color = defaultColor) = diamond(center, Vec2(scale, scale), color)

  fun diamond(center: Vec2, scale: Vec2, color: Color = defaultColor) {
    val up = center - scale.onlyY()
    val down = center + scale.onlyY()
    val left = center - scale.onlyX()
    val right = center + scale.onlyX()
    val sequence = arrayOf(up, right, down, left)
    polygonLooped(sequence, color)
  }

  fun polygonLooped(positions: Array<Vec2>, color: Color = defaultColor) = fromEachToEachLooped(positions, { from, to -> line(from, to, color)})

  inline private fun fromEachToEachLooped(positions: Array<Vec2>, drawWith: (from: Vec2, to: Vec2) -> Unit) {
    val i = positions.iterator()
    var prev = i.next()
    val first = prev
    while (i.hasNext()) {
      val next = i.next()
      drawWith.invoke(prev, next)
      prev = next
    }
    drawWith.invoke(first, prev)
  }

}