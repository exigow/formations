package rendering

import com.badlogic.gdx.math.Rectangle
import commons.math.Vec2
import core.Camera
import rendering.Paths.PathFactory
import rendering.shapes.Path

object Draw {

  private val pr = PathRenderer()

  fun update(camera: Camera) = pr.update(camera)

  fun line(from: Vec2, to: Vec2, color: Color = Color.WHITE, alpha: Float = 1f) = pr.renderLine(
    PathFactory.line(from, to),
    color, alpha
  )

  fun lineDotted(from: Vec2, to: Vec2, dotLength: Float, color: Color = Color.WHITE, alpha: Float = 1f) = pr.renderLines(
    PathFactory.line(from, to).populate(dotLength).slice(),
    color, alpha
  )

  fun dart(position: Vec2, scale: Float = 16f, angle: Float = 0f, color: Color = Color.WHITE, alpha: Float = 1f) = pr.renderLine(
    PathFactory.dart().rotate(angle).scale(Vec2.scaled(scale)).translate(position),
    color, alpha
  )

  fun dartFilled(position: Vec2, scale: Float = 16f, angle: Float = 0f, color: Color = Color.WHITE, alpha: Float = 1f) = pr.renderFilled(
    PathFactory.dart().rotate(angle).scale(Vec2.scaled(scale)).translate(position),
    color, alpha
  )

  fun cross(center: Vec2, size: Float, color: Color = Color.WHITE, alpha: Float = 1f) = pr.renderLines(
    PathFactory.cross().scale(Vec2.scaled(size)).translate(center),
    color, alpha
  )

  fun grid(center: Vec2 = Vec2.zero(), size: Vec2, density: Int, color: Color = Color.WHITE, alpha: Float = 1f) = pr.renderLines(
    PathFactory.grid(density).scale(size).translate(center - size / 2),
    color, alpha
  )

  fun arc(center: Vec2, radius: Float, start: Float, end: Float, quality: Int = 8, color: Color = Color.WHITE, alpha: Float = 1f) = pr.renderLines(
    PathFactory.arc(start, end, quality).scale(Vec2.scaled(radius)).populate(8f).slice().translate(center),
    color, alpha
  )

  fun rectangle(rect: Rectangle, color: Color = Color.WHITE, alpha: Float = 1f) = pr.renderLines(
    PathFactory.square().scale(Vec2(rect.width, rect.height)).populate(16f).slice().translate(Vec2(rect.x, rect.y)),
    color, alpha
  )

  fun cone(center: Vec2, radius: Float, start: Float, end: Float, quality: Int = 8, color: Color = Color.WHITE, alpha: Float = 1f) = pr.renderLine(
    PathFactory.cone(start, end, quality).scale(Vec2.scaled(radius)).translate(center),
    color, alpha
  )

  fun diamond(center: Vec2, scale: Float, color: Color = Color.WHITE, alpha: Float = 1f) = diamond(center, Vec2(scale, scale), color, alpha)

  fun diamond(center: Vec2, scale: Vec2, color: Color = Color.WHITE, alpha: Float = 1f) = pr.renderLine(
    PathFactory.diamond().scale(scale).translate(center),
    color, alpha
  )

  fun polygonLooped(positions: Array<Vec2>, color: Color = Color.WHITE) = fromEachToEachLooped(positions, { from, to -> line(from, to, color)})

  inline private fun fromEachToEachLooped(positions: Array<Vec2>, drawWith: (from: Vec2, to: Vec2) -> Unit) {
    val i = positions.iterator()
    var prev = i.next()
    val first = prev
    while (i.hasNext()) {
      val next = i.next()
      drawWith.invoke(prev, next)
      prev = next
    }
    drawWith.invoke(first, prev)
  }

  private fun List<Path>.scale(scalar: Vec2) = map { it.scale(scalar) }

  private fun List<Path>.translate(translation: Vec2) = map { it.translate(translation) }

}