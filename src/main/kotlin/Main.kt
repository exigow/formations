import assets.AssetsManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import commons.math.Random
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
import rendering.procedural.Chunk
import rendering.procedural.TextureToChunkConverter
import rendering.trails.TrailsRenderer
import ui.UserInterfaceRenderer

class Main {

  private val world = World.randomWorld()
  private val camera = Camera()
  private val actions = ActionsRegistry()
  private val context = PlayerContext()
  private val uiRenderer = UserInterfaceRenderer(context, camera, world)
  private val gbuffer = GBuffer.setUp(Gdx.graphics.width, Gdx.graphics.height)
  private val trailsRenderer = TrailsRenderer(gbuffer)
  private val materialRenderer = MaterialRenderer(gbuffer)
  private val batch = SpriteBatch()
  private val source = TextureToChunkConverter.convert(AssetsManager.peekMaterial("asteroid-mask-test").diffuse!!, {c -> c.r})
  private var timePassed = 0f

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
    render(delta);
  }

  fun render(delta: Float) {
    Draw.update(camera)
    gbuffer.clear()
    renderBackgroundImage();
    world.allShips().forEach {
      it.render(materialRenderer, trailsRenderer, camera.projectionMatrix())
    }
    gbuffer.paintOnUserInterface {
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
      uiRenderer.render(delta)
    }

    val processed = source
      .filter { it.value > .075f } // apply threshold
      .filter { it.value > camera.renderingScale() * .0375 } // show only noticeable by camera
      .map { it.translate(Vec2.one() * -16).scale(128f) } // centered + scaled to world
      .filter { camera.worldVisibilityRectangle(-64f).contains(it.position.toVector2()) } // show visible
    processed.forEach { it.toRenderedAsteroid() }

    gbuffer.showCombined()

    processed.forEach {
      Draw.diamond(it.position, it.value * 64)
      Draw.cross(it.position, 4f * camera.renderingScale())
    }
  }

  private fun Chunk.toRenderedAsteroid() {
    val seed = toSeed()

    val materialName = Random.chooseRandomly(seed, "asteroid-rock-a", "asteroid-rock-b", "asteroid-rock-c" ,"asteroid-rock-d")
    val material = AssetsManager.peekMaterial(materialName)

    val sizeVariation = Random.randomFloatRange(seed, .75f, 1.25f)
    val s = value * sizeVariation

    val positionVariation = Vec2.random(seed) * 32
    val p = position + positionVariation

    val angleVariation = Random.randomPiToMinusPi(seed)
    val startingAngleVariation = Random.randomPiToMinusPi(seed + 1)
    val a = startingAngleVariation + (angleVariation * timePassed) * .075f

    materialRenderer.draw(material, p, a, s * 2f, camera.projectionMatrix())
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