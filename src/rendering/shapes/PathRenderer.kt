package rendering.shapes

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4
import commons.math.Vec2
import core.Camera
import rendering.Color

object PathRenderer {

  fun update(camera: Camera) = GdxInternal.update(camera.projectionMatrix())

  fun render(path: Path, color: Color = Color.WHITE) {
    val i = path.elements.iterator()
    var prev = i.next()
    while (i.hasNext()) {
      val next = i.next()
      GdxInternal.line(prev, next, color)
      prev = next
    }
  }

  private object GdxInternal {

    private val shape = ShapeRenderer()

    fun line(from: Vec2, to: Vec2, color: Color) {
      shape.setColor(color.r, color.g, color.b, 1f)
      shape.begin(com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Line)
      shape.line(from.x, from.y, to.x, to.y)
      shape.end()
    }

    fun update(matrix: Matrix4) {
      shape.projectionMatrix = matrix
    }

  }

}