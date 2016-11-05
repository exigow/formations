import assets.AssetsManager
import com.badlogic.gdx.Gdx
import core.Camera
import core.actions.ActionsRegistry
import core.actions.catalog.*
import game.PlayerContext
import game.World
import rendering.GBuffer
import rendering.Sprite
import rendering.UIRenderer
import rendering.canvas.ShaderEffect
import rendering.procedural.ChunkToAsteroidConverter.toAsteroids
import rendering.procedural.TextureToChunkConverter
import rendering.renderers.GbufferRenderer
import rendering.utils.Draw

class Main {

  private val world = World.randomWorld()
  private val camera = Camera()
  private val actions = ActionsRegistry()
  private val context = PlayerContext()
  private val gbuffer = GBuffer()
  private val chunks = TextureToChunkConverter.convert(AssetsManager.peekMaterial("asteroid-mask-test").diffuse!!, { c -> c.red })
  private var timePassed = 0f
  private val spriteRenderer = GbufferRenderer(gbuffer)
  private val ui = UIRenderer(camera, context)

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
    render()
  }

  fun render() {
    Draw.update(camera)
    gbuffer.clear()
    renderFullscreenBackgroundImage()

    val asteroidSprites = chunks.toAsteroids(camera, timePassed)
    val shipSprites = world.allShips().map { it.toRenderable() }.flatten()
    val planet = Sprite("planet", Vec2(-16384, 0), Vec2.scaled(160f), 0f, -26576f, isCulled = false)
    val moonNear = Sprite("moon-a", Vec2(-8192, -2048), Vec2.scaled(48f), 0f, -14384f, isCulled = false)
    val moonFar = Sprite("moon-b", Vec2(16384, 2048), Vec2.scaled(32f), 0f, -46000f, isCulled = false)
    val belt = Sprite("belt", Vec2(-8192, -8192), Vec2.scaled(128f), 0f, -20000f, isCulled = false)
    val pilot = Sprite("pilot-a", Vec2(128, 32), Vec2.scaled(1f), timePassed * .125f, 32f)
    val blackDotWorkaround = Sprite("black", Vec2.zero(), Vec2.zero(), isCulled = false)
    val allSprites = asteroidSprites + shipSprites + ui.render() + planet + belt + moonNear + moonFar + pilot + blackDotWorkaround
    spriteRenderer.render(allSprites, camera)

    gbuffer.showCombined()
  }

  private fun renderFullscreenBackgroundImage() {
    gbuffer.paint {
      ShaderEffect.fromShader("fullscreenQuadShader")
        .bind("texture", AssetsManager.peekMaterial("background").diffuse!!)
        .showAsQuad()
    }
  }

}