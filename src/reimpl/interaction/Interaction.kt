package interaction

import Camera
import Renderer
import com.badlogic.gdx.math.Vector2
import game.Squad
import game.World
import input.Input
import java.util.*

object Interaction {

  private val movementPivot = Vector2()
  private val selection = SelectionTool()
  private val highlightedSquads = ArrayList<Squad>()
  private val selectedSquads = ArrayList<Squad>()
  private var singleSelectionLock = true
  private val pointerRadius = 20f

  fun interact(world: World) {
    val select = Input.Button.MOUSE_LEFT;
    val cameraPuppet = Input.Button.MOUSE_MIDDLE;
    val pointer = Input.getMousePositionInWorld();
    val hoveringSquad = findPointerHoveringSquad(pointer, world);

    if (cameraPuppet.isPressed()) {
      movementPivot.set(Camera.position()).add(Input.getMousePositionInWorld());
      Camera.zoomTo(.925f)
    }
    if (cameraPuppet.isHeld()) {
      val difference = Vector2()
      difference.set(movementPivot)
      difference.sub(Input.getMousePositionInWorld())
      Camera.lookAt(difference)
    }
    if (cameraPuppet.isReleased())
      Camera.zoomTo(1f)

    if (select.isPressed())
      selection.startFrom(pointer)
    if (select.isHeld()) {
      selection.endTo(pointer)
      if (selection.distanceFromStartingPoint() > pointerRadius)
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

    renderSquadsShips(highlightedSquads, 18f)
    renderSquadsShips(selectedSquads, 16f)
    if (hoveringSquad != null)
      renderSquadsShips(listOf(hoveringSquad), pointerRadius)
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