import assets.AssetsManager
import com.badlogic.gdx.Gdx
import Vec2
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Rectangle
import commons.Logger
import core.Camera
import core.actions.Action
import core.actions.ActionsRegistry
import core.actions.catalog.*
import core.input.event.bundles.ThreeStateButtonEventBundle
import core.input.mappings.MouseButton
import game.PlayerContext
import game.Squad
import game.World
import rendering.Color
import rendering.GBuffer
import rendering.NewUIRenderer
import rendering.Sprite
import rendering.canvas.FullscreenQuad
import rendering.procedural.ChunkToAsteroidConverter.toAsteroids
import rendering.procedural.TextureToChunkConverter
import rendering.renderers.GbufferRenderer
import rendering.utils.Draw
import ui.Widget

class Main {

  private val world = World.randomWorld()
  private val camera = Camera()
  private val actions = ActionsRegistry()
  private val context = PlayerContext()
  private val gbuffer = GBuffer.setUpWindowSize()
  private val chunks = TextureToChunkConverter.convert(AssetsManager.peekMaterial("asteroid-mask-test").diffuse!!, { c -> c.red })
  private var timePassed = 0f
  private val spriteRenderer = GbufferRenderer(gbuffer)
  private val newUIRenderer = NewUIRenderer(camera, context)
  private val widgets = (1..10)
    .map { p -> Vec2(p * 32, 0) }
    .map { v -> Widget(v + Vec2(-14, -16), v + Vec2(14, 16)) }
    .toList()

  init {
    actions.addAction(CameraScrollZoomAction(camera))
    actions.addAction(CameraMiddleClickMovementAction(camera))
    actions.addAction(CameraArrowsMovementAction(camera))
    actions.addAction(SelectionAction(camera, world, context))
    actions.addAction(OrderingActionClass(camera, context, world))
    actions.addAction(CameraShipLockAction(camera, context))
    actions.addAction(WidgetClickAction(camera, widgets))
  }

  fun onFrame() {
    val delta = Gdx.graphics.deltaTime
    timePassed += delta
    camera.update(delta)
    actions.update(delta)
    world.update(delta)
    widgets.forEach {
      if (it.isHovered(camera.mousePosition())) {
        it.hover()
      }
      it.update(delta)
    }
    render();
  }

  fun render() {
    Draw.update(camera)
    gbuffer.clear()
    renderFullscreenBackgroundImage();

    val asteroidSprites = chunks.toAsteroids(camera, timePassed)
    val shipSprites = world.allShips().map { it.toRenderable() }.flatten()
    val allSprites = asteroidSprites + shipSprites
    spriteRenderer.render(allSprites, camera)



    gbuffer.paintOnUserInterface {
      newUIRenderer.render()
      widgets.forEach { it.draw() }
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

  private class WidgetClickAction(private val camera: Camera, private val widgets: List<Widget>) : Action {

    private val events = object : ThreeStateButtonEventBundle(MouseButton.MOUSE_LEFT) {

      override fun onPress() {
        widgets.find { it.isHovered(camera.mousePosition()) }?.click()
      }

      override fun onRelease() {
      }

      override fun onHold(delta: Float) {
      }

    }.toBundle()

    override fun events() = events


  }


}