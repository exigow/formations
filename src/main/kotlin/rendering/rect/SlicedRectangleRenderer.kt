package rendering.rect

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Matrix4
import commons.math.Vec2
import rendering.Draw

object SlicedRectangleRenderer {

  private val batch = SpriteBatch()

  fun render(from: Vec2, to: Vec2, texture: Texture, matrix: Matrix4) {
    batch.projectionMatrix = matrix
    batch.begin()
    val width = to.x - from.x
    val height = to.y - from.y
    batch.draw(texture, from.x, from.y, width, height)
    batch.end()

    val widthSlice = calculateSlice(from.x, to.x, texture.width / 3f)
    val heightSlice = calculateSlice(from.y, to.y, texture.height / 3f)

    drawVectical(Vec2(widthSlice[0], 0))
    drawVectical(Vec2(widthSlice[1], 0))
    drawVectical(Vec2(widthSlice[2], 0))
    drawVectical(Vec2(widthSlice[3], 0))

    drawVectical(Vec2(0, heightSlice[0]))
    drawVectical(Vec2(0, heightSlice[1]))
    drawVectical(Vec2(0, heightSlice[2]))
    drawVectical(Vec2(0, heightSlice[3]))
  }

  private fun drawVectical(where: Vec2) {
    Draw.diamond(where, 4f)
  }

  private fun calculateSlice(from: Float, to: Float, segment: Float): FloatArray {
    val length = to - from
    val min = Math.min(segment, length / 2f)
    return floatArrayOf(
      from, from + min, to - min, to
    )
  }

}