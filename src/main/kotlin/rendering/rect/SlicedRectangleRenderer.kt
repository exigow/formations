package rendering.rect

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Matrix4
import commons.math.Vec2

object SlicedRectangleRenderer {

  private val batch = SpriteBatch()

  fun render(from: Vec2, to: Vec2, texture: Texture, matrix: Matrix4) {
    batch.projectionMatrix = matrix
    batch.begin()

    fun renderSegment(segment: Segment) {
      val region = TextureRegion(texture, segment.textureFrom.x, segment.textureFrom.y, segment.textureTo.x, segment.textureTo.y)
      region.flip(false, true)
      batch.draw(region, segment.from.x, segment.from.y, segment.to.x - segment.from.x, segment.to.y - segment.from.y)
    }

    val widthSlice = calculateSlice(from.x, to.x, texture.width / 3f)
    val heightSlice = calculateSlice(from.y, to.y, texture.height / 3f)

    val part = 1f / 3f

    val a = Segment(Vec2(widthSlice[0], heightSlice[0]), Vec2(widthSlice[1], heightSlice[1]), Vec2(0f, 0f), Vec2(part, part))
    val b = Segment(Vec2(widthSlice[1], heightSlice[0]), Vec2(widthSlice[2], heightSlice[1]), Vec2(part, 0f), Vec2(part * 2, part))
    val c = Segment(Vec2(widthSlice[2], heightSlice[0]), Vec2(widthSlice[3], heightSlice[1]), Vec2(part * 2, 0f), Vec2(1f, part))

    val d = Segment(Vec2(widthSlice[0], heightSlice[1]), Vec2(widthSlice[1], heightSlice[2]), Vec2(0f, part), Vec2(part, part * 2))
    val e = Segment(Vec2(widthSlice[1], heightSlice[1]), Vec2(widthSlice[2], heightSlice[2]), Vec2(part, part), Vec2(part * 2, part * 2))
    val f = Segment(Vec2(widthSlice[2], heightSlice[1]), Vec2(widthSlice[3], heightSlice[2]), Vec2(part * 2, part), Vec2(1f, part * 2))

    val g = Segment(Vec2(widthSlice[0], heightSlice[2]), Vec2(widthSlice[1], heightSlice[3]), Vec2(0f, part * 2), Vec2(part, 1f))
    val h = Segment(Vec2(widthSlice[1], heightSlice[2]), Vec2(widthSlice[2], heightSlice[3]), Vec2(part, part * 2), Vec2(part * 2, 1f))
    val i = Segment(Vec2(widthSlice[2], heightSlice[2]), Vec2(widthSlice[3], heightSlice[3]), Vec2(part * 2, part * 2), Vec2(1f, 1f))

    renderSegment(a)
    renderSegment(b)
    renderSegment(c)

    renderSegment(d)
    renderSegment(e)
    renderSegment(f)

    renderSegment(g)
    renderSegment(h)
    renderSegment(i)

    batch.end()
  }


  private fun calculateSlice(from: Float, to: Float, segment: Float): FloatArray {
    val length = to - from
    val min = Math.min(segment, length / 2f)
    return floatArrayOf(
      from, from + min, to - min, to
    )
  }

  private data class Segment(val from: Vec2, val to: Vec2, val textureFrom: Vec2 = Vec2.zero(), val textureTo: Vec2 = Vec2.one())

}