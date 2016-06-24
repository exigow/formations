import com.badlogic.gdx.Gdx
import commons.math.FastMath
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
    world.allShips().forEach { it.update(delta) }
    world.allShips().forEach { it.render() }
    uiRenderer.render(delta)
  }

  private fun Ship.render() {
    Renderer.renderDart(position, 16f, angle)
    renderAngle(position, angle, config.accelerationAngle, 64f);
    renderSpeed(position, angle, config.thrusterSpeedAcceleration * 64, config.thrusterSpeedMax * 64)
    renderRotation(position, angle, config.rotationSpeedAcceleration * .5f, config.rotationSpeedMax * .5f)
    renderMovementTarget(position, movementTarget)
  }

  private fun renderMovementTarget(from: Vec2, to: Vec2) {
    val dir = -from.directionTo(to) - FastMath.pi / 2
    Renderer.renderLineArrow(to - Vec2.rotated(dir) * 32f, to, 16f)
    Renderer.renderDiamond(to, 8f)
  }

  private fun renderRotation(where: Vec2, angle: Float, acceleration: Float, max: Float) {
    fun arc(amount: Float, length: Float) = Renderer.renderArc(where, length, angle - amount, angle + amount)
    arc(acceleration, 96f)
    arc(max, 128f)
  }

  private fun renderAngle(where: Vec2, angle: Float, range: Float, radius: Float) {
    val start = angle - range
    val end = angle + range
    Renderer.renderLine(where, where + Vec2.rotated(start) * radius)
    Renderer.renderLine(where, where + Vec2.rotated(end) * radius)
    Renderer.renderArc(where, radius, start, end)
  }

  private fun renderSpeed(where: Vec2, angle: Float, acceleration: Float, max: Float) {
    Renderer.renderLineDotted(where, where + Vec2.rotated(angle) * max, 8f)
    fun horizon(length: Float, size: Float) {
      val pivot = where + Vec2.rotated(angle) * length
      val horizontal = Vec2.rotated(angle + FastMath.pi / 2) * size
      Renderer.renderLine(pivot + horizontal, pivot - horizontal)
    }
    horizon(max, 16f)
    horizon(acceleration, 8f)
  }

}