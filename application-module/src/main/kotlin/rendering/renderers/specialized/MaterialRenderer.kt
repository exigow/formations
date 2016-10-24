package rendering.renderers.specialized

import Vec2
import assets.AssetsManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Mesh
import com.badlogic.gdx.graphics.VertexAttribute
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Matrix4
import rendering.Color
import rendering.GBuffer
import rendering.Sprite

internal class MaterialRenderer(val gbuffer: GBuffer) {

  private val ambientColor = Color(.852f, .467f, .242f) // Color(.784f, .764f, .662f)
  private val mesh = initialiseMesh()

  fun draw(sprite: Sprite, matrix: Matrix4) {
    val transformed = sprite.toVertices()
    val vertices = decomposeToVbo(transformed, sprite.depth, sprite.alpha)
    mesh.setVertices(vertices)
    sprite.material.blending.decorate {
      gbuffer.paintOnDiffuse {
        val shader = AssetsManager.peekShader("materialDiffuse")
        sprite.material.diffuse!!.bind(0)
        shader.begin();
        shader.setUniformMatrix("projection", matrix);
        shader.setUniformi("texture", 0);
        val color = when (sprite.material.isIlluminated) {
          true -> ambientColor
          false -> Color.white
        }
        shader.setUniform3fv("ambientColor", color.toFloatArray(), 0, 3)
        renderMesh(shader)
        shader.end();
      }
      gbuffer.paintOnEmissive {
        val shader = AssetsManager.peekShader("materialEmissive")
        if (sprite.material.emissive != null)
          sprite.material.emissive.bind(1)
        else
          AssetsManager.peekMaterial("black").diffuse!!.bind(1)
        sprite.material.diffuse!!.bind(0)
        shader.begin();
        shader.setUniformMatrix("projection", matrix);
        shader.setUniformi("texture", 1);
        shader.setUniformi("colorTexture", 0);
        renderMesh(shader)
        shader.end();
      }
    }
  }

  private fun renderMesh(shader: ShaderProgram) = mesh.render(shader, GL20.GL_TRIANGLE_FAN, 0, 4)

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

  private fun initialiseMesh(): Mesh {
    val mesh = Mesh(Mesh.VertexDataType.VertexArray, true, 4, 0,
      VertexAttribute(VertexAttributes.Usage.Position, 3, "positionAttr"),
      VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "texCoordAttr"),
      VertexAttribute(VertexAttributes.Usage.Generic, 1, "alphaAttr")
    );
    return mesh
  }

}