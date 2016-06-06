import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Line
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

object Renderer {

  private val shape = ShapeRenderer()

  fun renderCircle(position: Vector2, radius: Float = 1f) {
    shape.begin(Line)
    shape.circle(position.x, position.y, radius)
    shape.end()
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
    shape.begin(Line)
    shape.polygon(vertices)
    shape.end()
    shape.identity();
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

  fun reset() {
    clear()
    updateProjection()
  }

  private fun clear() {
    Gdx.gl.glClearColor(.125f, .125f, .125f, 1f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
  }

  private fun updateProjection() {
    shape.projectionMatrix.set(Camera.projectionMatrix())
  }

}