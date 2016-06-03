package interaction

import Renderer
import com.badlogic.gdx.math.Vector2
import game.Squad
import game.World
import input.Input
import java.util.*

object Interaction {

  //private val movementPivot = Vector2()
  private val selection = SelectionTool()
  private val highlightedSquads = ArrayList<Squad>()
  private val selectedSquads = ArrayList<Squad>()
  private var singleSelectionLock = true

  fun interact(world: World) {
    val select = Input.Button.MOUSE_LEFT;
    val pointer = Input.getMousePositionInWorld();
    val hoveringSquad = findPointerHoveringSquad(pointer, world);
    /*if (Input.Button.MOUSE_RIGHT.isPressed()) {
      movementPivot.set(Camera.position()).add(Input.getMousePositionInWorld());
      Camera.zoomTo(.925f)
    }
    if (Input.Button.MOUSE_RIGHT.isHeld()) {
      val difference = Vector2()
      difference.set(movementPivot)
      difference.sub(Input.getMousePositionInWorld())
      Camera.lookAt(difference)
    }
    if (Input.Button.MOUSE_RIGHT.isReleased()) {
      Camera.zoomTo(1f)
    }*/

    if (select.isPressed())
      selection.startFrom(pointer)
    if (select.isHeld()) {
      selection.endTo(pointer)
      if (selection.distanceFromStartingPoint() > 64f)
        singleSelectionLock = false
      if (singleSelectionLock == false) {
        highlightedSquads.clear()
        highlightedSquads.addAll(world.findSquadsInside(selection.selectionRectangle()))
        Renderer.renderRectangle(selection.selectionRectangle())
      }
    }
    if (select.isReleased()) {
      if (singleSelectionLock == false) {
        selectedSquads.clear()
        selectedSquads.addAll(highlightedSquads)
        highlightedSquads.clear()
        singleSelectionLock = true
      } else {
        selectedSquads.clear()
        if (hoveringSquad != null)
          selectedSquads.add(hoveringSquad);
      }
    }

    renderSquadsShips(highlightedSquads, 32f)
    renderSquadsShips(selectedSquads, 24f)
    if (hoveringSquad != null)
      renderSquadsShips(listOf(hoveringSquad), 48f)
  }

  private fun findPointerHoveringSquad(pointer: Vector2, world: World): Squad? {
    val ship = world.findClosestShip(pointer)
    if (pointer.dst(ship.position) < 32f)
      return world.findSquad(ship)
    return null;
  }

  private fun renderSquadsShips(squads: List<Squad>, radius: Float) {
    for (squad in squads)
      for (ship in squad.ships)
        Renderer.renderCircle(ship.position, radius)
  }

}