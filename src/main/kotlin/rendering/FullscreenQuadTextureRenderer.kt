package rendering

import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.glutils.ShaderProgram


class FullscreenQuadTextureRenderer {

  private val mesh = initialiseMesh()
  private val shader = ShaderProgram(
    """
    attribute vec4 a_position;
    attribute vec2 a_texCoord0;
    varying vec2 v_texCoords;

    void main() {
      v_texCoords = a_texCoord0;
      gl_Position = a_position;
    }
    """,
    """
    varying vec2 v_texCoords;
    uniform sampler2D u_texture;

    void main() {
        gl_FragColor = texture2D(u_texture, v_texCoords);
    }
    """
  )

  fun render(texture: Texture) {
    texture.bind(0)
    shader.begin()
    shader.setUniformi("u_texture", 0);
    mesh.render(shader, GL20.GL_TRIANGLE_FAN, 0, 4)
    shader.end()
  }

  private fun initialiseMesh(): Mesh {
    val mesh = Mesh(Mesh.VertexDataType.VertexArray, true, 4, 0,
      VertexAttribute(VertexAttributes.Usage.Position, 2, "a_position"),
      VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "a_texCoord0")
    );
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
