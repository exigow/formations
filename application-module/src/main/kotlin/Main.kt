import assets.AssetsManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import core.Camera
import core.actions.ActionsRegistry
import core.actions.catalog.*
import game.PlayerContext
import game.World
import game.orders.MoveOrder
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
    val invisibleBlackDotSprite = Sprite(AssetsManager.peekMaterial("black"), camera.positionEye(), Vec2.zero())
    val planet = Sprite(AssetsManager.peekMaterial("planet"), Vec2(-16384, 0), Vec2.scaled(160f), 0f, -26576f, isCulled = false)
    val moonNear = Sprite(AssetsManager.peekMaterial("moon-a"), Vec2(-8192, -2048), Vec2.scaled(48f), 0f, -14384f, isCulled = false)
    val moonFar = Sprite(AssetsManager.peekMaterial("moon-b"), Vec2(16384, 2048), Vec2.scaled(32f), 0f, -46000f, isCulled = false)
    val belt = Sprite(AssetsManager.peekMaterial("belt"), Vec2(-8192, -8192), Vec2.scaled(128f), 0f, -20000f, isCulled = false)
    val pilot = Sprite(AssetsManager.peekMaterial("pilot-a"), Vec2(128, 32), Vec2.scaled(1f), timePassed * .125f, 32f)
    val allSprites = asteroidSprites + shipSprites + planet + belt + moonNear + moonFar + pilot + invisibleBlackDotSprite
    spriteRenderer.render(allSprites, camera)

    gbuffer.showCombined()

    if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
      val s = world.allSquads().first()
      val c = world.joinToNewCollective(listOf(s))
      c.orders.add(MoveOrder(s.center() + Vec2(700, 0), 0f))
    }
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