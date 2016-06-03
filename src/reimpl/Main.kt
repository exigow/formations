import game.Ship
import game.World
import input.Input
import interaction.NewInteraction

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

    //Interaction.interact(world)



    NewInteraction.propagate(world)

    /*val closestShip = world.findClosestShip(Input.getMousePositionInWorld())
    val closestSquad = world.findSquad(closestShip)
    Renderer.renderCircle(closestShip.position, 24f)
    for (ship: Ship in closestSquad.ships)
      Renderer.renderCircle(ship.position, 18f)*/
  }

}