import game.Ship
import game.World
import input.Input
import interaction.Interaction

class Main {

  private val world = World.randomWorld()

  fun onRender() {
    Input.update()
    Camera.update(1f)
    Renderer.reset()
    Renderer.renderGrid()

    for (ship: Ship in world.findAllShips())
      Renderer.renderArrow(ship.position, 16f, ship.angle)
    Renderer.renderCircle(Input.getMousePositionInWorld(), 8f)

    Interaction.interact(world)
  }

}