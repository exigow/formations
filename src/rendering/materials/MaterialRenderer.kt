package rendering.materials

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Matrix4
import commons.math.FastMath
import commons.math.Vec2


class MaterialRenderer {

  private val batch = SpriteBatch()

  fun draw(material: Material, where: Vec2, angle: Float, matrix: Matrix4) {
    val texture = material.diffuse!!
    val vertices = calcSpriteQuad(where, angle + FastMath.pi / 2, texture.width.toFloat())
    batch.projectionMatrix = matrix
    batch.begin()
    batch.draw(texture, vertices, 0, vertices.size)
    batch.end()
  }

  private fun calcSpriteQuad(where: Vec2, angle: Float, size: Float): FloatArray {
    val fixedAngle = angle + FastMath.pi / 4
    val fixedSize = size / 4
    val cos = FastMath.cos(fixedAngle)
    val sin = FastMath.sin(fixedAngle)
    val a = where + Vec2(-cos, -sin) * fixedSize
    val b = where + Vec2(sin, -cos) * fixedSize
    val c = where + Vec2(cos, sin) * fixedSize
    val d = where + Vec2(-sin, cos) * fixedSize
    return calcQuad(a, b, c, d);
  }

  private fun calcQuad(a: Vec2, b: Vec2, c: Vec2, d: Vec2): FloatArray {
    val color = Color.WHITE.toFloatBits();
    return floatArrayOf(
      a.x, a.y, color, 0f, 0f,
      b.x, b.y, color, 1f, 0f,
      c.x, c.y, color, 1f, 1f,
      d.x, d.y, color, 0f, 1f
    )
  }

}