import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import game.Ship
import game.Squad
import game.World
import rendering.Camera
import rendering.Renderer

class Main {

  private val world = World.randomWorld()

  fun onRender() {
    Camera.update(1f)
    Renderer.reset()
    Renderer.renderGrid()
    for (squad: Squad in world.squads) {
      for (ship: Ship in squad.ships) {
        Renderer.renderCircle(ship.position, 4f);
        Renderer.renderArrow(ship.position, 16f, ship.angle)
      }
    }
    Renderer.renderCircle(Camera.unprojectedWorldMouse(), 4f)

    if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
      Camera.lookAt(Camera.unprojectedWorldMouse())
      Camera.zoom(.925f)
    } else {
      Camera.zoom(1f)
    }
  }

}