package rendering.paths

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20
import com.badlogic.gdx.math.Matrix4
import core.Camera
import rendering.Color


class PathRenderer {

  private val renderer: ImmediateModeRenderer = ImmediateModeRenderer20(false, true, 0)
  private val matrix = Matrix4()

  fun update(camera: Camera) {
    matrix.set(camera.projectionMatrix())
  }

  private fun enableBlend() {
    Gdx.gl.glEnable(GL20.GL_BLEND);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
  }

  private fun disableBlend() {
    Gdx.gl.glDisable(GL20.GL_BLEND);
  }

  private fun hasAlpha(alpha: Float) = alpha < .975f

  fun renderFilled(path: Path, color: Color, alpha: Float) {
    if (hasAlpha(alpha))
      enableBlend()
    renderer.begin(matrix, GL20.GL_TRIANGLE_FAN);
    for (element in path.elements) {
      renderer.color(color.r, color.g, color.b, alpha)
      renderer.vertex(element.x, element.y, 0f)
    }
    renderer.end();
    if (hasAlpha(alpha))
      disableBlend()
  }

  fun renderLine(path: Path, color: Color, alpha: Float) {
    if (hasAlpha(alpha))
      enableBlend()
    renderer.begin(matrix, GL20.GL_LINE_STRIP);
    for (element in path.elements) {
      renderer.color(color.r, color.g, color.b, alpha)
      renderer.vertex(element.x, element.y, 0f)
    }
    renderer.end();
    if (hasAlpha(alpha))
      disableBlend()
  }

  fun renderLines(paths: List<Path>, color: Color, alpha: Float) = paths.forEach { renderLine(it, color, alpha) }

  fun renderFilledOutlined(path: Path, fillColor: Color, fillAlpha: Float = 1f, outlineColor: Color, outlineAlpha: Float = 1f) {
    renderFilled(path, fillColor, fillAlpha)
    renderLine(path, outlineColor, outlineAlpha)
  }

}