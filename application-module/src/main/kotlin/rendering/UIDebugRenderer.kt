package rendering

import FastMath
import Vec2
import game.PlayerContext
import game.Ship
import rendering.paths.Path
import rendering.utils.Draw

class UIDebugRenderer(private val context: PlayerContext) {

  fun render() {
    drawHovered()
    drawHighlighted()
    drawSelected()
    drawRectangle()
  }

  private fun drawHovered() {
    if (context.hovered != null) {
      val h = context.hovered!!
      h.ships.forEach { ship ->
        (1..4).forEach { t ->
          val pivot = ship.position + Vec2.rotated(t / 4f * FastMath.pi2 + FastMath.pi / 4f) * 32f
          val f = Vec2.Calculations.lerp(ship.position, pivot, .75f)
          Draw.line(f, pivot)
        }
      }
    }
  }

  private fun drawHighlighted() {
    context.highlighted.forEach {
      it.ships.forEach {
        Draw.diamondDotted(it.position, Vec2.one() * 24f, 6f)
      }
    }
  }

  private fun drawSelected() {
    context.selected.forEach {
      it.ships.forEach {
        drawSelectedShip(it)
      }
    }
  }

  fun drawRectangle() {
    if (context.selectionRect != null) {
      val s = context.selectionRect!!
      Draw.rectangle(s)
    }
  }

  private fun drawSelectedShip(ship: Ship) {
    val vertices = ship.transformedHullSprite().toVertices()
    //vertices.forEach { Draw.cross(it, 4f) }
    Draw.dart(ship.position, ship.config.size * .5f, ship.angle)
    val a = Vec2(vertices.map { it.x }.min()!!, vertices.map { it.y }.min()!!)
    val b = Vec2(vertices.map { it.x }.max()!!, vertices.map { it.y }.max()!!)
    Draw.paths(Path.of(Vec2(a.x, a.y), Vec2(b.x, a.y), Vec2(b.x, b.y), Vec2(a.x, b.y)).loop().populate(8f).slice())
  }

}