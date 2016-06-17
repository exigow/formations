package core.actions.catalog.selecting

import com.badlogic.gdx.math.Rectangle
import commons.Logger
import commons.math.Vec2
import core.Camera
import core.actions.Action
import core.actions.catalog.CameraMiddleClickMovementAction
import core.input.event.bundles.ThreeStateButtonEventBundle
import core.input.mappings.MouseButton
import game.PlayerContext
import game.Squad
import game.World

class SelectionAction(val cameraDep: Camera, val world: World, val context: PlayerContext) : Action {

  private var time = 0f
  private var clickCounter = 0
  private var draggingPoint = Vec2.zero()
  private var isDragging = false
  private val selectionRect = SelectionRectangle()

  private val events = object : ThreeStateButtonEventBundle(MouseButton.MOUSE_LEFT) {

    override fun onPress() {
      draggingPoint = cameraDep.mousePosition()
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
      if (!context.highlighted.isEmpty()) {
        Logger.ACTION.log("Selecting highlighted squads.")
        context.migrateHighlightToSelection()
      }
    }

    override fun onHold(delta: Float) {
      val dragLength = draggingPoint.distanceTo(cameraDep.mousePosition())
      if (dragLength > 16f && !isDragging) {
        isDragging = true
        Logger.ACTION.log("Highlighting with rectangle (dragging).")
      }
      if (isDragging) {
        selectionRect.updatePivots(from = draggingPoint, to = cameraDep.mousePosition())
        val insideRect = world.findSquadsInside(selectionRect.selectionRectangle())
        context.replaceHighlightedWith(insideRect)
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
    context.clearSelection()
    Logger.ACTION.log("Background click. Clearing selection.")
  }

  fun hoverSingleSquad(squad: Squad) {
    context.replaceHighlightedWith(listOf(squad))
    Logger.ACTION.log("Highlighting single squad (single click).")
  }

  fun hoverVisibleSquads() {
    val visible = world.findSquadsInside(cameraDep.worldVisibilityRectangle(-128f))
    context.replaceHighlightedWith(visible)
    Logger.ACTION.log("Highlighting camera-visible squads (double click).")
  }

  fun hoverAllSquads() {
    context.replaceHighlightedWith(world.squads)
    Logger.ACTION.log("Highlighting all squads (triple click).")
  }

  private fun isHoveringSomething() = findHoveringShip() != null

  private fun findHoveringSquad(): Squad {
    val ship = findHoveringShip()
    return world.findSquad(ship!!)
  }

  private fun findHoveringShip() = world.findClosestShipInMaxRadius(cameraDep.mousePosition(), cameraDep.scaledClickRadius())

  private fun hasStillTime() = time < .25f // in seconds

  override fun events() = events

  override fun discardOn() = setOf(CameraMiddleClickMovementAction::class)

  override fun isWorking() = isDragging

}