package rendering

import com.badlogic.gdx.math.Rectangle
import commons.math.Vec2
import core.Camera
import rendering.Paths.PathFactory
import rendering.shapes.Path

object Draw {

  private val defaultColor = Color.WHITE
  private val renderer = LineRenderer()

  fun startRendering(camera: Camera) = renderer.begin(camera.projectionMatrix())

  fun present() = renderer.end()

  fun line(from: Vec2, to: Vec2, color: Color = defaultColor) = render(
    PathFactory.line(from, to),
    color
  )

  fun lineDotted(from: Vec2, to: Vec2, dotLength: Float, color: Color = defaultColor) = render(
    PathFactory.line(from, to).populate(dotLength).slice(),
    color
  )

  fun dart(position: Vec2, scale: Float = 16f, angle: Float = 0f) = render(
    PathFactory.dart().rotate(angle).scale(Vec2.scaled(scale)).translate(position),
    Color.WHITE
  )

  fun cross(center: Vec2, size: Float, color: Color = defaultColor) = render(
    PathFactory.cross().scale(Vec2.scaled(size)).translate(center),
    color
  )

  fun grid(center: Vec2 = Vec2.zero(), size: Vec2, density: Int, color: Color = defaultColor) = render(
    PathFactory.grid(density).scale(size).translate(center - size / 2),
    color
  )

  fun arc(center: Vec2, radius: Float, start: Float, end: Float, quality: Int = 8, color: Color = defaultColor) = render(
    PathFactory.arc(start, end, quality).scale(Vec2.scaled(radius)).populate(8f).slice().translate(center),
    color
  )

  fun rectangle(rect: Rectangle, color: Color = defaultColor) = render(
    PathFactory.square().scale(Vec2(rect.width, rect.height)).populate(16f).slice().translate(Vec2(rect.x, rect.y)),
    color
  )

  fun cone(center: Vec2, radius: Float, start: Float, end: Float, quality: Int = 8, color: Color = defaultColor) = render(
    PathFactory.cone(start, end, quality).scale(Vec2.scaled(radius)).translate(center),
    color
  )

  fun diamond(center: Vec2, scale: Float, color: Color = defaultColor) = diamond(center, Vec2(scale, scale), color)

  fun diamond(center: Vec2, scale: Vec2, color: Color = defaultColor) = render(
    PathFactory.diamond().scale(scale).translate(center),
    color
  )

  fun polygonLooped(positions: Array<Vec2>, color: Color = defaultColor) = fromEachToEachLooped(positions, { from, to -> line(from, to, color)})

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

  private fun List<Path>.scale(scalar: Vec2) = this.map { it.scale(scalar) }

  private fun List<Path>.translate(translation: Vec2) = this.map { it.translate(translation) }

  private fun render(paths: List<Path>, color: Color) = paths.forEach { render(it, color) }

  private fun render(path: Path, color: Color) {
    val i = path.elements.iterator()
    var prev = i.next()
    while (i.hasNext()) {
      val next = i.next()
      renderer.emitLine(prev, next, color)
      prev = next
    }
  }

}