import assets.AssetsManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import commons.math.FastMath
import commons.math.Vec2
import core.Camera
import core.actions.ActionsRegistry
import core.actions.catalog.*
import game.Asteroid
import game.PlayerContext
import game.World
import rendering.*
import rendering.canvas.FullscreenQuad
import rendering.materials.MaterialRenderer
import rendering.rect.SlicedRectangleRenderer
import rendering.trails.TrailsBuffer
import rendering.trails.TrailsRenderer
import rendering.utils.PixelIterator
import ui.UserInterfaceRenderer
import java.util.*

class Main {

  private val world = World.randomWorld()
  private val camera = Camera()
  private val actions = ActionsRegistry()
  private val context = PlayerContext()
  //private val uiRenderer = UserInterfaceRenderer(context, camera, world)
  private val buffer = TrailsBuffer()
  private val trailsMap = world.allShips().filter { !it.config.id.equals("cruiser") }.map{ it to buffer.registerTrail(it.position + Vec2.rotated(it.angle) * it.config.trailDistance) }.toMap()
  private val gbuffer = GBuffer.setUp(Gdx.graphics.width, Gdx.graphics.height)
  private val trailsRenderer = TrailsRenderer(gbuffer)
  private val materialRenderer = MaterialRenderer(gbuffer)
  private val batch = SpriteBatch()
  private val asteroids = maskToAsteroids()


  init {
    actions.addAction(CameraScrollZoomAction(camera))
    actions.addAction(CameraMiddleClickMovementAction(camera))
    actions.addAction(CameraArrowsMovementAction(camera))
    actions.addAction(SelectionAction(camera, world, context))
    actions.addAction(OrderingActionClass(camera, context, world))
    actions.addAction(CameraShipLockAction(camera, context))
  }

  private fun maskToAsteroids(): List<Asteroid> {
    val lookup = PixelIterator(AssetsManager.peekMaterial("asteroid-mask-test").diffuse!!)
    val result = ArrayList<Asteroid>()
    lookup.iterate { x, y, color ->
      val red = color.r
      if (red > .075f) {
        val asset = FastMath.chooseRandomly("rocky-big0", "rocky-big1", "rocky-big2", "rocky-big3")
        val rotSpeed = Vec2.random().angleInRadians() * .025f
        val angle = Vec2.random().angleInRadians()
        result += Asteroid((Vec2(x, y) + Vec2.random() - Vec2(32, 32)) * 128, angle, rotSpeed, asset, red * FastMath.randomRange(.5f, 2f))
      }
    }
    return result
  }

  fun onFrame() {
    val delta = Gdx.graphics.deltaTime
    camera.update(delta)
    actions.update(delta)
    world.update(delta)
    buffer.update(delta)
    trailsMap.forEach { e ->
      e.value.emit(e.key.position + (Vec2.rotated(e.key.angle) * e.key.config.trailDistance), 16f, Math.min(e.key.velocityAcceleration * 8 + .025f, 1f))
    }
    render(delta);
  }

  fun render(delta: Float) {
    Draw.update(camera)
    gbuffer.clear()
    renderBackgroundImage();
    asteroids.forEach {
      if (camera.worldVisibilityRectangle(128f).contains(it.position.toVector2())) {
        materialRenderer.draw(AssetsManager.peekMaterial(it.assetName), it.position, it.angle, it.scale, camera.projectionMatrix())
        it.angle += it.angleRotationSpeed * delta
      }
    }
    world.allShips().forEach {
      if (trailsMap.containsKey(it))
        trailsRenderer.render(trailsMap[it]!!, AssetsManager.peekMaterial("trail"), camera.projectionMatrix())
      materialRenderer.draw(AssetsManager.peekMaterial(it.config.hullName), it.position, it.angle, 1f, camera.projectionMatrix())
    }
    gbuffer.paintOnUserInterface {
      //uiRenderer.render(delta)
      if (context.isHovering()) {
        val h = context.hovered!!
        val type = h.ships.first().config.displayedName
        FontRenderer.draw(type, camera.mouseScreenPosition() + Vec2(32, 32), camera.screenMatrix())
        batch.projectionMatrix = camera.projectionMatrix()
        batch.begin()
        h.ships.forEach {
          val tex = AssetsManager.peekMaterial("arrow").diffuse!!
          val scale = camera.renderingScale()
          batch.draw(tex, it.position.x - tex.width / 2f * scale, it.position.y - tex.height / 2f * scale, tex.width * scale, tex.height * scale)
        }
        batch.end()
      }
      if (context.selectionRect != null) {
        Draw.rectangle(context.selectionRect!!)
      }

      /*if (context.selectionRect != null) {
        val rect = context.selectionRect!!
        val from = Vec2(rect.x, rect.y)
        val to = Vec2(rect.x + rect.width, rect.y + rect.height)

        SlicedRectangleRenderer.render(from, to, AssetsManager.peekMaterial("rect").diffuse!!, camera.projectionMatrix())
      }*/
    }

    gbuffer.showCombined()
  }

  private fun renderBackgroundImage() {
    gbuffer.paintOnDiffuse {
      AssetsManager.peekMaterial("background").diffuse!!.bind(0)
      val shader = AssetsManager.peekShader("fullscreenQuadShader")
      shader.begin()
      shader.setUniformi("texture", 0);
      FullscreenQuad.renderWith(shader)
      shader.end()
    }
  }

}