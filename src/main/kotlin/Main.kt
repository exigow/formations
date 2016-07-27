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
import rendering.FullscreenQuadTextureRenderer
import rendering.GBuffer
import rendering.ShipDebugRenderer.render
import rendering.materials.MaterialRenderer
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
  private val buffer = TrailsBuffer()
  private val trailsMap = world.allShips().filter { !it.config.displayedName.equals("Carrier") }.map{ it to buffer.registerTrail(it.position + Vec2.rotated(it.angle) * it.config.trailDistance) }.toMap()
  private val trailsRenderer = TrailsRenderer()
  private val materialRenderer = MaterialRenderer()
  private val gbuffer = GBuffer.setUp(Gdx.graphics.width, Gdx.graphics.height)
  private val fullscreenQuadRenderer = FullscreenQuadTextureRenderer()

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
    world.update(delta)
    buffer.update(delta)
    render(delta);
    trailsMap.forEach { e ->
      e.value.emit(e.key.position + (Vec2.rotated(e.key.angle) * e.key.config.trailDistance), 32f, Math.min(e.key.velocityAcceleration * 8 + .025f, 1f))
    }
  }

  fun render(delta: Float) {
    Draw.update(camera)
    gbuffer.clearDiffuse(Color.VERY_DARK_GRAY, 1f)
    gbuffer.paintOnDiffuse {
      Draw.grid(size = Vec2.scaled(1024f), density = 16, color = Color.DARK_GRAY)
    }
    world.allShips().forEach {
      gbuffer.paintOnDiffuse {
        if (trailsMap.containsKey(it))
          trailsRenderer.render(trailsMap[it]!!, AssetsManager.peekMaterial("trail").diffuse!!, camera.projectionMatrix())
      }
      gbuffer.paintOnDiffuse {
        materialRenderer.draw(AssetsManager.peekMaterial(it.config.hullName), it.position, it.angle, camera.projectionMatrix())
      }
    }
    gbuffer.paintOnDiffuse {
      world.allShips().forEach {
        it.render(camera.normalizedRenderingScale())
      }
      uiRenderer.render(delta)
      TrailsDebugRenderer.render(buffer)
    }
    fullscreenQuadRenderer.render(gbuffer.diffuseTexture())
  }

}