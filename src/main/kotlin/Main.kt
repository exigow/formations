import assets.AssetsManager
import com.badlogic.gdx.Gdx
import commons.math.Vec2
import core.Camera
import core.actions.ActionsRegistry
import core.actions.catalog.*
import game.PlayerContext
import game.World
import rendering.GBuffer
import rendering.NewUIRenderer
import rendering.Sprite
import rendering.canvas.FullscreenQuad
import rendering.procedural.ChunkToAsteroidConverter.toAsteroids
import rendering.procedural.TextureToChunkConverter
import rendering.renderers.GbufferRenderer
import rendering.utils.Draw

class Main {

  private val world = World.randomWorld()
  private val camera = Camera()
  private val actions = ActionsRegistry()
  private val context = PlayerContext()
  private val gbuffer = GBuffer.setUpWindowSize()
  private val chunks = TextureToChunkConverter.convert(AssetsManager.peekMaterial("asteroid-mask-test").diffuse!!, { c -> c.r})
  private var timePassed = 0f
  private val spriteRenderer = GbufferRenderer(gbuffer)
  private val newUIRenderer = NewUIRenderer(camera, context)

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
    timePassed += delta
    camera.update(delta)
    actions.update(delta)
    world.update(delta)
    render();
  }

  fun render() {
    Draw.update(camera)
    gbuffer.clear()
    renderFullscreenBackgroundImage();

    val asteroidSprites = chunks.toAsteroids(camera, timePassed)
    val shipSprites = world.allShips().map { it.toRenderable() }.flatten()
    val allSprites = asteroidSprites + shipSprites + Sprite(AssetsManager.peekMaterial("explosion"), Vec2.zero(), 4f)
    spriteRenderer.render(allSprites, camera)

    gbuffer.paintOnUserInterface {
      newUIRenderer.render()
    }
    gbuffer.showCombined()
  }

  private fun renderFullscreenBackgroundImage() {
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