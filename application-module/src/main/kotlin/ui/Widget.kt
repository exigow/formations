package ui

import Vec2
import rendering.Blending
import rendering.Color
import rendering.paths.Path
import rendering.utils.Draw

class Widget(private val from: Vec2, private val to: Vec2) {

  //private var state = State.RESTING
  private var fixedFrom = from
  private var fixedTo = to
  private var highlightTarget = 0f
  private var highlightPresent = 0f
  private val borderHeight = 2
  private var activated = false
  private var activatedTarget = 0f
  private var activatedPresent = 0f

  fun update(delta: Float) {
    highlightPresent += (highlightTarget - highlightPresent) * .25f
    activatedTarget = when (activated) {
      false -> 0f
      true -> 1f
    }
    activatedPresent += (activatedTarget - activatedPresent) * .25f
    updateFixed()
    highlightTarget = 0f
  }

  fun draw() {
    drawFilled(fixedFrom, fixedTo, Color.black, .5f)
    drawFilled(fixedFrom, fixedTo, Color.white, activatedPresent * .75f)
    drawBorders(fixedFrom, fixedTo)

    //Draw.paths(rectanglePath().populate(4f).slice())
  }

  fun click() {
    println("click")
    activated = !activated
  }

  private fun updateFixed() {
    val hvec = Vec2(0, highlightPresent * 16)
    fixedFrom = from - hvec
    fixedTo = to
  }

  private fun drawBorders(f: Vec2, t: Vec2) {
    drawFilled(f + Vec2(0, borderHeight), f.onlyY() + t.onlyX(), Color.white, 1f)
    drawFilled(Vec2(f.x, t.y) - Vec2(0, borderHeight), t, Color.white, 1f)
  }

  fun hover() {
    highlightTarget = 1f
  }

  fun isHovered(mouse: Vec2) = (mouse.x > fixedFrom.x && mouse.x < fixedTo.x) && (mouse.y > fixedFrom.y && mouse.y < fixedTo.y)

  private fun rectanglePath() = Path.of(fixedFrom, Vec2(fixedTo.x, fixedFrom.y), fixedTo, Vec2(fixedFrom.x, fixedTo.y)).loop()

  private fun drawFilled(f: Vec2, t: Vec2, color: Color, alpha: Float) = Draw.pathFilled(
    Path.of(f, Vec2(t.x, f.y), t, Vec2(f.x, t.y)).loop(), color, alpha)

  /*enum class State {

    RESTING, HOVERED, ACTIVE

  }*/

}