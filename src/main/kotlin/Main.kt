import assets.AssetsManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import commons.math.FastMath
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
import rendering.trails.TrailsRenderer
import rendering.utils.TextureToValueConverter
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
  private val asteroidsSource = TextureToValueConverter.convert(AssetsManager.peekMaterial("asteroid-mask-test").diffuse!!, {color -> color.r})
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
    generateAsteroidInstances().forEach {
      val chosenAsset = Random.chooseRandomly(it.toSeed(), "asteroid-rock-a", "asteroid-rock-b", "asteroid-rock-c" ,"asteroid-rock-d")
      val positionVariation = Vec2.random(it.toSeed()) * 32
      val angleSeed = Random.randomFloatNormalized(it.toSeed())
      val angle = (angleSeed * FastMath.pi) + (angleSeed * timePassed * .075f)
      val sizeMultiplier = Random.randomFloatRange(it.toSeed(), .5f, 4f)
      materialRenderer.draw(AssetsManager.peekMaterial(chosenAsset), it.toVec2() + positionVariation, angle, it.value * sizeMultiplier, camera.projectionMatrix())
    }
    world.allShips().forEach {
      it.render(materialRenderer, trailsRenderer, camera.projectionMatrix())
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

      /*if (context.selectionRect != null) {
        val rect = context.selectionRect!!
        val from = Vec2(rect.x, rect.y)
        val to = Vec2(rect.x + rect.width, rect.y + rect.height)

        SlicedRectangleRenderer.render(from, to, AssetsManager.peekMaterial("rect").diffuse!!, camera.projectionMatrix())
      }*/
      uiRenderer.render(delta)
    }
    gbuffer.showCombined()
  }

  private fun generateAsteroidInstances() = asteroidsSource.toSetOfValuePoints()
    .filter { it.value > .075f }
    .map { it.translatePosition(-16, -16).scalePosition(128) }
    .filter { camera.worldVisibilityRectangle(-128f).contains(it.toVec2().toVector2()) }
    .filter { it.value > camera.renderingScale() * .0375 }

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