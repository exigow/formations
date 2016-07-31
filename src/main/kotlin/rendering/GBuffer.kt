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
  private val combined = setUpSubBuffer(diffuse.width, diffuse.height)
  private val cutoff = setUpSubBuffer(256, 256)
  private val lensflare = setUpSubBuffer(256, 256)
  private val temp0 = setUpSubBuffer(256, 256)
  private val temp1 = setUpSubBuffer(256, 256)

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
    blur(emissive, temporaryBuffer, temporaryBuffer2)

    combined.paintOn {
      diffuse.colorBufferTexture.bind(0)
      temporaryBuffer2.colorBufferTexture.bind(1)
      val shader = AssetsManager.peekShader("mixDiffuseWithEmissive")
      shader.begin()
      shader.setUniformi("textureDiffuse", 0);
      shader.setUniformi("textureEmissive", 1);
      FullscreenQuad.renderWith(shader)
      shader.end()
    }

    thresholdBuffer(combined.colorBufferTexture, cutoff)

    lensflareBuffer(cutoff.colorBufferTexture, lensflare)
    blur(lensflare, temp0, temp1)

    //show(temp1)

    combined.colorBufferTexture.bind(0)
    temp1.colorBufferTexture.bind(1)
    AssetsManager.peekMaterial("dirt").diffuse!!.bind(2)
    val shader = AssetsManager.peekShader("lensPlusMix")
    shader.begin()
    shader.setUniformi("textureClean", 0);
    shader.setUniformi("textureLens", 1);
    shader.setUniformi("textureDirt", 2);
    FullscreenQuad.renderWith(shader)
    shader.end()
  }

  private fun blur(source: FrameBuffer, firstStage: FrameBuffer, secondStage: FrameBuffer) {
    val size = Vec2(1f / firstStage.width, 1f / firstStage.height)
    blurBuffer(source.colorBufferTexture, firstStage, size.onlyX())
    blurBuffer(firstStage.colorBufferTexture, secondStage, size.onlyY())
  }

  private fun blurBuffer(source: Texture, destination: FrameBuffer, vector: Vec2) {
    destination.paintOn {
      source.bind(0)
      val shader = AssetsManager.peekShader("blur")
      shader.begin()
      shader.setUniformi("texture", 0);
      shader.setUniform2fv("scale", vector.toFloatArray(), 0, 2)
      FullscreenQuad.renderWith(shader)
      shader.end()
    }
  }

  private fun thresholdBuffer(source: Texture, destination: FrameBuffer) {
    destination.paintOn {
      source.bind(0)
      val shader = AssetsManager.peekShader("threshold")
      shader.begin()
      shader.setUniformi("texture", 0);
      shader.setUniformf("scale", 4f)
      shader.setUniformf("bias", -.875f)
      FullscreenQuad.renderWith(shader)
      shader.end()
    }
  }

  private fun lensflareBuffer(source: Texture, destination: FrameBuffer) {
    destination.paintOn {
      source.bind(0)
      AssetsManager.peekMaterial("flareGradient").diffuse!!.bind(1)
      val shader = AssetsManager.peekShader("lensflare")
      shader.begin()
      shader.setUniformi("texture", 0);
      shader.setUniformi("gradient", 1)
      FullscreenQuad.renderWith(shader)
      shader.end()
    }
  }

  private fun show(buffer: FrameBuffer) {
    buffer.colorBufferTexture.bind(0)
    val shader = AssetsManager.peekShader("fullscreenQuadShader")
    shader.begin()
    shader.setUniformi("texture", 0);
    FullscreenQuad.renderWith(shader)
    shader.end()
  }

}