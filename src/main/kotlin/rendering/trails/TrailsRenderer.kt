package rendering.trails

import assets.AssetsManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.math.Matrix4
import commons.math.Vec2

class TrailsRenderer {

  private val mesh = Mesh(true, 1024, 0,
    VertexAttribute(VertexAttributes.Usage.Position, 2, "positionAttr"),
    VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "texCoordAttr"),
    VertexAttribute(VertexAttributes.Usage.Generic, 1, "lifeAttr")
  );

  fun render(trail: TrailsBuffer.Trail, texture: Texture, matrix: Matrix4) {
    applyAdditiveBlending {
      mesh.setVertices(calculateTrailArray(trail.list))
      texture.bind(0)
      val shader = AssetsManager.peekShader("trailShader")
      shader.begin();
      shader.setUniformMatrix("projection", matrix);
      shader.setUniformi("texture", 0);
      mesh.render(shader, GL20.GL_TRIANGLE_STRIP);
      shader.end();
    }
  }

  private fun calculateTrailArray(structures: List<TrailsBuffer.Structure>): FloatArray {
    val batch = StripBatch((structures.size * 2) * 5)
    var v = 0f
    TrailStructuresIterator.iterate(structures, {
      struct, vec ->
      emitSegment(struct.position, struct.life, vec, batch, v)
      v += 1f
    })
    return batch.toVerticesArray()
  }

  private fun emitSegment(position: Vec2, life: Float, diff: Vec2, batch: StripBatch, vCoord: Float) {
    val scaled = diff * 12
    batch.emit(position - scaled, Vec2(0f, vCoord), life)
    batch.emit(position + scaled, Vec2(1f, vCoord), life)
  }

  private class StripBatch(capacity: Int) {

    private val array = FloatArray(capacity, {0f});
    private var index = 0

    fun emit(position: Vec2, coord: Vec2, life: Float) {
      array[index++] = position.x
      array[index++] = position.y
      array[index++] = coord.x
      array[index++] = coord.y
      array[index++] = life
    }

    fun toVerticesArray() = array

  }

  private fun applyAdditiveBlending(f: () -> Unit) {
    val blending = GL20.GL_BLEND
    Gdx.gl.glEnable(blending);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
    f.invoke()
    Gdx.gl.glDisable(blending);
  }

}