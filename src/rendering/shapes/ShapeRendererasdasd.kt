package rendering.shapes

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4
import commons.math.Vec2
import rendering.Color

class ShapeRendererasdasd {

  private val shape = ShapeRenderer()

  fun render(shape: Shape, color: Color) {
    for (path in shape.paths) {
      val i = path.elements.iterator()
      var prev = i.next()
      while (i.hasNext()) {
        val next = i.next()
        line(prev, next, color)
        prev = next
      }
    }
  }

  private fun line(from: Vec2, to: Vec2, color: Color) {
    shape.setColor(color.r, color.g, color.b, 1f)
    shape.begin(ShapeRenderer.ShapeType.Line)
    shape.line(from.x, from.y, to.x, to.y)
    shape.end()
  }

  fun update(matrix: Matrix4) {
    shape.projectionMatrix = matrix
  }

}