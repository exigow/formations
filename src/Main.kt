import com.badlogic.gdx.Gdx
import commons.math.Vec2
import core.Camera
import core.actions.ActionsRegistry
import core.actions.catalog.*
import game.PlayerContext
import game.World
import rendering.Color
import rendering.Draw
import rendering.ShipDebugRenderer.render
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
    world.collectives.forEach { it.update() }
    world.allShips().forEach { it.update(delta) }
    render(delta);
  }

  fun render(delta: Float) {
    Draw.update(camera)
    Draw.grid(size = Vec2.scaled(1024f), density = 32, color = Color.DARK_GRAY)
    world.allShips().forEach { it.render() }
    uiRenderer.render(delta)
  }

}