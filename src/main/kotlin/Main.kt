import assets.AssetsManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import commons.math.Vec2
import core.Camera
import core.actions.ActionsRegistry
import core.actions.catalog.*
import game.PlayerContext
import game.World
import rendering.Draw
import rendering.FontRenderer
import rendering.GBuffer
import rendering.canvas.FullscreenQuad
import rendering.materials.MaterialRenderer
import rendering.trails.TrailsBuffer
import rendering.trails.TrailsRenderer
import ui.UserInterfaceRenderer

class Main {

  private val world = World.randomWorld()
  private val camera = Camera()
  private val actions = ActionsRegistry()
  private val context = PlayerContext()
  private val uiRenderer = UserInterfaceRenderer(context, camera, world)
  private val buffer = TrailsBuffer()
  private val trailsMap = world.allShips().filter { !it.config.id.equals("cruiser") }.map{ it to buffer.registerTrail(it.position + Vec2.rotated(it.angle) * it.config.trailDistance) }.toMap()
  private val gbuffer = GBuffer.setUp(Gdx.graphics.width, Gdx.graphics.height)
  private val trailsRenderer = TrailsRenderer(gbuffer)
  private val materialRenderer = MaterialRenderer(gbuffer)
  private val shape = ShapeRenderer() // todo remove

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
    trailsMap.forEach { e ->
      e.value.emit(e.key.position + (Vec2.rotated(e.key.angle) * e.key.config.trailDistance), 16f, Math.min(e.key.velocityAcceleration * 8 + .025f, 1f))
    }
    render(delta);
  }

  fun render(delta: Float) {
    Draw.update(camera)
    gbuffer.clear()
    renderBackgroundImage();
    /*gbuffer.paintOnDiffuse {
      Draw.grid(size = Vec2.scaled(1024f), density = 16, color = Color.DARK_GRAY)
    }*/
    world.allShips().forEach {
      if (trailsMap.containsKey(it))
        trailsRenderer.render(trailsMap[it]!!, AssetsManager.peekMaterial("trail"), camera.projectionMatrix())
      materialRenderer.draw(AssetsManager.peekMaterial(it.config.hullName), it.position, it.angle, camera.projectionMatrix())
    }
    gbuffer.paintOnDiffuse {
      /*world.allShips().forEach {
        it.render(camera.normalizedRenderingScale())
      }*/
      uiRenderer.render(delta)
      //TrailsDebugRenderer.render(buffer)

      shape.projectionMatrix = camera.projectionMatrix()
      shape.begin(ShapeRenderer.ShapeType.Filled)
      shape.circle(camera.mousePosition().x, camera.mousePosition().y, 16f)
      shape.end()
    }

    gbuffer.paintOnDiffuse {
      FontRenderer.draw("asdasdasdas", Vec2.zero(), camera.projectionMatrix())
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