import com.badlogic.gdx.Gdx
import commons.math.ConvexHull
import core.Camera
import core.actions.ActionsRegistry
import core.actions.catalog.*
import game.PlayerContext
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
    actions.addAction(OrderingActionClass(camera, context))
  }

  fun onFrame() {
    val delta = Gdx.graphics.deltaTime
    camera.update(delta)
    actions.update(delta)
    render()
  }

  fun render() {
    Renderer.reset(camera)
    Renderer.renderGrid()
    world.allShips().forEach { Renderer.renderArrow(it.position, 16f, it.angle) }
    world.allSquads().forEach { Renderer.renderDiamond(it.center(), camera.renderingScale() * 4f) }
    context.selected.flatMap { it.ships }.forEach { Renderer.renderCircle(it.position, 16f, 4) }
    context.highlighted.flatMap { it.ships }.forEach { Renderer.renderCircle(it.position, 24f, 4) }
    context.hovered?.ships?.forEach { Renderer.renderCircle(it.position, 28f, 16) }
    renderMouse()
    renderSelectionRect()

    world.allSquads().forEach {
      val input = it.ships.map { it.position }
      val out = ConvexHull.calculate(input)
      val iter = out.iterator()
      var prev = iter.next()
      while (iter.hasNext()) {
        val next = iter.next()
        Renderer.renderLine(prev, next)
        prev = next
      }
    }
  }

  fun renderSelectionRect() {
    val rect = selectionAction.selectionRectangle()
    if (rect != null)
      Renderer.renderRectangle(rect)
  }

  fun renderMouse() {
    val pos = camera.mousePosition()
    val radius = camera.scaledClickRadius()
    Renderer.renderCross(pos, radius)
  }

}