package rendering.utils

import assets.AssetsManager
import rendering.Blending
import rendering.canvas.Canvas
import rendering.canvas.FullscreenQuad
import rendering.canvas.ShaderEffect


class FastBloomTool(private val width: Int, private val height: Int) {

  private val threshold = Canvas.setUpRGB(width, height)
  private val verticalThreshold = Canvas.setUpRGB(width, height)


  private val vertical = DoublePassBlurringTool({ Canvas.setUpRGBSquare(256) })
  private val a = DoublePassBlurringTool({ Canvas.setUpRGBSquare(256) })
  private val b = DoublePassBlurringTool({ Canvas.setUpRGBSquare(64) })
  private val c = DoublePassBlurringTool({ Canvas.setUpRGBSquare(16) })
  private val d = DoublePassBlurringTool({ Canvas.setUpRGBSquare(8) })


  private val out = Canvas.setUpRGB(width, height)

  fun process(input: Canvas): Canvas {
    computeThreshold(input, threshold, 2.25f, -.625f)

    a.blur(threshold.texture)
    b.blur(a.result().texture)
    c.blur(b.result().texture)
    d.blur(c.result().texture)

    computeThreshold(a.result(), verticalThreshold, 1.75f, -.275f)
    vertical.blur(verticalThreshold.texture, Vec2.one().onlyX() * 3.66f, Vec2.one().onlyX() * 1.66f)

    out.clear()
    out.paint {
      Blending.ADDITIVE.decorate {
        FullscreenQuad.justBlit(a.result().texture)
        FullscreenQuad.justBlit(b.result().texture)
        FullscreenQuad.justBlit(c.result().texture)
        FullscreenQuad.justBlit(d.result().texture)
        FullscreenQuad.justBlit(vertical.result().texture)
        paintFlares(verticalThreshold)
      }
    }
    return out
  }

  private fun computeThreshold(input: Canvas, output: Canvas, scale: Float, bias: Float) {
    output.paint {
      ShaderEffect.fromShader("threshold")
        .bind("texture", input)
        .parametrize("scale", scale)
        .parametrize("bias", bias)
        .showAsQuad()
    }
  }

  private fun paintFlares(input: Canvas) {
    ShaderEffect.fromShader("lensflare")
      .bind("texture", input)
      .bind("gradient", AssetsManager.peekMaterial("flare-gradient").diffuse!!)
      .showAsQuad()
  }

}