package interaction

import Renderer
import com.badlogic.gdx.math.Vector2
import game.Squad
import game.World
import input.Input
import java.util.*

object Interaction {

  // todo rename all "tools" to "components" + create sub package???
  private val selectionTool = SelectionTool()
  private val movementTool = MiddleClickCameraMovementTool()
  private val highlightedSquads = ArrayList<Squad>()
  private val selectedSquads = ArrayList<Squad>()
  private var singleSelectionLock = true
  private val pointerRadius = 20f

  fun interact(world: World) {
    val select = Input.Button.MOUSE_LEFT;
    val pointer = Input.getMousePositionInWorld();
    val hoveringSquad = findPointerHoveringSquad(pointer, world);

    movementTool.update()

    if (select.isPressed())
      selectionTool.startFrom(pointer)
    if (select.isHeld()) {
      selectionTool.endTo(pointer)
      if (selectionTool.distanceFromStartingPoint() > pointerRadius)
        singleSelectionLock = false
      if (singleSelectionLock == false) {
        val squadsInsideSelection = world.findSquadsInside(selectionTool.selectionRectangle())
        highlightedSquads.clear()
        highlightedSquads.addAll(squadsInsideSelection)
        Renderer.renderRectangle(selectionTool.selectionRectangle())
      }
    }
    if (select.isReleased()) {
      if (singleSelectionLock == false) {
        flushList(highlightedSquads, selectedSquads)
        singleSelectionLock = true
      } else {
        selectedSquads.clear()
        if (hoveringSquad != null)
          selectedSquads.add(hoveringSquad);
      }
    }

    renderSquadsShips(highlightedSquads, 18f)
    renderSquadsShips(selectedSquads, 16f)
    if (hoveringSquad != null)
      renderSquadsShips(listOf(hoveringSquad), pointerRadius)
  }

  private fun <T> flushList(from: MutableList<T>, to: MutableList<T>) {
    to.clear()
    to.addAll(from)
    from.clear()
  }

  private fun findPointerHoveringSquad(pointer: Vector2, world: World): Squad? {
    val ship = world.findClosestShip(pointer)
    if (pointer.dst(ship.position) < pointerRadius)
      return world.findSquad(ship)
    return null;
  }

  private fun renderSquadsShips(squads: List<Squad>, radius: Float) {
    for (squad in squads)
      for (ship in squad.ships)
        Renderer.renderCircle(ship.position, radius)
  }

}