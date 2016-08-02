package rendering

import assets.AssetsManager
import com.badlogic.gdx.graphics.Texture
import commons.math.Vec2
import rendering.canvas.Canvas
import rendering.canvas.ShaderEffect
import rendering.utils.DoublePassBlurringTool


class GBuffer(private val diffuse: Canvas, private val emissive: Canvas) {

  private val combined = Canvas.setUp(diffuse.width, diffuse.height)
  private val emissiveBlurTool = DoublePassBlurringTool({ Canvas.setUp(diffuse.width / 2, diffuse.height / 2) })
  private val threshold = Canvas.setUp(512, 512)
  private val lensFlares = Canvas.setUp(512, 512)
  private val lensBlurTool = DoublePassBlurringTool({ Canvas.setUp(512, 512) })
  private val bloomBlurTool = DoublePassBlurringTool({ Canvas.setUp(512, 512) })

  companion object  {

    fun setUp(width: Int, height: Int) = GBuffer(
      diffuse = Canvas.setUp(width, height),
      emissive = Canvas.setUp(width / 2, height / 2)
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
      ShaderEffect.fromShader("mixDiffuseWithEmissive")
        .bind("textureDiffuse", diffuse)
        .bind("textureEmissive", emissive)
        .bind("textureEmissiveBlurred", emissiveBlurred)
        .showAsQuad()
    }

    val thresholdBlur = prepareBlurredThreshold()

    lensFlares.paint {
      ShaderEffect.fromShader("lensflare")
        .bind("texture", thresholdBlur)
        .bind("gradient", AssetsManager.peekMaterial("flareGradient").diffuse!!)
        .showAsQuad()
    }

    ShaderEffect.fromShader("lensPlusMix")
      .bind("textureClean", combined)
      .bind("textureLens", lensFlares)
      .bind("textureBloom", thresholdBlur)
      .bind("textureDirt", AssetsManager.peekMaterial("dirt").diffuse!!)
      .showAsQuad()
  }

  private fun prepareBlurredThreshold(): Texture {
    threshold.paint {
      ShaderEffect.fromShader("threshold")
        .bind("texture", combined)
        .parametrize("scale", 16f)
        .parametrize("bias", -.965f)
        .showAsQuad()
    }
    threshold.showAsQuad()
    return lensBlurTool.blur(threshold.texture, Vec2(1.76f, 0f), Vec2(2.33f, 0f)).texture
  }

}