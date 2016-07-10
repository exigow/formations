import assets.AssetsManager
import com.badlogic.gdx.Gdx
import commons.math.Vec2
import core.Camera
import core.actions.ActionsRegistry
import core.actions.catalog.*
import game.PlayerContext
import game.World
import rendering.Color
import rendering.Draw
import rendering.DrawAsset
import rendering.ShipDebugRenderer.render
import rendering.trails.TrailsBuffer
import rendering.trails.TrailsEmitter
import ui.UserInterfaceRenderer

class Main {

  private val world = World.randomWorld()
  private val camera = Camera()
  private val actions = ActionsRegistry()
  private val context = PlayerContext()
  private val uiRenderer = UserInterfaceRenderer(context, camera, world)
  private val asset = AssetsManager.load()
  private val trails = TrailsBuffer(positionCapacity = 64, connectionCapacity = 96)
  private val shipToEmitter = world.allShips().map { it to TrailsEmitter(32f, trails, it.position) }.toMap()

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
    shipToEmitter.forEach { it.value.emit(it.key.position) }
  }

  fun render(delta: Float) {
    Draw.update(camera)
    DrawAsset.update(camera)
    Draw.grid(size = Vec2.scaled(1024f), density = 32, color = Color.DARK_GRAY)
    world.allShips().forEach {
      fun checkoutAsset(): String = when (it.config.displayedName) {
        "Fighter" -> "interceptor"
        "Carrier" -> "carrier"
        else -> throw RuntimeException()
      }
      DrawAsset.draw(asset[checkoutAsset()], it.position, it.angle)
      it.render()
    }
    uiRenderer.render(delta)
    renderTrails()
  }

  private fun renderTrails() {
    trails.forEachPosition { Draw.cross(it, 4f) }
    trails.forEachConnection { from, to -> Draw.line(from, to) }
  }

}