package rendering.renderers.specialized

import assets.AssetsManager
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.math.Matrix4
import commons.math.Vec2
import rendering.Blending
import rendering.GBuffer
import rendering.materials.Material
import rendering.trails.Trail
import rendering.trails.TrailIterator

class TrailsRenderer(private val gbuffer: GBuffer) {

  private val mesh = Mesh(true, 1024, 0,
    VertexAttribute(VertexAttributes.Usage.Position, 2, "positionAttr"),
    VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "texCoordAttr"),
    VertexAttribute(VertexAttributes.Usage.Generic, 1, "lifeAttr")
  );

  fun render(trail: Trail, material: Material, matrix: Matrix4) {
    Blending.ADDITIVE.decorate {
      mesh.setVertices(calculateTrailArray(trail.list))
      fun paint(withTexture: Texture, shaderName: String) {
        val shader = AssetsManager.peekShader(shaderName)
        withTexture.bind(0)
        shader.begin();
        shader.setUniformMatrix("projection", matrix);
        shader.setUniformi("texture", 0);
        mesh.render(shader, GL20.GL_TRIANGLE_STRIP);
        shader.end();
      }
      gbuffer.paintOnDiffuse {
        paint(material.diffuse!!, "trailShader")
      }
      gbuffer.paintOnEmissive {
        paint(material.emissive!!, "trailShaderEmissive")
      }
    }
  }

  private fun calculateTrailArray(structures: List<Trail.Structure>): FloatArray {
    val batch = StripBatch((structures.size * 2) * 5)
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

}