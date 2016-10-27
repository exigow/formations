package rendering.canvas

import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.glutils.ShaderProgram


internal object FullscreenQuad {

  private val mesh = initialiseMesh()

  fun renderWith(shader: ShaderProgram) = mesh.render(shader, GL20.GL_TRIANGLE_FAN, 0, 4)

  fun justBlit(texture: Texture) = ShaderEffect.fromShader("fullscreenQuadShader")
    .bind("texture", texture)
    .showAsQuad()

  private fun initialiseMesh(): Mesh {
    val mesh = Mesh(true, 4, 0,
      VertexAttribute(VertexAttributes.Usage.Position, 2, "positionAttr"),
      VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "texCoordAttr")
    )
    val vertices = initialiseVertices()
    mesh.setVertices(vertices)
    return mesh
  }

  private fun initialiseVertices() = floatArrayOf(
    -1f, // x1
    -1f, // y1
    0f,  // u1
    0f,  // v1

    1f,  // x2
    -1f, // y2
    1f,  // u2
    0f,  // v2

    1f,  // x3
    1f,  // y3
    1f,  // u3
    1f,  // v3

    -1f, // x4
    1f,  // y4
    0f,  // u4
    1f   // v4
  )

}