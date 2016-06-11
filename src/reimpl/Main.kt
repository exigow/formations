import com.badlogic.gdx.Gdx
import core.Camera
import core.actions.catalog.CameraArrowsMovementAction
import core.actions.catalog.CameraMiddleClickMovementAction
import core.actions.catalog.CameraScrollZoomAction
import core.actions.catalog.CameraUnitZoomDoubleClickAction
import core.input.event.EventRegistry
import game.Ship
import game.World

class Main {

  private val world = World.randomWorld()
  private val camera = Camera()
  private val eventRegistry = EventRegistry()

  init {
    eventRegistry.registerBundle(CameraScrollZoomAction(camera).events())
    eventRegistry.registerBundle(CameraMiddleClickMovementAction(camera).events())
    eventRegistry.registerBundle(CameraArrowsMovementAction(camera).events())
    eventRegistry.registerBundle(CameraUnitZoomDoubleClickAction(world, camera).events())
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