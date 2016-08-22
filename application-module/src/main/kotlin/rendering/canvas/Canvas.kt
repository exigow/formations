package rendering.canvas

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import rendering.Color


class Canvas(private val buffer: FrameBuffer) {

  val width = buffer.width
  val height = buffer.height
  val texture = buffer.colorBufferTexture

  fun paint(f: () -> Unit) {
    buffer.begin()
    f.invoke()
    buffer.end()
  }

  fun clear(color: Color = Color.black, alpha: Float = 1f) = paint {
    Gdx.gl.glClearColor(color.red, color.green, color.blue, alpha)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
  }

  fun showAsQuad() = ShaderEffect.fromShader("fullscreenQuadShader")
    .bind("texture", this)
    .showAsQuad()

  companion object {

    fun setUpRGBA(width: Int, height: Int) = setUp(width, height, Pixmap.Format.RGBA8888)

    fun setUpRGB(width: Int, height: Int) = setUp(width, height, Pixmap.Format.RGB888)

    fun setUpRGBSquare(size: Int) = setUpRGB(size, size)

    private fun setUp(width: Int, height: Int, format: Pixmap.Format): Canvas {
      val buffer = FrameBuffer(format, width, height, false)
      val wrap = Texture.TextureWrap.ClampToEdge
      buffer.colorBufferTexture.setWrap(wrap, wrap)
      return Canvas(buffer)
    }

  }

}