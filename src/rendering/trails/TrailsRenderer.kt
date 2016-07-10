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
    val scale = 8f

    buffer.forEachConnection { from, to, fromAngle, toAngle ->

      val fromRotated = Vec2.rotated(fromAngle) * scale
      val toRotated = Vec2.rotated(toAngle) * scale

      vertices[verticesIndex + 0] = from.x + fromRotated.x
      vertices[verticesIndex + 1] = from.y + fromRotated.y

      vertices[verticesIndex + 2] = from.x - fromRotated.x
      vertices[verticesIndex + 3] = from.y - fromRotated.y

      vertices[verticesIndex + 4] = to.x + toRotated.x
      vertices[verticesIndex + 5] = to.y + toRotated.y

      vertices[verticesIndex + 6] = to.x - toRotated.x
      vertices[verticesIndex + 7] = to.y - toRotated.y

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
    buffer.forEachConnection { from, to, fromAngle, toAngle ->
      Draw.line(from, to)
      val fa = Vec2.rotated(fromAngle + FastMath.pi) * scale * 2
      val ta = Vec2.rotated(toAngle + FastMath.pi) * scale * 2
      Draw.line(from + fa, from - fa)
      Draw.line(to + ta, to - ta)
    }
  }

  private fun createMesh() = Mesh(Mesh.VertexDataType.VertexArray, true, 512, 512, // 4,6
    VertexAttribute(VertexAttributes.Usage.Position, 2, "positionAttr")
  );

}