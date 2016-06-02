package interaction

import Camera
import Renderer
import com.badlogic.gdx.math.Vector2
import game.World
import input.Input

object Interaction {

  private val movementPivot = Vector2()
  private val selectionTool = SelectionTool()

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
      selectionTool.startFrom(Input.getMousePositionInWorld())
    if (Input.Button.MOUSE_LEFT.isHeld()) {
      selectionTool.endTo(Input.getMousePositionInWorld())
      selectionTool.updateSelection(world)
      Renderer.renderRectangle(selectionTool.selectionRectangle())
    }
    if (Input.Button.MOUSE_LEFT.isReleased()) {
      println("selection begin")
      selectionTool.selectedShips().forEach { println("--> $it") }
      println("selection end")
    }
  }

}