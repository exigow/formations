package rendering

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4
import commons.math.Vec2

class LineRenderer {

  private val shape = ShapeRenderer()

  fun emitLine(from: Vec2, to: Vec2, color: Color) {
    shape.setColor(color.r, color.g, color.b, 1f)
    shape.line(from.x, from.y, to.x, to.y)
  }

  fun begin(matrix: Matrix4) {
    shape.projectionMatrix = matrix
    shape.begin(ShapeRenderer.ShapeType.Line)
  }

  fun end() = shape.end()

}