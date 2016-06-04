import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import game.Ship
import game.World
import input.Input
import interaction.Interaction

class Main {

  private val world = World.randomWorld()
 // private val font = BitmapFont()
 // private val batch = SpriteBatch()

  fun onRender() {
    Input.update()
    Camera.update(1f)
    Renderer.reset()
    Renderer.renderGrid()

    for (ship: Ship in world.findAllShips())
      Renderer.renderArrow(ship.position, 16f, ship.angle)
    Renderer.renderCircle(Input.getMousePositionInWorld(), 8f)

    Interaction.interact(world)

/*    val actions = NewInteraction.collectPossibleActions().joinToString(" ")

    batch.projectionMatrix.set(Camera.projectionMatrix())
    batch.begin();
    font.draw(batch, "actions: $actions", 0f, 0f);
    batch.end();
*/
    /*val closestShip = world.findClosestShip(Input.getMousePositionInWorld())
    val closestSquad = world.findSquad(closestShip)
    Renderer.renderCircle(closestShip.position, 24f)
    for (ship: Ship in closestSquad.ships)
      Renderer.renderCircle(ship.position, 18f)*/
  }

}