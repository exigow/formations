package rendering

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.glutils.FrameBuffer


class GBuffer(private val diffuse: FrameBuffer, private val emissive: FrameBuffer) {

  companion object  {

    fun setUp(width: Int, height: Int) = GBuffer(
      diffuse = setUpSubBuffer(width, height),
      emissive = setUpSubBuffer(width / 2, height / 2)
    )

    private fun setUpSubBuffer(width: Int, height: Int): FrameBuffer {
      val buffer = FrameBuffer(Pixmap.Format.RGB888, width, height, false);
      val wrap = Texture.TextureWrap.ClampToEdge
      buffer.colorBufferTexture.setWrap(wrap, wrap);
      return buffer;
    }

  }

  fun paintOnDiffuse(f: () -> Unit) = paintOn(diffuse, f)

  private fun paintOn(buffer: FrameBuffer, f: () -> Unit) {
    buffer.begin()
    f.invoke()
    buffer.end()
  }

  fun diffuseTexture() = diffuse.colorBufferTexture

  fun clearDiffuse(color: Color, alpha: Float) = paintOnDiffuse { clearBuffer(color, alpha) }

  private fun clearBuffer(color: Color, alpha: Float) {
    Gdx.gl.glClearColor(color.r, color.g, color.b, alpha)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
  }

}