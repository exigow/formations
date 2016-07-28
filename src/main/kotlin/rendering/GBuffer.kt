package rendering

import assets.AssetsManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import rendering.utils.FullscreenQuad


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

  fun paintOnEmissive(f: () -> Unit) = paintOn(emissive, f)

  private fun paintOn(buffer: FrameBuffer, f: () -> Unit) {
    buffer.begin()
    f.invoke()
    buffer.end()
  }

  fun clear() {
    val color = Color.BLACK
    val alpha = 1f
    paintOnDiffuse { clearBuffer(color, alpha) }
    paintOnEmissive { clearBuffer(color, alpha) }
  }

  private fun clearBuffer(color: Color, alpha: Float) {
    Gdx.gl.glClearColor(color.r, color.g, color.b, alpha)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
  }

  fun showCombined() {
    diffuse.colorBufferTexture.bind(0)
    emissive.colorBufferTexture.bind(1)
    val shader = AssetsManager.peekShader("combineGbuffer")
    shader.begin()
    shader.setUniformi("textureDiffuse", 0);
    shader.setUniformi("textureEmissive", 1);
    FullscreenQuad.renderWith(shader)
    shader.end()
  }

}