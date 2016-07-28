package rendering

import assets.AssetsManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import commons.math.Vec2
import rendering.utils.FullscreenQuad


class GBuffer(private val diffuse: FrameBuffer, private val emissive: FrameBuffer) {

  private val temporaryBuffer = setUpSubBuffer(emissive.width, emissive.height)
  private val temporaryBuffer2 = setUpSubBuffer(emissive.width, emissive.height)

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

  fun paintOnDiffuse(f: () -> Unit) = diffuse.paintOn(f)

  fun paintOnEmissive(f: () -> Unit) = emissive.paintOn(f)

  private fun FrameBuffer.paintOn(f: () -> Unit) {
    begin()
    f.invoke()
    end()
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
    val scale = 1f // in pixels
    val size = Vec2(scale / emissive.width, scale / emissive.height)
    blurBuffer(emissive.colorBufferTexture, temporaryBuffer, size.onlyX())
    blurBuffer(temporaryBuffer.colorBufferTexture, temporaryBuffer2, size.onlyY())

    diffuse.colorBufferTexture.bind(0)
    temporaryBuffer2.colorBufferTexture.bind(1)
    val shader = AssetsManager.peekShader("combineGbuffer")
    shader.begin()
    shader.setUniformi("textureDiffuse", 0);
    shader.setUniformi("textureEmissive", 1);
    FullscreenQuad.renderWith(shader)
    shader.end()
  }

  private fun blurBuffer(source: Texture, destination: FrameBuffer, vector: Vec2) {
    destination.paintOn {
      source.bind(0)
      val shader = AssetsManager.peekShader("blur")
      shader.begin()
      shader.setUniformi("texture", 0);
      shader.setUniform2fv("scale", floatArrayOf(vector.x, vector.y), 0, 2)
      FullscreenQuad.renderWith(shader)
      shader.end()
    }
  }

}