package rendering

import assets.AssetsManager
import rendering.canvas.Canvas
import rendering.canvas.FullscreenQuad
import rendering.utils.BlurringTool


class GBuffer(private val diffuse: Canvas, private val emissive: Canvas) {

  private val combined = Canvas.setUp(diffuse.width, diffuse.height)
  private val emissiveBlurTool = BlurringTool(512)

  companion object  {

    fun setUp(width: Int, height: Int) = GBuffer(
      diffuse = Canvas.setUp(width, height),
      emissive = Canvas.setUp(512, 512)
    )

  }

  fun paintOnDiffuse(f: () -> Unit) = diffuse.paint(f)

  fun paintOnEmissive(f: () -> Unit) = emissive.paint(f)

  fun clear() {
    diffuse.clear()
    emissive.clear()
  }

  fun showCombined() {
    val emissiveBlurred = emissiveBlurTool.blur(emissive.texture)

    combined.paint {
      diffuse.texture.bind(0)
      emissive.texture.bind(1)
      emissiveBlurred.texture.bind(2)
      val shader = AssetsManager.peekShader("mixDiffuseWithEmissive")
      shader.begin()
      shader.setUniformi("textureDiffuse", 0);
      shader.setUniformi("textureEmissive", 1);
      shader.setUniformi("textureEmissiveBlurred", 2);
      FullscreenQuad.renderWith(shader)
      shader.end()
    }



    //computeThreshold(combined, cutoff)

    //lensflareBuffer(cutoff.colorBufferTexture, lensflare)
    //blur(cutoff, tempA256, tempB256, 1f)
    //blur(tempB256, tempA64, tempB64, 1f)

    //val bloom = bloomBlurTool.blur(cutoff, Vec2(.75f, .75f))

    //val bloomHalo = bloomBlurHaloTool.blur(bloom, Vec2(1.75f, .25f))

    combined.show()

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

  /*private fun computeThreshold(source: FrameBuffer, destination: FrameBuffer) {
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
  }*/

}