package rendering.renderers.specialized

import Vec2
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.math.Matrix4
import Color
import rendering.Sprite

internal class SpriteRenderer {

  private val ambientColor = Color(.852f, .467f, .242f) // Color(.784f, .764f, .662f)
  private val mesh = VboUtils.createCommonVbo(4)

  fun draw(sprite: Sprite, matrix: Matrix4) {
    val transformed = sprite.toVertices()
    val vertices = decomposeToVbo(transformed, sprite.depth, sprite.alpha)
    mesh.setVertices(vertices)
    sprite.material.blending.decorate {
      val color = when (sprite.material.isIlluminated) {
        true -> ambientColor
        false -> Color.white
      }
      VboUtils.paintWithMaterialShader(sprite.material, matrix, color, {
        shader -> mesh.render(shader, GL20.GL_TRIANGLE_FAN, 0, 4)
      })
    }
  }

  private fun decomposeToVbo(vectors: List<Vec2>, depth: Float, alpha: Float): FloatArray {
    val i = vectors.iterator()
    return toVbo(i.next(), i.next(), i.next(), i.next(), depth, alpha)
  }

  private fun toVbo(a: Vec2, b: Vec2, c: Vec2, d: Vec2, depth: Float, alpha: Float) = floatArrayOf(
    a.x, a.y, depth, 0f, 0f, alpha,
    b.x, b.y, depth, 1f, 0f, alpha,
    c.x, c.y, depth, 1f, 1f, alpha,
    d.x, d.y, depth, 0f, 1f, alpha
  )

}