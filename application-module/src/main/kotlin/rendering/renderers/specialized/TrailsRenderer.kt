package rendering.renderers.specialized

import Vec2
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.math.Matrix4
import rendering.Blending
import rendering.Color
import rendering.materials.Material
import rendering.trails.Trail
import rendering.trails.TrailIterator

class TrailsRenderer {

  private val mesh = VboUtils.createCommonVbo(1024)

  fun render(trail: Trail, material: Material, matrix: Matrix4) {
    Blending.ADDITIVE.decorate {
      mesh.setVertices(calculateTrailArray(trail.list))
      VboUtils.paintWithMaterialShader(material, matrix, Color.white, {
        shader -> mesh.render(shader, GL20.GL_TRIANGLE_STRIP)
      })
    }
  }

  private fun calculateTrailArray(structures: List<Trail.Structure>): FloatArray {
    val batch = StripBatch((structures.size * 2) * 6)
    var v = 0f
    TrailIterator.iterate(structures, {
      struct, vec ->
      emitSegment(struct.position, struct.life, struct.width, vec, batch, v)
      v += 1f
    })
    return batch.toVerticesArray()
  }

  private fun emitSegment(position: Vec2, life: Float, width: Float, diff: Vec2, batch: StripBatch, vCoord: Float) {
    val scaled = diff * width
    batch.emit(position - scaled, Vec2(0f, vCoord), life)
    batch.emit(position + scaled, Vec2(1f, vCoord), life)
  }

  private class StripBatch(capacity: Int) {

    private val array = FloatArray(capacity, {0f})
    private var index = 0

    fun emit(position: Vec2, coord: Vec2, life: Float) {
      array[index++] = position.x
      array[index++] = position.y
      array[index++] = 0f
      array[index++] = coord.x
      array[index++] = coord.y
      array[index++] = life
    }

    fun toVerticesArray() = array

  }

}