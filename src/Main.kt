import com.badlogic.gdx.Gdx
import core.Camera
import core.actions.ActionsRegistry
import core.actions.catalog.CameraArrowsMovementAction
import core.actions.catalog.CameraMiddleClickMovementAction
import core.actions.catalog.CameraScrollZoomAction
import core.actions.catalog.OrderingActionClass
import core.actions.catalog.SelectionAction
import game.PlayerContext
import game.Ship
import game.Squad
import game.World
import game.orders.MoveOrder

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
    actions.addAction(OrderingActionClass(camera, context))
  }

  fun onRender() {
    val delta = Gdx.graphics.deltaTime
    camera.update(delta)
    actions.update(delta)
    Renderer.reset(camera)
    Renderer.renderGrid()
    forEachShip(world.squads, {renderShip(it)})
    world.squads.forEach { Renderer.renderCircle(it.center(), 4f * camera.renderingScale(), 4) }
    forEachShip(context.selected, {Renderer.renderCircle(it.position, 16f, 4)})
    forEachShip(context.highlighted, {Renderer.renderCircle(it.position, 24f, 4)})
    if (context.hovered != null)
      for (hoverShip in context.hovered!!.ships)
        Renderer.renderCircle(hoverShip.position, 28f, 16)
    /*for (squad in world.squads) {
      if (!squad.orders.isEmpty()) {
        val moveOrder = squad.orders.iterator().next() as MoveOrder
        Renderer.renderLine(squad.center(), moveOrder.where)
      }
    }*/
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
  }

  fun renderMouse() {
    val pos = camera.mousePosition()
    val radius = camera.scaledClickRadius()
    Renderer.renderCross(pos, radius / 2f)
  }

}