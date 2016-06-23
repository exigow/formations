import com.badlogic.gdx.Gdx
import commons.math.Vec2
import core.Camera
import core.actions.ActionsRegistry
import core.actions.catalog.*
import game.PlayerContext
import game.Ship
import game.World
import ui.UserInterfaceRenderer

class Main {

  private val world = World.randomWorld()
  private val camera = Camera()
  private val actions = ActionsRegistry()
  private val context = PlayerContext()
  private val uiRenderer = UserInterfaceRenderer(context, camera, world)

  init {
    actions.addAction(CameraScrollZoomAction(camera))
    actions.addAction(CameraMiddleClickMovementAction(camera))
    actions.addAction(CameraArrowsMovementAction(camera))
    actions.addAction(SelectionAction(camera, world, context))
    actions.addAction(OrderingActionClass(camera, context, world))
  }

  fun onFrame() {
    val delta = Gdx.graphics.deltaTime
    camera.update(delta)
    actions.update(delta)
    Renderer.reset(camera)
    Renderer.renderGrid()
    world.allShips().forEach { it.render() }
    uiRenderer.render(delta)
  }

  private fun Ship.render() {
    Renderer.renderDart(position, 16f, angle)
    renderAngle(position, angle, config.accelerationAngle, 64f);
  }

  private fun renderAngle(where: Vec2, angle: Float, range: Float, radius: Float) {
    val start = angle - range
    val end = angle + range
    Renderer.renderLine(where, where + Vec2.rotated(start) * radius)
    Renderer.renderLine(where, where + Vec2.rotated(end) * radius)
    Renderer.renderArc(where, radius, start, end)
  }

}