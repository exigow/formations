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

  fun clear(color: Color = Color.BLACK) = paint {
    Gdx.gl.glClearColor(color.r, color.g, color.b, 1f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
  }

  fun showAsQuad() = ShaderEffect.fromShader("fullscreenQuadShader")
    .bind("texture", this)
    .showAsQuad()

  companion object {

    fun setUp(width: Int, height: Int): Canvas {
      val buffer = FrameBuffer(Pixmap.Format.RGB888, width, height, false)
      val wrap = Texture.TextureWrap.ClampToEdge
      buffer.colorBufferTexture.setWrap(wrap, wrap)
      return Canvas(buffer)
    }

    fun setUpSquare(size: Int) = setUp(size, size)

  }

}