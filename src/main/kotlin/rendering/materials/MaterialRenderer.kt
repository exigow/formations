package rendering.materials

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Matrix4
import commons.math.FastMath
import commons.math.Vec2
import commons.math.Vec2.Transformations.rotate
import commons.math.Vec2.Transformations.scale
import commons.math.Vec2.Transformations.translate

class MaterialRenderer {

  private val batch = SpriteBatch()

  fun draw(material: Material, where: Vec2, angle: Float, matrix: Matrix4) {
    val transformed = transformedQuad(material.size(), where, angle)
    val vertices = decomposeToVbo(transformed)
    paintWithBatch(material.diffuse!!, vertices, matrix)
  }

  private fun paintWithBatch(texture: Texture, vertices: FloatArray, matrix: Matrix4) {
    batch.projectionMatrix = matrix
    batch.begin()
    batch.draw(texture, vertices, 0, vertices.size)
    batch.end()
  }

  private fun decomposeToVbo(vectors: List<Vec2>): FloatArray {
    val i = vectors.iterator()
    return toVbo(i.next(), i.next(), i.next(), i.next())
  }

  private fun transformedQuad(size: Vec2, position: Vec2, angle: Float) = listOf(
      Vec2(-1, -1),
      Vec2(1, -1),
      Vec2(1, 1),
      Vec2(-1, 1)
    ).scale(size / 4)
    .rotate(angle + FastMath.pi / 2)
    .translate(position)

  private fun toVbo(a: Vec2, b: Vec2, c: Vec2, d: Vec2): FloatArray {
    val color = Color.WHITE.toFloatBits();
    return floatArrayOf(
      a.x, a.y, color, 0f, 0f,
      b.x, b.y, color, 1f, 0f,
      c.x, c.y, color, 1f, 1f,
      d.x, d.y, color, 0f, 1f
    )
  }

}