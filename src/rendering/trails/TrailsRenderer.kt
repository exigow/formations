package rendering.trails

import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Matrix4
import commons.math.FastMath
import commons.math.Vec2
import core.Camera
import rendering.Draw

class TrailsRenderer {

  private val matrix = Matrix4()
  private val mesh = createMesh();
  private val shader = ShaderProgram(
    """
    uniform mat4 projection;
    attribute vec4 positionAttr;
    void main() {
       gl_Position = projection * positionAttr;
    }
    """,
    """
    void main() {
        gl_FragColor = vec4(1, 0, 0, 1);
    }
    """
  );

  fun update(camera: Camera) = matrix.set(camera.projectionMatrix())

  fun render(buffer: TrailsBuffer, texture: Texture) {
    val vertices = FloatArray(256)
    val indices = ShortArray(256)

    var verticesIndex = 0
    var indicesIndex = 0
    var z = 0

    buffer.forEachConnection { from, to ->

      val rotated = Vec2.rotated(from.directionTo(to) + FastMath.pi / 2f) * 8f

      vertices[verticesIndex + 0] = from.x + rotated.x
      vertices[verticesIndex + 1] = from.y + rotated.y

      vertices[verticesIndex + 2] = from.x - rotated.x
      vertices[verticesIndex + 3] = from.y - rotated.y

      vertices[verticesIndex + 4] = to.x + rotated.x
      vertices[verticesIndex + 5] = to.y + rotated.y

      vertices[verticesIndex + 6] = to.x - rotated.x
      vertices[verticesIndex + 7] = to.y - rotated.y

      verticesIndex += 8

      indices[indicesIndex + 0] = (z + 0).toShort()
      indices[indicesIndex + 1] = (z + 1).toShort()
      indices[indicesIndex + 2] = (z + 2).toShort()
      indices[indicesIndex + 3] = (z + 2).toShort()
      indices[indicesIndex + 4] = (z + 3).toShort()
      indices[indicesIndex + 5] = (z + 1).toShort()

      z += 4

      indicesIndex += 6
    }

    mesh.setVertices(vertices)
    mesh.setIndices(indices)

    shader.begin();
    shader.setUniformMatrix("projection", matrix);
    mesh.render(shader, GL20.GL_TRIANGLES);
    shader.end();

    buffer.forEachPosition { Draw.cross(it, 4f) }
    buffer.forEachConnection { from, to -> Draw.line(from, to) }
  }

  private fun createMesh() = Mesh(Mesh.VertexDataType.VertexArray, true, 512, 512, // 4,6
    VertexAttribute(VertexAttributes.Usage.Position, 2, "positionAttr")
  );

}