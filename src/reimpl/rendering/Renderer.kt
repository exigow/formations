package rendering

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2

object Renderer {

  val shape = ShapeRenderer()

  fun renderCircle(position: Vector2, radius: Float = 1f) {
    beginLine()
    shape.circle(position.x, position.y, radius)
    end()
  }

  fun renderArrow(position: Vector2, scale: Float = 16f, angle: Float = 0f) {
    val vertices: FloatArray = floatArrayOf(
      -1f, -.5f,
      -.75f, 0f,
      -1f, .5f,
      1f, 0f
    );
    shape.identity();
    shape.translate(position.x, position.y, 0f)
    shape.rotate(0f, 0f, 1f, angle * MathUtils.radiansToDegrees)
    shape.scale(scale, scale, scale)
    beginLine()
    shape.polygon(vertices)
    end()
    shape.identity();
  }

  private fun beginFilled() = shape.begin(ShapeRenderer.ShapeType.Filled)

  private fun beginLine() = shape.begin(ShapeRenderer.ShapeType.Line)

  private fun end() = shape.end()

  fun reset() {
    clear()
    updateProjection()
  }

  private fun clear() {
    Gdx.gl.glClearColor(.25f, .25f, .25f, 1f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
  }

  private fun updateProjection() {
    shape.projectionMatrix.set(Camera.matrix())
  }

}