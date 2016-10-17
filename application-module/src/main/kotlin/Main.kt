import assets.AssetsManager
import com.badlogic.gdx.Gdx
import core.Camera
import core.actions.ActionsRegistry
import core.actions.catalog.*
import game.PlayerContext
import game.World
import rendering.GBuffer
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
  private val chunks = TextureToChunkConverter.convert(AssetsManager.peekMaterial("asteroid-mask-test").diffuse!!, { c -> c.red })
  private var timePassed = 0f
  private val spriteRenderer = GbufferRenderer(gbuffer)

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
    val invisibleBlackDotSprite = Sprite(AssetsManager.peekMaterial("black"), camera.positionEye(), 0f)
    val planet = Sprite(AssetsManager.peekMaterial("planet"), Vec2(-16384, 0), 160f, FastMath.pi / 2, -26576f, canBeCulled = false)
    val moon = Sprite(AssetsManager.peekMaterial("moon"), Vec2(-8192, -2048), 48f, FastMath.pi / 2, -14384f, canBeCulled = false)
    val belt = Sprite(AssetsManager.peekMaterial("belt"), Vec2(-8192, -8192), 128f, FastMath.pi / 2, -20000f, canBeCulled = false)
    val allSprites = asteroidSprites + shipSprites + planet + belt + moon + invisibleBlackDotSprite
    spriteRenderer.render(allSprites, camera)

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