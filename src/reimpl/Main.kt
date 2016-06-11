import actions.CameraArrowsMovementAction
import actions.CameraMiddleClickMovementAction
import actions.CameraScrollZoomAction
import actions.CameraUnitZoomDoubleClickAction
import com.badlogic.gdx.Gdx
import core.Camera
import core.input.EventRegistry
import game.Ship
import game.World

class Main {

  private val world = World.randomWorld()
  private val camera = Camera()
  private val eventRegistry = EventRegistry()

  init {
    CameraScrollZoomAction(camera).bind(eventRegistry)
    CameraUnitZoomDoubleClickAction(world, camera).bind(eventRegistry)
    CameraMiddleClickMovementAction(camera).bind(eventRegistry)
    CameraArrowsMovementAction(camera).bind(eventRegistry)
  }

  fun onRender() {
    val delta = Gdx.graphics.deltaTime
    camera.update(delta)
    eventRegistry.updatePressingTicks(delta)

    Renderer.reset(camera)
    Renderer.renderGrid()
    for (ship: Ship in world.findAllShips())
      Renderer.renderArrow(ship.position, 16f, ship.angle)
    Renderer.renderCircle(camera.mousePosition(), camera.scaledClickRadius(), 16)

  }

}