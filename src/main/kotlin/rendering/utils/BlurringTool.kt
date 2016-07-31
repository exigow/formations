package rendering.utils

import assets.AssetsManager
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import commons.math.Vec2
import rendering.utils.FrameBufferUtils.paintOn

class BlurringTool(private val size: Int) {

  val tempBufferA = FrameBufferUtils.create(size)
  val tempBufferB = FrameBufferUtils.create(size)

  fun blur(source: FrameBuffer, multiplier: Vec2 = Vec2.one()): FrameBuffer {
    val fixed = multiplier / size
    blurBuffer(source, tempBufferA, fixed.onlyX())
    blurBuffer(tempBufferA, tempBufferB, fixed.onlyY())
    return tempBufferB
  }

  private fun blurBuffer(source: FrameBuffer, destination: FrameBuffer, vector: Vec2) {
    destination.paintOn {
      source.colorBufferTexture.bind(0)
      val shader = AssetsManager.peekShader("blur")
      shader.begin()
      shader.setUniformi("texture", 0);
      shader.setUniform2fv("scale", vector.toFloatArray(), 0, 2)
      FullscreenQuad.renderWith(shader)
      shader.end()
    }
  }

}