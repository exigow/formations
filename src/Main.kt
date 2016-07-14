import assets.AssetsManager
import com.badlogic.gdx.Gdx
import core.Camera
import core.actions.ActionsRegistry
import core.actions.catalog.*
import game.PlayerContext
import game.World
import rendering.Draw
import rendering.DrawAsset
import rendering.ShipDebugRenderer.render
import ui.UserInterfaceRenderer

class Main {

  private val world = World.randomWorld()
  private val camera = Camera()
  private val actions = ActionsRegistry()
  private val context = PlayerContext()
  private val uiRenderer = UserInterfaceRenderer(context, camera, world)
  private val asset = AssetsManager.load()

  init {
    actions.addAction(CameraScrollZoomAction(camera))
    actions.addAction(CameraMiddleClickMovementAction(camera))
    actions.addAction(CameraArrowsMovementAction(camera))
    actions.addAction(SelectionAction(camera, world, context))
    actions.addAction(OrderingActionClass(camera, context, world))
    actions.addAction(CameraShipLockAction(camera, context))
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
    DrawAsset.update(camera)
    //Draw.grid(size = Vec2.scaled(128f), density = 4, color = Color.DARK_GRAY)
    //DynamicGridRenderer.draw(camera)
    world.allShips().forEach {
      fun checkoutAsset(): String = when (it.config.displayedName) {
        "Fighter" -> "interceptor-old"
        "Carrier" -> "carrier"
        else -> throw RuntimeException()
      }
      DrawAsset.draw(asset[checkoutAsset()], it.position, it.angle)
      it.render(camera.normalizedRenderingScale())
    }
    uiRenderer.render(delta)
  }

}