package rendering

import core.Camera
import game.PlayerContext
import game.Ship
import Vec2
import game.Squad
import rendering.renderers.Renderable
import rendering.utils.Draw


class UIRenderer(private val camera: Camera, private val context: PlayerContext) {

  private val uiColor = Color.fromInteger(116, 187, 193)

  fun render(): Collection<Renderable> {
    var result = emptyList<Renderable>()
    val alpha = calcSelectionAlpha()
    context.selected.forEach {
      it.ships.forEach {
        result += drawSelectionBorderSprites(it, alpha)
        result += Sprite("selection-icon", it.position, Vec2.one() * camera.normalizedRenderingScale())
      }
    }
    result += drawSelectionRectangle()
    if (context.isHovering())
      result += drawHoveredSquad(context.hovered!!)
    return result
  }

  private fun drawHoveredSquad(squad: Squad) = squad.ships
    .map { Sprite("selection-icon", it.position, Vec2.one() * camera.normalizedRenderingScale() * 2f) }

  private fun drawSelectionBorderSprites(ship: Ship, alpha: Float): Collection<Sprite> {
    var tempAngle = 0f
    return ship.transformedHullSprite().toVertices().map {
      val scale = Vec2(1f, -1f) * camera.normalizedRenderingScale()
      tempAngle -= FastMath.pi / 2
      val angle = tempAngle + ship.angle
      Sprite("selection-border", it, scale, angle, alpha = alpha)
    }
  }

  private fun calcSelectionAlpha(): Float {
    val pivot = 4f
    val width = 1f
    val linear = 1f + Math.max(pivot - Math.max(pivot, camera.normalizedRenderingScale()), -width) / width
    return FastMath.pow(linear, 2f)
  }

  private fun drawSelectionRectangle(): Collection<Renderable> {
    if (context.selectionRect != null) {
      val s = context.selectionRect!!
      return listOf(ImmediateDrawCall( {
        Draw.rectangleFilled(s, uiColor, .125f)
        Draw.rectangle(s, uiColor)
      }))
    }
    return emptyList()
  }

}