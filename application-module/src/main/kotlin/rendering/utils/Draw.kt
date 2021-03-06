package rendering.utils

import com.badlogic.gdx.math.Rectangle
import Vec2
import core.Camera
import Color
import rendering.Paths.PathFactory
import rendering.paths.Path
import rendering.paths.PathRenderer

object Draw {

  private val pr = PathRenderer()

  fun update(camera: Camera) = pr.update(camera)

  fun line(from: Vec2, to: Vec2, color: Color = Color.white, alpha: Float = 1f) = pr.renderLine(
    PathFactory.line(from, to),
    color, alpha
  )

  fun lineDotted(from: Vec2, to: Vec2, dotLength: Float, color: Color = Color.white, alpha: Float = 1f) = pr.renderLines(
    PathFactory.line(from, to).populate(dotLength).slice(),
    color, alpha
  )

  fun dartDotted(position: Vec2, scale: Float = 16f, angle: Float = 0f, dotLength: Float, color: Color = Color.white, alpha: Float = 1f) = pr.renderLines(
    PathFactory.dart().rotate(angle).scale(Vec2.scaled(scale)).translate(position).populate(dotLength).slice(),
    color, alpha
  )

  fun dart(position: Vec2, scale: Float = 16f, angle: Float = 0f, color: Color = Color.white, alpha: Float = 1f) = pr.renderLine(
    PathFactory.dart().rotate(angle).scale(Vec2.scaled(scale)).translate(position),
    color, alpha
  )

  fun dartFilled(position: Vec2, scale: Float = 16f, angle: Float = 0f, color: Color = Color.white, alpha: Float = 1f) = pr.renderFilled(
    PathFactory.dart().rotate(angle).scale(Vec2.scaled(scale)).translate(position),
    color, alpha
  )

  fun cross(center: Vec2, size: Float, color: Color = Color.white, alpha: Float = 1f) = pr.renderLines(
    PathFactory.cross().scale(Vec2.scaled(size)).translate(center),
    color, alpha
  )

  fun grid(center: Vec2 = Vec2.zero(), size: Vec2, density: Int, color: Color = Color.white, alpha: Float = 1f) = pr.renderLines(
    PathFactory.grid(density).scale(size).translate(center - size / 2),
    color, alpha
  )

  fun arcDotted(center: Vec2, radius: Float, start: Float, end: Float, quality: Int = 8, dotLength: Float, color: Color = Color.white, alpha: Float = 1f) = pr.renderLines(
    PathFactory.arc(start, end, quality).scale(Vec2.scaled(radius)).populate(dotLength).slice().translate(center),
    color, alpha
  )

  fun arc(center: Vec2, radius: Float, start: Float, end: Float, quality: Int = 8, color: Color = Color.white, alpha: Float = 1f) = pr.renderLine(
    PathFactory.arc(start, end, quality).scale(Vec2.scaled(radius)).translate(center),
    color, alpha
  )

  fun circle(center: Vec2, radius: Float, quality: Int = 8, color: Color = Color.white, alpha: Float = 1f) =
    arc(center, radius, 0f, FastMath.pi2, quality, color, alpha)

  fun rectangleDotted(rect: Rectangle, dotLength: Float, color: Color = Color.white, alpha: Float = 1f) = pr.renderLines(
    PathFactory.square().scale(Vec2(rect.width, rect.height)).populate(dotLength).slice().translate(Vec2(rect.x, rect.y)),
    color, alpha
  )

  fun rectangle(rect: Rectangle, color: Color = Color.white, alpha: Float = 1f) = pr.renderLine(
    PathFactory.square().scale(Vec2(rect.width, rect.height)).translate(Vec2(rect.x, rect.y)),
    color, alpha
  )

  fun rectangleFilled(rect: Rectangle, color: Color = Color.white, alpha: Float = 1f) = pr.renderFilled(
    PathFactory.square().scale(Vec2(rect.width, rect.height)).translate(Vec2(rect.x, rect.y)),
    color, alpha
  )

  fun cone(center: Vec2, radius: Float, start: Float, end: Float, quality: Int = 8, color: Color = Color.white, alpha: Float = 1f) = pr.renderLine(
    PathFactory.cone(start, end, quality).scale(Vec2.scaled(radius)).translate(center),
    color, alpha
  )

  fun diamond(center: Vec2, scale: Float, color: Color = Color.white, alpha: Float = 1f) = diamond(center, Vec2(scale, scale), color, alpha)

  fun diamond(center: Vec2, scale: Vec2, color: Color = Color.white, alpha: Float = 1f) = pr.renderLine(
    PathFactory.diamond().scale(scale).translate(center),
    color, alpha
  )

  fun diamondDotted(center: Vec2, scale: Vec2, dotLength: Float, color: Color = Color.white, alpha: Float = 1f) = pr.renderLines(
    PathFactory.diamond().scale(scale).translate(center).populate(dotLength).slice(),
    color, alpha
  )

  fun path(path: Path, color: Color = Color.white, alpha: Float = 1f) = pr.renderLine(path, color, alpha)

  fun pathFilled(path: Path, color: Color = Color.white, alpha: Float = 1f) = pr.renderFilled(path, color, alpha)

  fun paths(paths: List<Path>, color: Color = Color.white, alpha: Float = 1f) = pr.renderLines(paths, color, alpha)

  private fun List<Path>.scale(scalar: Vec2) = map { it.scale(scalar) }

  private fun List<Path>.translate(translation: Vec2) = map { it.translate(translation) }

}