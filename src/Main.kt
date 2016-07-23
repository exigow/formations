import assets.AssetsManager
import com.badlogic.gdx.Gdx
import commons.math.Vec2
import core.Camera
import core.actions.Action
import core.actions.ActionsRegistry
import core.actions.catalog.*
import core.input.event.bundles.ThreeStateButtonEventBundle
import core.input.mappings.MouseButton
import game.PlayerContext
import game.World
import rendering.Color
import rendering.Draw
import rendering.DrawAsset
import rendering.ShipDebugRenderer.render
import rendering.trails.TrailsBuffer
import rendering.trails.TrailsDebugRenderer
import rendering.trails.TrailsRenderer
import ui.UserInterfaceRenderer

class Main {

  private val world = World.randomWorld()
  private val camera = Camera()
  private val actions = ActionsRegistry()
  private val context = PlayerContext()
  private val uiRenderer = UserInterfaceRenderer(context, camera, world)
  private val asset = AssetsManager.load()
  private val buffer = TrailsBuffer()
  private val trailsMap = world.allShips().map{ it to buffer.registerTrail(it.position) }.toMap()
  private val trailsRenderer = TrailsRenderer();

  init {
    actions.addAction(CameraScrollZoomAction(camera))
    actions.addAction(CameraMiddleClickMovementAction(camera))
    actions.addAction(CameraArrowsMovementAction(camera))
    actions.addAction(SelectionAction(camera, world, context))
    actions.addAction(OrderingActionClass(camera, context, world))
    actions.addAction(CameraShipLockAction(camera, context))
    //actions.addAction(PainterAction(camera, buffer))
  }

  fun onFrame() {
    val delta = Gdx.graphics.deltaTime
    camera.update(delta)
    actions.update(delta)
    world.update(delta)
    buffer.update(delta)
    render(delta);
    trailsMap.forEach { e -> e.value.emit(e.key.position + (Vec2.rotated(e.key.angle) * -16), 32f, Math.min(e.key.velocityAcceleration + .025f, 1f)) }
  }

  fun render(delta: Float) {
    Draw.update(camera)
    DrawAsset.update(camera)
    Draw.grid(size = Vec2.scaled(1024f), density = 16, color = Color.DARK_GRAY)
    trailsRenderer.render(buffer, asset["trail"], camera.projectionMatrix())
    world.allShips().forEach {
      fun checkoutAsset(): String = when (it.config.displayedName) {
        "Fighter" -> "interceptor"
        "Carrier" -> "carrier"
        else -> throw RuntimeException()
      }
      DrawAsset.draw(asset[checkoutAsset()], it.position, it.angle)
      it.render(camera.normalizedRenderingScale())
    }
    uiRenderer.render(delta)
    TrailsDebugRenderer.render(buffer)
  }

  private class PainterAction(private val cameraDep: Camera, private val buffer: TrailsBuffer) : Action {

    private var currentTrail: TrailsBuffer.Trail? = null

    private val events = object : ThreeStateButtonEventBundle(MouseButton.MOUSE_LEFT) {

      override fun onPress() {
        currentTrail = buffer.registerTrail(cameraDep.mousePosition())
      }

      override fun onRelease() {
        currentTrail = null
      }

      override fun onHold(delta: Float) {
        currentTrail!!.emit(cameraDep.mousePosition(), 64f, 1f)
      }

    }.toBundle()

    override fun events() = events

  }

}