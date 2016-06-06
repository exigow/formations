import com.badlogic.gdx.Gdx
import game.Ship
import game.World
import newinput.NewInput

class Main {

  private val world = World.randomWorld()

  init {
    NewInput.Mouse.LEFT.registerOnRelease { pointer -> println("left released on $pointer") }
    NewInput.Mouse.LEFT.registerOnPress { pointer -> println("left pressed on $pointer") }
    NewInput.Mouse.LEFT.registerOnPressedTick { pointer, delta -> println("left ticking on $pointer with delta $delta") }
  }

  fun onRender() {
    //Input.update()
    NewInput.update(Gdx.graphics.deltaTime)
    Camera.update(1f)
    Renderer.reset()
    Renderer.renderGrid()

    for (ship: Ship in world.findAllShips())
      Renderer.renderArrow(ship.position, 16f, ship.angle)
    Renderer.renderCircle(NewInput.Mouse.position(), 8f)

    //Interaction.interact(world)
  }

}