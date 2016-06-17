import com.badlogic.gdx.Gdx
import core.Camera
import core.actions.ActionsRegistry
import core.actions.catalog.CameraArrowsMovementAction
import core.actions.catalog.CameraMiddleClickMovementAction
import core.actions.catalog.CameraScrollZoomAction
import core.actions.catalog.OrderingActionClass
import core.actions.catalog.selecting.SelectionAction
import game.PlayerContext
import game.Ship
import game.Squad
import game.World

class Main {

  private val world = World.randomWorld()
  private val camera = Camera()
  private val actions = ActionsRegistry()
  private val context = PlayerContext()
  private val selectionAction = SelectionAction(camera, world, context)

  init {
    actions.addAction(CameraScrollZoomAction(camera))
    actions.addAction(CameraMiddleClickMovementAction(camera))
    actions.addAction(CameraArrowsMovementAction(camera))
    actions.addAction(selectionAction)
    actions.addAction(OrderingActionClass(context))
  }

  fun onRender() {
    val delta = Gdx.graphics.deltaTime
    camera.update(delta)
    actions.update(delta)
    Renderer.reset(camera)
    Renderer.renderGrid()
    forEachShip(world.squads, {renderShip(it)})
    forEachShip(context.selected, {Renderer.renderCircle(it.position, 16f)})
    forEachShip(context.highlighted, {Renderer.renderCircle(it.position, 24f)})
    if (context.hovered != null)
      for (hoverShip in context.hovered!!.ships)
        Renderer.renderCircle(hoverShip.position, 32f)
    renderMouse()
    renderSelectionRect()
  }

  private fun forEachShip(ships: List<Squad>, f: (ship: Ship) -> Unit) = ships.flatMap { s -> s.ships }.forEach { f.invoke(it) }

  fun renderSelectionRect() {
    val rect = selectionAction.selectionRectangle()
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
    Renderer.renderCross(pos, radius / 2f)
  }

}