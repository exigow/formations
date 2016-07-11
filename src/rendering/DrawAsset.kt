package rendering

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import commons.math.FastMath
import commons.math.Vec2
import core.Camera


object DrawAsset {

  private val batch = SpriteBatch()

  fun update(camera: Camera) {
    batch.projectionMatrix = camera.projectionMatrix()
  }

  fun draw(texture: Texture, where: Vec2, angle: Float) {
    val vertices = calcSpriteQuad(where, angle + FastMath.pi / 2, texture.width.toFloat())
    batch.begin()
    batch.draw(texture, vertices, 0, vertices.size)
    batch.end()
  }

  private fun calcSpriteQuad(where: Vec2, angle: Float, size: Float): FloatArray {
    val fixedAngle = angle + FastMath.pi / 4
    val fixedSize = size / 2
    val cos = FastMath.cos(fixedAngle)
    val sin = FastMath.sin(fixedAngle)
    val a = where + Vec2(-cos, -sin) * fixedSize
    val b = where + Vec2(sin, -cos) * fixedSize
    val c = where + Vec2(cos, sin) * fixedSize
    val d = where + Vec2(-sin, cos) * fixedSize
    return calcQuad(a, b, c, d);
  }

  private fun calcQuad(a: Vec2, b: Vec2, c: Vec2, d: Vec2): FloatArray {
    val color = com.badlogic.gdx.graphics.Color.WHITE.toFloatBits();
    return floatArrayOf(
      a.x, a.y, color, 0f, 0f,
      b.x, b.y, color, 1f, 0f,
      c.x, c.y, color, 1f, 1f,
      d.x, d.y, color, 0f, 1f
    )
  }

}