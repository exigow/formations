package rendering.trails

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Matrix4
import commons.math.Vec2
import core.Camera

class TrailsRenderer {

  private val matrix = Matrix4()
  private val mesh = createMesh();
  private val shader = ShaderProgram(
    """
    uniform mat4 projection;
    attribute vec4 positionAttr;
    attribute vec2 texCoordAttr;
    varying vec2 texCoord;
    varying float alpha;
    void main() {
      texCoord = texCoordAttr;
      alpha = positionAttr.z;
      gl_Position = projection * vec4(positionAttr.x, positionAttr.y, 0, 1);
    }
    """,
    """
    uniform sampler2D texture;
    varying vec2 texCoord;
    varying float alpha;
    void main() {
        vec4 color = texture2D(texture, texCoord) * vec4(vec3(alpha), 1);
        gl_FragColor = color;
    }
    """
  );

  fun update(camera: Camera) = matrix.set(camera.projectionMatrix())

  fun render(buffer: TrailsBuffer, texture: Texture) {
    val vertices = FloatArray(2048)
    val indices = ShortArray(2048)

    var verticesIndex = 0
    var indicesIndex = 0
    var z = 0
    val scale = 4f

    buffer.forEachConnection { from, to, fromAngle, toAngle, fromAlpha, toAlpha ->

      val fromRotated = Vec2.rotated(fromAngle) * scale
      val toRotated = Vec2.rotated(toAngle) * scale

      vertices[verticesIndex + 0] = from.x + fromRotated.x
      vertices[verticesIndex + 1] = from.y + fromRotated.y
      vertices[verticesIndex + 2] = fromAlpha
      vertices[verticesIndex + 3] = 0f
      vertices[verticesIndex + 4] = 0f
      vertices[verticesIndex + 5] = from.x - fromRotated.x
      vertices[verticesIndex + 6] = from.y - fromRotated.y
      vertices[verticesIndex + 7] = fromAlpha
      vertices[verticesIndex + 8] = 1f
      vertices[verticesIndex + 9] = 0f
      vertices[verticesIndex + 10] = to.x + toRotated.x
      vertices[verticesIndex + 11] = to.y + toRotated.y
      vertices[verticesIndex + 12] = toAlpha
      vertices[verticesIndex + 13] = 0f
      vertices[verticesIndex + 14] = 1f
      vertices[verticesIndex + 15] = to.x - toRotated.x
      vertices[verticesIndex + 16] = to.y - toRotated.y
      vertices[verticesIndex + 17] = toAlpha
      vertices[verticesIndex + 18] = 1f
      vertices[verticesIndex + 19] = 1f

      verticesIndex += 20

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


    Gdx.gl.glEnable(GL20.GL_BLEND);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE); // GL11.GL_DST_COLOR, GL11.GL_ONE
    texture.bind(0)
    shader.begin();
    shader.setUniformMatrix("projection", matrix);
    shader.setUniformi("texture", 0);
    mesh.render(shader, GL20.GL_TRIANGLES);
    shader.end();
    Gdx.gl.glDisable(GL20.GL_BLEND);

    /*buffer.forEachPosition { Draw.cross(it, 4f) }
    buffer.forEachConnection { from, to, fromAngle, toAngle ->
      Draw.line(from, to)
      val fa = Vec2.rotated(fromAngle + FastMath.pi) * scale * 2
      val ta = Vec2.rotated(toAngle + FastMath.pi) * scale * 2
      Draw.line(from + fa, from - fa)
      Draw.line(to + ta, to - ta)
    }*/
  }

  private fun createMesh() = Mesh(Mesh.VertexDataType.VertexArray, true, 2048, 2048, // 4,6
    VertexAttribute(VertexAttributes.Usage.Position, 3, "positionAttr"),
    VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "texCoordAttr")
  );

}