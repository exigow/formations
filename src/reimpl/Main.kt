import com.badlogic.gdx.math.Vector2
import game.Ship
import game.World
import input.PlayerInput

class Main {

  private val world = World.randomWorld()
  private val movementPivot = Vector2()
  private val selectionTool = SelectionTool()

  fun onRender() {
    PlayerInput.updateStates()
    Camera.update(1f)
    Renderer.reset()
    Renderer.renderGrid()
    for (ship: Ship in world.collectShips()) {
      Renderer.renderCircle(ship.position, 4f);
      Renderer.renderArrow(ship.position, 16f, ship.angle)
    }
    Renderer.renderCircle(PlayerInput.getMousePositionInWorld(), 8f)

    if (PlayerInput.mouseRight.isPressed()) {
      movementPivot.set(Camera.position()).add(PlayerInput.getMousePositionInWorld());
      Camera.zoomTo(.925f)
    }
    if (PlayerInput.mouseRight.isHeld()) {
      val difference = Vector2()
      difference.set(movementPivot)
      difference.sub(PlayerInput.getMousePositionInWorld())
      Camera.lookAt(difference)
    }
    if (PlayerInput.mouseRight.isReleased()) {
      Camera.zoomTo(1f)
    }

    if (PlayerInput.mouseLeft.isPressed())
      selectionTool.startFrom(PlayerInput.getMousePositionInWorld())
    if (PlayerInput.mouseLeft.isHeld()) {
      selectionTool.endTo(PlayerInput.getMousePositionInWorld())
      selectionTool.updateSelection(world)
      Renderer.renderRectangle(selectionTool.selectionRectangle())
    }
    if (PlayerInput.mouseLeft.isReleased()) {
      println("selection begin")
      selectionTool.selectedShips().forEach { println("--> $it") }
      println("selection end")
    }
  }

}