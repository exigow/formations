package rendering.renderers.specialized

import assets.AssetsManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Mesh
import com.badlogic.gdx.graphics.VertexAttribute
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Matrix4
import FastMath
import Vec2
import Vec2.Transformations.rotate
import Vec2.Transformations.scale
import Vec2.Transformations.translate
import rendering.Color
import rendering.GBuffer
import rendering.materials.Material

internal class MaterialRenderer(val gbuffer: GBuffer) {

  private val ambientColor = Color(.784f, .764f, .662f)
  private val mesh = initialiseMesh()

  fun draw(material: Material, where: Vec2, angle: Float, scale: Float, matrix: Matrix4) {
    val transformed = transformedQuad(material.size(), scale, where, angle, material.origin)
    val vertices = decomposeToVbo(transformed)
    mesh.setVertices(vertices)
    material.blending.decorate {
      gbuffer.paintOnDiffuse {
        val shader = AssetsManager.peekShader("materialDiffuse")
        material.diffuse!!.bind(0)
        shader.begin();
        shader.setUniformMatrix("projection", matrix);
        shader.setUniformi("texture", 0);
        shader.setUniform3fv("ambientColor", ambientColor.toFloatArray(), 0, 3)
        renderMesh(shader)
        shader.end();
      }
      gbuffer.paintOnEmissive {
        val shader = AssetsManager.peekShader("materialEmissive")
        if (material.emissive != null)
          material.emissive.bind(1)
        else
          AssetsManager.peekMaterial("black").diffuse!!.bind(1)
        material.diffuse!!.bind(0)
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

  private fun decomposeToVbo(vectors: List<Vec2>): FloatArray {
    val i = vectors.iterator()
    return toVbo(i.next(), i.next(), i.next(), i.next())
  }

  private fun transformedQuad(size: Vec2, scale: Float, position: Vec2, angle: Float, origin: Vec2) = listOf(
    Vec2(0, 0),
    Vec2(1, 0),
    Vec2(1, 1),
    Vec2(0, 1)
    )
    .scale(size)
    .translate(origin * -1f)
    .scale(Vec2.one() * scale * .25f)
    .rotate(angle + FastMath.pi / 2)
    .translate(position)

  private fun toVbo(a: Vec2, b: Vec2, c: Vec2, d: Vec2) = floatArrayOf(
    a.x, a.y, 0f, 0f,
    b.x, b.y, 1f, 0f,
    c.x, c.y, 1f, 1f,
    d.x, d.y, 0f, 1f
  )

  private fun initialiseMesh(): Mesh {
    val mesh = Mesh(Mesh.VertexDataType.VertexArray, true, 4, 0,
      VertexAttribute(VertexAttributes.Usage.Position, 2, "positionAttr"),
      VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "texCoordAttr")
    );
    return mesh
  }

}