import com.badlogic.gdx.math.Vector2
import game.Ship
import game.Squad
import game.World
import input.Inputs

class Main {

  private val world = World.randomWorld()
  private val movementPivot = Vector2()

  fun onRender() {
    Inputs.updateStates()
    Camera.update(1f)
    Renderer.reset()
    Renderer.renderGrid()
    for (squad: Squad in world.squads) {
      for (ship: Ship in squad.ships) {
        Renderer.renderCircle(ship.position, 4f);
        Renderer.renderArrow(ship.position, 16f, ship.angle)
      }
    }
    Renderer.renderCircle(Camera.unprojectedWorldMouse(), 8f)

    if (Inputs.mouseRight.isPressed()) {
      movementPivot.set(Camera.position()).add(Camera.unprojectedWorldMouse());
      Camera.zoomTo(.925f)
    }
    if (Inputs.mouseRight.isHeld()) {
      val difference = Vector2()
      difference.set(movementPivot)
      difference.sub(Camera.unprojectedWorldMouse())
      Camera.lookAt(difference)
    }
    if (Inputs.mouseRight.isReleased()) {
      Camera.zoomTo(1f)
    }
  }

}