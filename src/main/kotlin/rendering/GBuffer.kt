package rendering

import assets.AssetsManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import commons.math.Vec2
import rendering.utils.BlurringTool
import rendering.utils.FrameBufferUtils
import rendering.utils.FrameBufferUtils.paintOn
import rendering.utils.FullscreenQuad


class GBuffer(private val diffuse: FrameBuffer, private val emissive: FrameBuffer) {

  /*private val temporaryBuffer = setUpSubBuffer(emissive.width, emissive.height)
  private val temporaryBuffer2 = setUpSubBuffer(emissive.width, emissive.height)*/
  private val combined = FrameBufferUtils.create(diffuse.width, diffuse.height)
  private val cutoff = FrameBufferUtils.create(512)
  /*private val tempA256 = setUpSubBuffer(256, 256)
  private val tempB256 = setUpSubBuffer(256, 256)
  private val tempA64 = setUpSubBuffer(64, 64)
  private val tempB64 = setUpSubBuffer(64, 64)*/
  private val emissiveBlurTool = BlurringTool(512)

  private val bloomBlurTool = BlurringTool(256)
  private val bloomBlurHaloTool = BlurringTool(128)
  //private val bloomBlurTwoTool = BlurringTool(64)

  companion object  {

    fun setUp(width: Int, height: Int) = GBuffer(
      diffuse = FrameBufferUtils.create(width, height),
      emissive = FrameBufferUtils.create(512, 512)
    )

  }

  fun paintOnDiffuse(f: () -> Unit) = diffuse.paintOn(f)

  fun paintOnEmissive(f: () -> Unit) = emissive.paintOn(f)

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
    val emissiveBlurred = emissiveBlurTool.blur(emissive)

    combined.paintOn {
      diffuse.colorBufferTexture.bind(0)
      emissiveBlurred.colorBufferTexture.bind(1)
      val shader = AssetsManager.peekShader("mixDiffuseWithEmissive")
      shader.begin()
      shader.setUniformi("textureDiffuse", 0);
      shader.setUniformi("textureEmissive", 1);
      FullscreenQuad.renderWith(shader)
      shader.end()
    }



    //computeThreshold(combined, cutoff)

    //lensflareBuffer(cutoff.colorBufferTexture, lensflare)
    //blur(cutoff, tempA256, tempB256, 1f)
    //blur(tempB256, tempA64, tempB64, 1f)

    //val bloom = bloomBlurTool.blur(cutoff, Vec2(.75f, .75f))

    //val bloomHalo = bloomBlurHaloTool.blur(bloom, Vec2(1.75f, .25f))

    show(combined)

    /*combined.colorBufferTexture.bind(0)
    bloom.colorBufferTexture.bind(1)
    bloomHalo.colorBufferTexture.bind(2)
    val shader = AssetsManager.peekShader("addbloom")
    shader.begin()
    shader.setUniformi("textureClean", 0);
    shader.setUniformi("textureBloom", 1);
    shader.setUniformi("textureBloomHalo", 2);
    FullscreenQuad.renderWith(shader)
    shader.end()*/
  }

  private fun computeThreshold(source: FrameBuffer, destination: FrameBuffer) {
    destination.paintOn {
      source.colorBufferTexture.bind(0)
      val shader = AssetsManager.peekShader("threshold")
      shader.begin()
      shader.setUniformi("texture", 0);
      shader.setUniformf("scale", 4f)
      shader.setUniformf("bias", -.675f)
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