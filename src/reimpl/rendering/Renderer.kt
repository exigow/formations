package rendering

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

object Renderer {

  val shape = ShapeRenderer()

  fun renderCircle(position: Vector2, radius: Float) {
    beginFilled()
    shape.circle(position.x, position.y, radius)
    end()
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
    shape.projectionMatrix.set(Camera.projection())
  }

}