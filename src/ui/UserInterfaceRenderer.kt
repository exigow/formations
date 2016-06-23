package ui

import Renderer
import com.badlogic.gdx.math.Rectangle
import commons.math.FastMath
import commons.math.Vec2
import core.Camera
import game.Collective
import game.PlayerContext
import game.Squad
import game.World
import game.orders.MoveOrder
import java.util.*


class UserInterfaceRenderer(val context: PlayerContext, val camera: Camera, val world: World) {

  private val diamondSize = 24f
  private var time = 0f
  private val animations: MutableMap<Squad, AnimationSequenceSquadBundle> = HashMap()
  private val rectangleSequence = AnimationSequence()
  private val rectangle = Rectangle()

  fun render(delta: Float) {
    updateAnimationsKeys()
    updateAnimationsStates(delta)
    time += delta
    context.selected.flatMap { it.ships }.forEach { Renderer.renderDiamond(it.position, (diamondSize - 4f) * camera.renderingScale()) }
    renderMouse()
    //world.collectives.forEach { it.render() }
    performRectangleAnimation(delta)
    for (anim in animations) {
      anim.key.ships.forEach {
        PathAnimation.render(createDiamondPaths().add(it.position), anim.value.highlight.animaionStep())
        PathAnimation.render(createCrossPaths().add(it.position), anim.value.hover.animaionStep(), divided = false)
      }
    }
  }

  fun performRectangleAnimation(delta: Float) {
    if (context.selectionRect != null) {
      rectangleSequence.show()
      rectangle.set(context.selectionRect)
      Renderer.renderRectangle(rectangle)
    } else
      rectangleSequence.hide()
    rectangleSequence.update(delta * 2)
    PathAnimation.render(createRectanglePath(rectangle.plusBorder(8f * camera.renderingScale())), rectangleSequence.animaionStep(), divided = false)
  }

  fun createDiamondPaths(): Map<Vec2, Vec2> {
    val scale = diamondSize * camera.renderingScale()
    val left = Vec2(-scale, 0)
    val right = Vec2(scale, 0)
    val up = Vec2(0, -scale)
    val down = Vec2(0, scale)
    return mapOf(
      left to up,
      up to right,
      right to down,
      down to left
    )
  }

  fun createCrossPaths(): Map<Vec2, Vec2> {
    val scale = diamondSize * camera.renderingScale()
    val div = 2
    val leftUpA = Vec2(-scale, -scale)
    val leftUpB = leftUpA / div
    val rightUpA = Vec2(scale, -scale)
    val rightUpB = rightUpA / div
    val leftDownA = Vec2(-scale, scale)
    val leftDownB = leftDownA / div
    val rightDownA = Vec2(scale, scale)
    val rightDownB = rightDownA / div
    return mapOf(
      leftUpA to leftUpB,
      rightUpA to rightUpB,
      leftDownA to leftDownB,
      rightDownA to rightDownB
    )
  }

  fun createRectanglePath(rect: Rectangle): Map<Vec2, Vec2> {
    val leftUp = Vec2(rect.x, rect.y)
    val rightUp = leftUp + Vec2(rect.width, 0)
    val leftDown = leftUp + Vec2(0, rect.height)
    val rightDown = leftUp + Vec2(rect.width, rect.height)
    val q = FastMath.pi / 4f
    return createCornerPath(leftUp, 0f)
      .plus(createCornerPath(rightUp, q * 2))
      .plus(createCornerPath(leftDown, q * 3))
      .plus(createCornerPath(rightDown, q * 4))
  }

  fun createCornerPath(where: Vec2, angle: Float): Map<Vec2, Vec2> {
    val r = Vec2.rotated(angle)
    val len = 32 * camera.renderingScale()
    val a = where + Vec2(1, 0) * len
    val b = where + Vec2(0, 1) * len
    return mapOf(a to where, b to where)
  }

  fun Map<Vec2, Vec2>.add(addition: Vec2) = this.mapKeys { addition + it.key }.mapValues { addition + it.value }

  fun updateAnimationsKeys() {
    for (s in world.allSquads())
      if (!animations.containsKey(s))
        animations.put(s, AnimationSequenceSquadBundle())
  }

  fun updateAnimationsStates(delta: Float) {
    for ((squad, state) in animations) {
      fun isHighlighted() = context.highlighted.contains(squad) //|| context.hovered == squad
      if (isHighlighted())
        state.highlight.show()
      else
        state.highlight.hide()
      fun isHovered() = context.hovered == squad
      if (isHovered())
        state.hover.show()
      else
        state.hover.hide()
      state.update(delta)
    }
  }

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

  private class AnimationSequenceSquadBundle(
    val highlight: AnimationSequence = AnimationSequence(),
    val hover: AnimationSequence = AnimationSequence()
  ) {

    fun update(delta: Float) {
      highlight.update(delta)
      hover.update(delta)
    }

  }

  fun Rectangle.plusBorder(border: Float): Rectangle {
    val w = width + border
    val h = height + border
    val x = x - (border / 2f)
    val y = y - (border / 2f)
    return Rectangle(x, y, w, h);
  }

}