package rendering.trails

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Matrix4
import commons.math.Vec2

class TrailsRenderer {

  private val mesh = Mesh(true, 256, 0,
    VertexAttribute(VertexAttributes.Usage.Position, 2, "positionAttr"),
    VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "texCoordAttr"),
    VertexAttribute(VertexAttributes.Usage.Generic, 1, "lifeAttr")
  );
  private val shader = ShaderProgram(
    """
    uniform mat4 projection;
    attribute vec4 positionAttr;
    attribute vec2 texCoordAttr;
    attribute float lifeAttr;
    varying vec2 texCoord;
    varying float life;
    void main() {
      texCoord = texCoordAttr;
      life = lifeAttr;
      gl_Position = projection * positionAttr;
    }
    """,
    """
    uniform sampler2D texture;
    varying vec2 texCoord;
    varying float life;
    void main() {
        vec4 color = texture2D(texture, texCoord) * vec4(1, 1, 1, life);
        gl_FragColor = color;
    }
    """
  );

  fun render(buffer: TrailsBuffer, texture: Texture, matrix: Matrix4) {
    enableBlend();
    for (trail in buffer.trails) {
      mesh.setVertices(calculateTrailArray(trail))
      texture.bind(0)
      shader.begin();
      shader.setUniformMatrix("projection", matrix);
      shader.setUniformi("texture", 0);
      mesh.render(shader, GL20.GL_TRIANGLE_STRIP);
      shader.end();
    }
    disableBlend()
  }

  private fun calculateTrailArray(trail: TrailsBuffer.Trail): FloatArray {
    val batch = StripBatch((trail.list.size * 2) * 5)
    var prev = trail.list.first
    for (struct in trail.list) {
      val rel = (struct.position - prev.position).rotate90Left().normalize()
      val scaled = rel * 32//(.25f + .75f * struct.life) * 16f
      batch.emit(struct.position - scaled, Vec2(0f, 0f), struct.life)
      batch.emit(struct.position + scaled, Vec2(1f, 0f), struct.life)
      prev = struct
    }
    return batch.toVerticesArray()
  }

  private class StripBatch(capacity: Int) {

    private val array = FloatArray(capacity, {0f});
    private var index = 0

    fun emit(position: Vec2, coord: Vec2, life: Float) {
      array[index++] = position.x
      array[index++] = position.y
      array[index++] = coord.x
      array[index++] = coord.x
      array[index++] = life
    }

    fun toVerticesArray() = array

  }

  private fun enableBlend() {
    Gdx.gl.glEnable(GL20.GL_BLEND);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
  }

  private fun disableBlend() {
    Gdx.gl.glDisable(GL20.GL_BLEND);
  }

}