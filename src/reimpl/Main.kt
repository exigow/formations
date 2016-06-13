import com.badlogic.gdx.Gdx
import core.Camera
import core.actions.ActionsRegistry
import core.actions.catalog.*
import game.Ship
import game.World

class Main {

  private val world = World.randomWorld()
  private val camera = Camera()
  private val actions = ActionsRegistry()
  private val selectionAction = SelectionAction(camera)

  init {
    actions.addAction(CameraScrollZoomAction(camera))
    actions.addAction(CameraMiddleClickMovementAction(camera))
    actions.addAction(CameraArrowsMovementAction(camera))
    actions.addAction(CameraUnitZoomDoubleClickAction(world, camera))
    actions.addAction(selectionAction)
  }

  fun onRender() {
    val delta = Gdx.graphics.deltaTime
    camera.update(delta)
    actions.update(delta)

    Renderer.reset(camera)
    Renderer.renderGrid()
    for (ship: Ship in world.findAllShips())
      Renderer.renderArrow(ship.position, 16f, ship.angle)
    Renderer.renderCircle(camera.mousePosition(), camera.scaledClickRadius(), 16)

  }

}