package rendering.shapes

import com.badlogic.gdx.math.Matrix4
import commons.math.Vec2
import rendering.Color

class PathRenderer {

  private val shape = com.badlogic.gdx.graphics.glutils.ShapeRenderer()

  fun render(path: Path, color: Color) {
    val i = path.elements.iterator()
    var prev = i.next()
    while (i.hasNext()) {
      val next = i.next()
      line(prev, next, color)
      prev = next
    }
  }

  private fun line(from: Vec2, to: Vec2, color: Color) {
    val lineMode = com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Line
    shape.setColor(color.r, color.g, color.b, 1f)
    shape.begin(lineMode)
    shape.line(from.x, from.y, to.x, to.y)
    shape.end()
  }

  fun update(matrix: Matrix4) {
    shape.projectionMatrix = matrix
  }

}