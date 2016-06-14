import com.badlogic.gdx.Gdx
import core.Camera
import core.actions.ActionsRegistry
import core.actions.catalog.CameraArrowsMovementAction
import core.actions.catalog.CameraMiddleClickMovementAction
import core.actions.catalog.CameraScrollZoomAction
import core.actions.catalog.SelectionAction
import game.Ship
import game.World

class Main {

  private val world = World.randomWorld()
  private val camera = Camera()
  private val actions = ActionsRegistry()
  private val selectionAction = SelectionAction(camera, world)

  init {
    actions.addAction(CameraScrollZoomAction(camera))
    actions.addAction(CameraMiddleClickMovementAction(camera))
    actions.addAction(CameraArrowsMovementAction(camera))
    actions.addAction(selectionAction)
  }

  fun onRender() {
    val delta = Gdx.graphics.deltaTime
    camera.update(delta)
    actions.update(delta)

    Renderer.reset(camera)
    Renderer.renderGrid()
    for (ship in world.findAllShips())
      renderShip(ship)
    renderMouse()
    renderSelectionRect()

    for (ship in selectionAction.selectedSquads().flatMap { e -> e.ships })
      Renderer.renderCircle(ship.position, 24f)
  }

  fun renderSelectionRect() {
    val rect = selectionAction.rectangle()
    if (rect != null)
      Renderer.renderRectangle(rect)
  }

  fun renderShip(ship: Ship) {
    Renderer.renderArrow(ship.position, 16f, ship.angle)
    Renderer.renderCross(ship.position, camera.renderingScale() * 8f)
  }

  fun renderMouse() {
    val pos = camera.mousePosition()
    val radius = camera.scaledClickRadius()
    Renderer.renderCross(pos, radius)
    Renderer.renderCircle(pos, radius, 16)
  }

}