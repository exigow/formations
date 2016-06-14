package core.actions.catalog.selecting

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import core.Camera
import core.Logger
import core.actions.Action
import core.input.event.bundles.ThreeStateButtonEventBundle
import core.input.mappings.MouseButton
import game.Squad
import game.World

class SelectionAction(val cameraDep: Camera, val world: World, val highlighted: MutableList<Squad>, val selected: MutableList<Squad>) : Action {

  private var time = 0f
  private var clickCounter = 0
  private val draggingPoint = Vector2()
  private var isDragging = false
  private val selectionRect = SelectionRectangle()

  private val events = object : ThreeStateButtonEventBundle(MouseButton.MOUSE_LEFT) {

    override fun onPress() {
      draggingPoint.set(cameraDep.mousePosition())
      isDragging = false
      if (isHoveringSomething()) {
        clickCounter += 1
        time = 0f
        val squad = findHoveringSquad()
        when (clickCounter) {
          1 -> hoverSingleSquad(squad)
          2 -> hoverVisibleSquads()
          3 -> hoverAllSquads()
        }
        if (!hasStillTime()) {
          time = 0f;
          clickCounter = 0
        }
      } else {
        onBackgroundClick()
      }
    }

    override fun onRelease() {
      isDragging = false
      selected.clear()
      if (!highlighted.isEmpty()) {
        Logger.ACTION.log("Selecting highlighted squads.")
        selected.addAll(highlighted)
        highlighted.clear()
      }
    }

    override fun onHold(delta: Float) {
      val dragLength = draggingPoint.dst(cameraDep.mousePosition())
      if (dragLength > 16f && !isDragging) {
        isDragging = true
        Logger.ACTION.log("Highlighting with rectangle (dragging).")
      }
      if (isDragging) {
        selectionRect.startFrom(draggingPoint)
        selectionRect.endTo(cameraDep.mousePosition())
        val insideRect = world.findSquadsInside(selectionRect.selectionRectangle())
        highlighted.clear()
        highlighted.addAll(insideRect)
      }
    }

    override fun onTick(delta: Float) {
      time += delta
      if (!hasStillTime())
        clickCounter = 0
    }

  }.toBundle()

  fun selectionRectangle(): Rectangle? {
    val rect = selectionRect.selectionRectangle()
    if (isDragging)
      return rect
    return null
  }

  fun onBackgroundClick() {
    Logger.ACTION.log("Background click.")
  }

  fun hoverSingleSquad(squad: Squad) {
    highlighted.clear()
    highlighted.add(squad)
    Logger.ACTION.log("Highlighting single squad (single click).")
  }

  fun hoverVisibleSquads() {
    val visible = world.findSquadsInside(cameraDep.worldVisibilityRectangle(-128f))
    highlighted.clear()
    highlighted.addAll(visible)
    Logger.ACTION.log("Highlighting camera-visible squads (double click).")
  }

  fun hoverAllSquads() {
    highlighted.clear()
    highlighted.addAll(world.squads)
    Logger.ACTION.log("Highlighting all squads (triple click).")
  }

  private fun isHoveringSomething() = findHoveringShip() != null

  private fun findHoveringSquad(): Squad {
    val ship = findHoveringShip()
    return world.findSquad(ship!!)
  }

  private fun findHoveringShip() = world.findClosestShipInMaxRadius(cameraDep.mousePosition(), cameraDep.scaledClickRadius())

  private fun hasStillTime() = time < .25f

  override fun events() = events

}