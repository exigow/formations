package core.actions.catalog

import com.badlogic.gdx.math.Rectangle
import commons.Logger
import commons.math.Vec2
import core.Camera
import core.actions.Action
import core.actions.catalog.CameraMiddleClickMovementAction
import core.actions.catalog.utils.DraggingTool
import core.actions.catalog.utils.SelectionRectangle
import core.input.event.bundles.ThreeStateButtonEventBundle
import core.input.mappings.MouseButton
import game.PlayerContext
import game.Squad
import game.World

class SelectionAction(val cameraDep: Camera, val world: World, val context: PlayerContext) : Action {

  private var time = 0f
  private var clickCounter = 0
  private val draggingTool = DraggingTool()
  private val selectionRect = SelectionRectangle()
  private var previousSquad: Squad? = null
  private val events = object : ThreeStateButtonEventBundle(MouseButton.MOUSE_LEFT) {

    override fun onPress() {
      updateHover()
      draggingTool.pinTo(cameraDep.mousePosition())
      if (context.hovered != null) {
        val squad = context.hovered!!
        if (squad != previousSquad)
          resetClickingCombo()
        previousSquad = squad
        clickCounter += 1
        time = 0f
        when (clickCounter) {
          1 -> hoverSingleSquad(squad)
          2 -> hoverVisibleSquads()
          3 -> hoverAllSquads()
        }
        if (!hasStillTime())
          resetClickingCombo()
      } else {
        onBackgroundClick()
      }
    }

    override fun onRelease() {
      draggingTool.reset()
      if (!context.highlighted.isEmpty()) {
        Logger.ACTION.log("Selecting highlighted squads.")
        context.migrateHighlightToSelection()
      }
    }

    override fun onHold(delta: Float) {
      draggingTool.update(cameraDep.mousePosition(), cameraDep.scaledClickRadius())
      if (draggingTool.isDragging()) {
        selectionRect.updatePivots(from = draggingTool.startingPosition(), to = cameraDep.mousePosition())
        val insideRect = world.findSquadsInside(selectionRect.selectionRectangle())
        context.replaceHighlightedWith(insideRect)
      }
    }

    override fun onTick(delta: Float) {
      updateHover()
      time += delta
      if (!hasStillTime())
        resetClickingCombo()
    }

  }.toBundle()

  private fun resetClickingCombo() {
    time = 0f;
    clickCounter = 0
  }

  fun selectionRectangle(): Rectangle? {
    val rect = selectionRect.selectionRectangle()
    if (draggingTool.isDragging())
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
    context.replaceHighlightedWith(world.collectives.flatMap { it.squads })
    Logger.ACTION.log("Highlighting all squads (triple click).")
  }

  private fun updateHover() = context.updateHover(findHoveringSquad())

  private fun findHoveringSquad(): Squad? {
    val ship = findHoveringShip()
    if (ship != null)
      return world.findSquadOf(ship)
    return null
  }

  private fun findHoveringShip() = world.findClosestShipInMaxRadius(cameraDep.mousePosition(), cameraDep.scaledClickRadius())

  private fun hasStillTime() = time < .25f // in seconds

  override fun events() = events

  override fun discardOn() = setOf(CameraMiddleClickMovementAction::class)

  override fun isWorking() = draggingTool.isDragging()

}