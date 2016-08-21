package rendering.utils

import rendering.Blending
import rendering.canvas.Canvas
import rendering.canvas.FullscreenQuad
import rendering.canvas.ShaderEffect


class FastBloomTool(private val width: Int, private val height: Int) {

  private val threshold = Canvas.setUp(width, height)
  private val verticalThreshold = Canvas.setUp(width, height)


  private val vertical = DoublePassBlurringTool({ Canvas.setUpSquare(256) })
  private val a = DoublePassBlurringTool({ Canvas.setUpSquare(256) })
  private val b = DoublePassBlurringTool({ Canvas.setUpSquare(64) })
  private val c = DoublePassBlurringTool({ Canvas.setUpSquare(16) })
  private val d = DoublePassBlurringTool({ Canvas.setUpSquare(8) })


  private val out = Canvas.setUp(width, height)

  fun process(input: Canvas): Canvas {
    computeThreshold(input, threshold, 2.25f, -.625f)

    a.blur(threshold.texture)
    b.blur(a.result().texture)
    c.blur(b.result().texture)
    d.blur(c.result().texture)

    computeThreshold(a.result(), verticalThreshold, 4f, -.175f)
    vertical.blur(verticalThreshold.texture, Vec2.one().onlyX() * 3.66f, Vec2.one().onlyX() * 1.66f)

    out.clear()
    out.paint {
      Blending.ADDITIVE.decorate {
        FullscreenQuad.justBlit(a.result().texture)
        FullscreenQuad.justBlit(b.result().texture)
        FullscreenQuad.justBlit(c.result().texture)
        FullscreenQuad.justBlit(d.result().texture)
        FullscreenQuad.justBlit(vertical.result().texture)
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

}