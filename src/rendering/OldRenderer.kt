package rendering

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Line
import com.badlogic.gdx.math.MathUtils
import commons.math.FastMath
import commons.math.Vec2
import java.util.*

object OldRenderer {

  private val shape = ShapeRenderer()

  fun renderCircle(position: Vec2, radius: Float = 1f, segments: Int = 8) {
    shape.begin(Line)
    shape.circle(position.x, position.y, radius, segments)
    shape.end()
  }

  fun renderDart(position: Vec2, scale: Float = 16f, angle: Float = 0f) {
    val vertices = arrayOf(
      Vec2(-1f, -.5f),
      Vec2(-.75f, 0f),
      Vec2(-1f, .5f),
      Vec2(1f, 0f)
    )
    renderPolygon(vertices, position, scale, angle)
  }

  fun renderLineArrow(from: Vec2, to: Vec2, scale: Float = 16f) {
    renderLine(from, to)
    val dir = from.directionTo(to)
    val a = FastMath.pi * .75f
    val l = scale
    renderLine(to, Vec2(to.x + FastMath.cos(dir + a) * l, to.y + FastMath.sin(dir + a) * l))
    renderLine(to, Vec2(to.x + FastMath.cos(dir - a) * l, to.y + FastMath.sin(dir - a) * l))
  }

  private fun renderPolygon(vectors: Array<Vec2>, position: Vec2, scale: Float, angle: Float) {
    shape.identity();
    shape.translate(position.x, position.y, 0f)
    shape.rotate(0f, 0f, 1f, angle * MathUtils.radiansToDegrees)
    shape.scale(scale, scale, scale)
    shape.begin(Line)
    shape.polygon(vectorsToVertices(vectors))
    shape.end()
    shape.identity();
  }

  private fun vectorsToVertices(vectors: Array<Vec2>): FloatArray {
    val list = ArrayList<Float>(vectors.size / 2)
    for (vector in vectors)
      list.addAll(listOf(vector.x, vector.y))
    return list.toFloatArray()
  }

  fun renderLine(from: Vec2, to: Vec2) {
    shape.begin(Line)
    shape.line(from.x, from.y, to.x, to.y)
    shape.end()
  }


}