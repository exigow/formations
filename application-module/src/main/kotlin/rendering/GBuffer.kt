package rendering

import com.badlogic.gdx.Gdx
import rendering.canvas.Canvas
import rendering.canvas.ShaderEffect
import rendering.utils.FastBloomTool


class GBuffer(private val diffuse: Canvas, private val emissive: Canvas, private val ui: Canvas) {

  private val combined = Canvas.setUpRGB(diffuse.width, diffuse.height)
  private val bloom = FastBloomTool(256, 256)

  companion object  {

    fun setUp(width: Int, height: Int) = GBuffer(
      diffuse = Canvas.setUpRGB(width, height),
      emissive = Canvas.setUpRGB(width, height),
      ui = Canvas.setUpRGBA(width, height)
    )

    fun setUpWindowSize() = setUp(Gdx.graphics.width, Gdx.graphics.height)

  }

  fun paintOnDiffuse(f: () -> Unit) = diffuse.paint(f)

  fun paintOnEmissive(f: () -> Unit) = emissive.paint(f)

  fun paintOnUserInterface(f: () -> Unit) = ui.paint(f)

  fun clear() {
    diffuse.clear()
    emissive.clear()
    ui.clear(color = Color.white, alpha = 0f)
  }

  fun showCombined() {
    combined.paint {
      ShaderEffect.fromShader("mixDiffuseWithEmissive")
        .bind("textureDiffuse", diffuse)
        .bind("textureEmissive", emissive)
        .parametrize("noiseOffset", (System.currentTimeMillis() % 16).toFloat())
        .showAsQuad()
    }

    combined.showAsQuad()

    val bloomed = bloom.process(combined)
    Blending.ADDITIVE.decorate {
      bloomed.showAsQuad()
    }

    // todo blend it somehow inside final composition
    Blending.TRANSPARENCY.decorate {
      ui.showAsQuad()
    }
  }

}