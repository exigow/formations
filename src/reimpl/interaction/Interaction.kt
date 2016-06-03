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

  fun interact(world: World) {
    if (Input.Button.MOUSE_RIGHT.isPressed()) {
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
    }

    if (Input.Button.MOUSE_LEFT.isPressed())
      selection.startFrom(Input.getMousePositionInWorld())
    if (Input.Button.MOUSE_LEFT.isHeld()) {
      selection.endTo(Input.getMousePositionInWorld())
      highlightedSquads.clear()
      highlightedSquads.addAll(world.findSquadsInside(selection.selectionRectangle()))
      Renderer.renderRectangle(selection.selectionRectangle())
    }
    if (Input.Button.MOUSE_LEFT.isReleased()) {
      selectedSquads.clear()
      selectedSquads.addAll(highlightedSquads)
      highlightedSquads.clear()
    }

    renderSquadsShips(highlightedSquads, 32f)
    renderSquadsShips(selectedSquads, 24f)
  }

  private fun renderSquadsShips(squads: List<Squad>, radius: Float) {
    for (squad in squads)
      for (ship in squad.ships)
        Renderer.renderCircle(ship.position, radius)
  }

}