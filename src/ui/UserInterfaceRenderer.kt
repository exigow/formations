package ui

import com.badlogic.gdx.math.Rectangle
import commons.math.ConvexHull
import commons.math.FastMath
import commons.math.Vec2
import core.Camera
import game.Collective
import game.PlayerContext
import game.Squad
import game.World
import rendering.Color
import rendering.Draw
import rendering.paths.Path
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
    context.selected.flatMap { it.ships }.forEach { Draw.diamond(it.position, (diamondSize - 4f) * camera.normalizedRenderingScale()) }
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
      Draw.rectangleDotted(rectangle, dotLength = camera.renderingScale() * 8f, color = Color.LIGHT_GRAY)
      Draw.rectangleFilled(rectangle, color = Color.WHITE, alpha = .075f)
    } else
      rectangleSequence.hide()
    rectangleSequence.update(delta * 2)
    val border = (-1 + rectangleSequence.animaionStep()) * 24 + 8
    PathAnimation.render(createRectanglePath(rectangle.plusBorder(border * camera.renderingScale())), rectangleSequence.animaionStep(), divided = false)
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
    val q = FastMath.pi / 2f
    val len = 32 * camera.renderingScale()
    val diagonalLength = leftUp.distanceTo(rightDown)
    val fixedLen = Math.min(len, diagonalLength / 4)
    return createCornerPath(leftUp, q * 4, fixedLen)
      .plus(createCornerPath(rightUp, q, fixedLen))
      .plus(createCornerPath(leftDown, q * 3, fixedLen))
      .plus(createCornerPath(rightDown, q * 2, fixedLen))
  }

  fun createCornerPath(where: Vec2, angle: Float, len: Float): Map<Vec2, Vec2> {
    val a = where + Vec2.rotated(angle) * len
    val b = where + Vec2.rotated(angle + FastMath.pi / 2) * len
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
    renderConvexHull()
    if (!this.orders.isEmpty()) {
      val order = this.orders.first()
      /*if (order is MoveOrder)
        Draw.lineDotted(center(), order.where, 8f)
      if (order is AttackOrder)
        Draw.line(center(), order.who.center())*/
    }
  }

  private fun Collective.renderConvexHull() {
    val positions = this.squads.flatMap { it.ships }.map { it.position }
    val hull = ConvexHull.calculate(positions)
    Draw.paths(Path(hull).populate(camera.normalizedRenderingScale() * 16f).slice(), color = Color.GRAY)
  }

  fun renderMouse() = Draw.diamond(camera.mousePosition(), camera.renderingScale() * 8f)

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