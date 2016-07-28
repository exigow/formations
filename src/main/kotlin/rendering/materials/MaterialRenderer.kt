package rendering.materials

import assets.AssetsManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Matrix4
import commons.math.FastMath
import commons.math.Vec2
import commons.math.Vec2.Transformations.rotate
import commons.math.Vec2.Transformations.scale
import commons.math.Vec2.Transformations.translate
import rendering.GBuffer
import rendering.utils.Blender

class MaterialRenderer(val gbuffer: GBuffer) {

  private val mesh = initialiseMesh()

  fun draw(material: Material, where: Vec2, angle: Float, matrix: Matrix4) {
    val transformed = transformedQuad(material.size(), where, angle)
    val vertices = decomposeToVbo(transformed)
    mesh.setVertices(vertices)
    Blender.enableTransparency {
      gbuffer.paintOnDiffuse {
        val shader = AssetsManager.peekShader("materialDiffuse")
        material.diffuse!!.bind(0)
        shader.begin();
        shader.setUniformMatrix("projection", matrix);
        shader.setUniformi("texture", 0);
        renderMesh(shader)
        shader.end();
      }
      gbuffer.paintOnEmissive {
        val shader = AssetsManager.peekShader("materialEmissive")
        material.diffuse!!.bind(0)
        material.emissive!!.bind(1)
        shader.begin();
        shader.setUniformMatrix("projection", matrix);
        shader.setUniformi("colorTexture", 0);
        shader.setUniformi("texture", 1);
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

  private fun transformedQuad(size: Vec2, position: Vec2, angle: Float) = listOf(
      Vec2(-1, -1),
      Vec2(1, -1),
      Vec2(1, 1),
      Vec2(-1, 1)
    ).scale(size / 8)
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