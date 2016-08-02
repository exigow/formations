package rendering.utils

import com.badlogic.gdx.graphics.Texture
import commons.math.Vec2
import rendering.canvas.Canvas
import rendering.canvas.ShaderEffect

class BlurringTool(private val size: Int) {

  val tempBufferA = Canvas.setUpSquare(size)
  val tempBufferB = Canvas.setUpSquare(size)

  fun blur(source: Texture, multiplier: Vec2 = Vec2.one()): Canvas {
    val fixed = multiplier / size
    blurBuffer(source, tempBufferA, fixed.onlyX())
    blurBuffer(tempBufferA.texture, tempBufferB, fixed.onlyY())
    return tempBufferB
  }

  private fun blurBuffer(source: Texture, destination: Canvas, vector: Vec2) = destination.paint {
    ShaderEffect.fromShader("blur")
      .bind("texture", source)
      .parametrize("scale", vector)
      .showAsQuad()
  }

}