package rendering

import FastMath
import Vec2
import game.PlayerContext
import rendering.utils.Draw

class UIRenderer(private val context: PlayerContext) {

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
        Draw.diamond(it.position, Vec2.one() * 16f)
      }
    }
  }

  fun drawRectangle() {
    if (context.selectionRect != null) {
      val s = context.selectionRect!!
      Draw.rectangle(s)
    }
  }

}