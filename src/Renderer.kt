import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Line
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import commons.math.ConvexHull
import commons.math.FastMath
import commons.math.Vec2
import core.Camera
import java.util.*

object Renderer {

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

  fun renderCross(position: Vec2, size: Float = 16f) {
    shape.begin(Line)
    shape.line(position.x - size, position.y, position.x + size, position.y)
    shape.line(position.x, position.y - size, position.x, position.y + size)
    shape.end()
  }

  fun renderLine(from: Vec2, to: Vec2) {
    shape.begin(Line)
    shape.line(from.x, from.y, to.x, to.y)
    shape.end()
  }

  fun renderLineDotted(from: Vec2, to: Vec2, dotLength: Float) {
    var passed = 0f
    val rotatedVector = Vec2.rotated(from.directionTo(to))
    while (passed < from.distanceTo(to)) {
      val alpha = rotatedVector * passed
      val delta = rotatedVector * (passed + dotLength)
      renderLine(from + alpha, from + delta)
      passed += dotLength * 2
    }
  }

  fun renderArc(where: Vec2, radius: Float, start: Float, end: Float, quality: Int = 64) {
    fun sample(angle: Float) = where + Vec2.rotated(angle) * radius
    var prev = sample(start)
    for (iteration in 1..quality) {
      val angle = start + (iteration.toFloat() / quality) * (end - start)
      val next = sample(angle)
      renderLine(prev, next)
      prev = next
    }
  }

  fun renderDiamond(where: Vec2, size: Float) {
    Renderer.renderCircle(where, size, 4)
  }

  fun renderConvexHull(vectors: List<Vec2>) {
    val out = ConvexHull.calculate(vectors)
    val iter = out.iterator()
    var prev = iter.next()
    while (iter.hasNext()) {
      val next = iter.next()
      Renderer.renderLine(prev, next)
      prev = next
    }
  }

  fun renderGrid() {
    shape.color.set(.25f, .25f, .25f, 1f)
    val max = 16
    val scale = 64f
    shape.begin(Line)
    for (x in -max..max)
      shape.line(x * scale, scale * -max, x * scale, scale * max)
    for (y in -max..max)
      shape.line(scale * -max, y * scale, scale * max, y * scale)
    shape.end()
    shape.color.set(Color.WHITE)
  }

  fun renderRectangle(rect: Rectangle) {
    shape.begin(Line)
    shape.rect(rect.x, rect.y, rect.width, rect.height)
    shape.end()
  }

  fun reset(camera: Camera) {
    shape.projectionMatrix.set(camera.projectionMatrix())
    clear()
  }

  private fun clear() {
    Gdx.gl.glClearColor(.125f, .125f, .125f, 1f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
  }

}