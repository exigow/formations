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
import rendering.Draw
import rendering.shapes.Path
import rendering.shapes.PathRenderer
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
    //world.allShips().forEach { it.update(delta) }
    //render();
    //uiRenderer.render(delta)

    PathRenderer.update(camera)
    //val path = ShapeFactory.rectangle(camera.mousePosition()).paths.first()

    //val path = ShapeFactory.rectangle(camera.mousePosition()).first()
    //PathRenderer.renderLines(path)
    val a = Vec2(-256, 0)
    val b = camera.mousePosition()
    val c = Vec2(256, 0)
    val d = Vec2(384, 0)
    val path = Path(listOf(a, b, c, d))

    for (subPath in path.populate(32f).cut(preserve = 3, remove = 1))
      PathRenderer.renderLines(subPath)

    PathRenderer.renderPoints(path)
  }

  fun render() {
    Draw.update(camera)
    Draw.grid(size = Vec2.scaled(1024f), density = 32, color = Color.DARK_GRAY)
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
    Draw.lineDotted(position, position + Vec2.rotated(angle) * max, 8f)
    fun horizon(length: Float, size: Float) {
      val pivot = position + Vec2.rotated(angle) * length
      val horizontal = Vec2.rotated(angle + FastMath.pi / 2) * size
      Draw.line(pivot + horizontal, pivot - horizontal)
    }
    horizon(max, 8f)
    horizon(current, 16f)
  }

  private fun Ship.renderMovementTarget() {
    Draw.lineDotted(position, movementTarget, 16f)
    Draw.line(movementTarget, movementTarget + Vec2.rotated(movementTargetAngle) * 32)
  }

  private fun Ship.renderRotationState() {
    val angleScale = .25f
    val distanceScale = 128f
    val amount = config.rotationSpeedMax * angleScale
    Draw.arc(position, distanceScale, angle - amount, angle + amount)
    fun pivot(a: Float, thickness: Float) {
      val vec = Vec2.rotated(angle + a * angleScale)
      Draw.line(position + vec * (distanceScale - thickness), position + vec * (distanceScale + thickness))
    }
    pivot(angleAcceleration, 16f)
    pivot(config.rotationSpeedMax, 8f)
    pivot(-config.rotationSpeedMax, 8f)
  }

  private fun Ship.renderAccelerationAngle() {
    val length = 96f
    val start = angle - config.accelerationAngle
    val end = angle + config.accelerationAngle
    Draw.cone(position, length, start, end)
  }

}