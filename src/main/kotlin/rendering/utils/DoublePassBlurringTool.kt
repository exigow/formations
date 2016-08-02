package rendering.utils

import com.badlogic.gdx.graphics.Texture
import commons.math.Vec2
import rendering.canvas.Canvas
import rendering.canvas.ShaderEffect

class DoublePassBlurringTool(private val canvasFunc: () -> Canvas) {

  val tempBufferA = canvasFunc.invoke()
  val tempBufferB = canvasFunc.invoke()

  fun blur(source: Texture, firstPass: Vec2 = Vec2.one().onlyX(), secondPass: Vec2 = Vec2.one().onlyY()): Canvas {
    val fixedFirst = firstPass / Vec2(tempBufferA.width, tempBufferA.height)
    val fixedSecond = secondPass / Vec2(tempBufferA.width, tempBufferA.height)
    blurBuffer(source, tempBufferA, fixedFirst)
    blurBuffer(tempBufferA.texture, tempBufferB, fixedSecond)
    return tempBufferB
  }

  private fun blurBuffer(source: Texture, destination: Canvas, vector: Vec2) = destination.paint {
    ShaderEffect.fromShader("blur")
      .bind("texture", source)
      .parametrize("scale", vector)
      .showAsQuad()
  }

}