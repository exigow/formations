import com.badlogic.gdx.Gdx
import commons.math.FastMath
import commons.math.Vec2
import core.Camera
import core.actions.ActionsRegistry
import core.actions.catalog.*
import game.PlayerContext
import game.Ship
import game.World
import rendering.Color
import rendering.Shapes
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
    world.allShips().forEach { it.update(delta) }
    render();
    uiRenderer.render(delta)
  }

  fun render() {
    Shapes.update(camera)
    Shapes.grid(size = Vec2.scaled(1024f), density = 32, color = Color.DARK_GRAY)
    Shapes.lineDotted(Vec2.zero(), camera.mousePosition(), 16f)
    world.allShips().forEach { it.render() }
  }

  private fun Ship.render() {
   //renderBody()
    renderThrusterState()
    renderRotationState()
    renderMovementTarget()
    renderAccelerationAngle()
  }

  //private fun Ship.renderBody() = OldRenderer.renderDart(position, config.size, angle)

  private fun Ship.renderThrusterState() {
    val scale = 64f
    val max = config.thrusterSpeedMax * scale
    val current = velocityAcceleration * scale
    Shapes.lineDotted(position, position + Vec2.rotated(angle) * max, 8f)
    fun horizon(length: Float, size: Float) {
      val pivot = position + Vec2.rotated(angle) * length
      val horizontal = Vec2.rotated(angle + FastMath.pi / 2) * size
      Shapes.line(pivot + horizontal, pivot - horizontal)
    }
    horizon(max, 8f)
    horizon(current, 16f)
  }

  private fun Ship.renderMovementTarget() {
    Shapes.lineDotted(position, movementTarget, 16f)
    Shapes.line(movementTarget, movementTarget + Vec2.rotated(movementTargetAngle) * 32)
  }

  private fun Ship.renderRotationState() {
    val angleScale = .25f
    val distanceScale = 128f
    val amount = config.rotationSpeedMax * angleScale
    Shapes.arc(position, distanceScale, angle - amount, angle + amount)
    fun pivot(a: Float, thickness: Float) {
      val vec = Vec2.rotated(angle + a * angleScale)
      Shapes.line(position + vec * (distanceScale - thickness), position + vec * (distanceScale + thickness))
    }
    pivot(angleAcceleration, 16f)
    pivot(config.rotationSpeedMax, 8f)
    pivot(-config.rotationSpeedMax, 8f)
  }

  private fun Ship.renderAccelerationAngle() {
    val length = 96f
    val start = angle - config.accelerationAngle
    val end = angle + config.accelerationAngle
    Shapes.cone(position, length, start, end)
  }

}