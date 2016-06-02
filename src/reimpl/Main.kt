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
    Interaction.interact(world)
    Renderer.renderGrid()
    for (ship: Ship in world.findAllShips()) {
      Renderer.renderCircle(ship.position, 4f);
      Renderer.renderArrow(ship.position, 16f, ship.angle)
    }
    Renderer.renderCircle(Input.getMousePositionInWorld(), 8f)

    val closestShip = world.findClosestShip(Input.getMousePositionInWorld())
    val closestSquad = world.findSquad(closestShip)
    Renderer.renderCircle(closestShip.position, 24f)
    for (ship: Ship in closestSquad.ships)
      Renderer.renderCircle(ship.position, 18f)
  }

}