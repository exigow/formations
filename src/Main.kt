import com.badlogic.gdx.Gdx
import core.Camera
import core.actions.ActionsRegistry
import core.actions.catalog.*
import game.PlayerContext
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
    world.allShips().forEach { Renderer.renderDart(it.position, 16f, it.angle) }
    uiRenderer.render(delta)
  }

}