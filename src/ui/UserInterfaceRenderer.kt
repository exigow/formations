package ui

import Renderer
import com.badlogic.gdx.math.Rectangle
import core.Camera
import game.Collective
import game.PlayerContext
import game.Squad
import game.World
import game.orders.MoveOrder
import java.util.*


class UserInterfaceRenderer(val context: PlayerContext, val camera: Camera, val world: World) {

  private var time = 0f
  private val animations: MutableMap<Squad, AnimationSequence> = HashMap()

  fun render(delta: Float) {
    updateAnimationsKeys()
    updateAnimationsStates(delta)
    time += delta
    context.selected.flatMap { it.ships }.forEach { Renderer.renderDiamond(it.position, 24f) }
    //context.highlighted.flatMap { it.ships }.forEach { Renderer.renderCircle(it.position, 24f, 4) }
    //context.hovered?.ships?.forEach { Renderer.renderCircle(it.position, 28f, 16) }
    renderMouse()
    //world.collectives.forEach { it.render() }
    context.selectionRect?.render()
    for (anim in animations) {
      anim.key.ships.forEach { SelectionAnimation.render(it.position, anim.value.animaionStep()) }
    }
  }

  fun updateAnimationsKeys() {
    for (s in world.allSquads())
      if (!animations.containsKey(s))
        animations.put(s, AnimationSequence())
  }

  fun updateAnimationsStates(delta: Float) {
    for ((squad, state) in animations) {
      fun isHighlighted() = context.highlighted.contains(squad) || context.hovered == squad
      if (isHighlighted())
        state.show()
      else
        state.hide()
      state.update(delta)
    }
  }

  fun Rectangle.render() = Renderer.renderRectangle(this)

  fun Collective.render() {
    val positions = this.squads.flatMap { it.ships }.map { it.position }
    Renderer.renderConvexHull(positions)
    if (!this.orders.isEmpty()) {
      val iter = this.orders.iterator()
      var prev = this.center()
      while (iter.hasNext()) {
        val next = (iter.next() as MoveOrder).where
        Renderer.renderLineArrow(prev, next)
        prev = next
      }
    }
  }

  fun renderMouse() {
    val pos = camera.mousePosition()
    val radius = camera.scaledClickRadius()
    Renderer.renderCross(pos, radius)
  }

}