import com.badlogic.gdx.Gdx
import commons.math.FastMath
import commons.math.Vec2
import core.Camera
import core.actions.ActionsRegistry
import core.actions.catalog.*
import game.PlayerContext
import game.Ship
import game.World
import rendering.Renderer
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
    world.allShips().forEach { it.update(delta) }
    world.allShips().forEach { it.render() }
    uiRenderer.render(delta)
  }

  private fun Ship.render() {
    renderBody()
    renderThrusterState()
    renderRotationState()
    renderMovementTarget()
    renderAccelerationAngle()
  }

  private fun Ship.renderBody() = Renderer.renderDart(position, config.size, angle)

  private fun Ship.renderThrusterState() {
    val scale = 64f
    val max = config.thrusterSpeedMax * scale
    val current = velocityAcceleration * scale
    Renderer.renderLineDotted(position, position + Vec2.rotated(angle) * max, 8f)
    fun horizon(length: Float, size: Float) {
      val pivot = position + Vec2.rotated(angle) * length
      val horizontal = Vec2.rotated(angle + FastMath.pi / 2) * size
      Renderer.renderLine(pivot + horizontal, pivot - horizontal)
    }
    horizon(max, 8f)
    horizon(current, 16f)
  }

  private fun Ship.renderMovementTarget() {
    Renderer.renderLineDotted(position, movementTarget, 16f)
    Renderer.renderLineArrow(movementTarget - Vec2.rotated(position.directionTo(movementTarget)) * 32f, movementTarget, 16f)

    Renderer.renderLine(movementTarget, movementTarget + Vec2.rotated(movementTargetAngle) * 32)
  }

  private fun Ship.renderRotationState() {
    val angleScale = .25f
    val distanceScale = 128f
    fun arc(amount: Float, length: Float) = Renderer.renderArc(position, length, angle - amount, angle + amount)
    arc(config.rotationSpeedMax * angleScale, distanceScale)
    fun pivot(a: Float, thickness: Float) {
      val vec = Vec2.rotated(angle + a * angleScale)
      Renderer.renderLine(position + vec * (distanceScale - thickness), position + vec * (distanceScale + thickness))
    }
    pivot(angleAcceleration, 16f)
    pivot(config.rotationSpeedMax, 8f)
    pivot(-config.rotationSpeedMax, 8f)
  }

  private fun Ship.renderAccelerationAngle() {
    val length = 96f
    val start = angle - config.accelerationAngle
    val end = angle + config.accelerationAngle
    Renderer.renderLine(position, position + Vec2.rotated(start) * length)
    Renderer.renderLine(position, position + Vec2.rotated(end) * length)
    Renderer.renderArc(position, length, start, end)
  }

}