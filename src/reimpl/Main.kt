import com.badlogic.gdx.math.Vector2
import game.Ship
import game.Squad
import game.World
import rendering.Renderer

class Main {

  val world = World.createTestWorld()

  fun onRender() {
    Renderer.reset()
    Renderer.renderCircle(Vector2.Zero, 8f)
    for (squad: Squad in world.squads) {
      for (ship: Ship in squad.ships) {
        Renderer.renderCircle(ship.position, 4f);
      }
    }
  }

}