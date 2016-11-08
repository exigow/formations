package rendering.renderers

import assets.AssetsManager
import core.Camera
import rendering.GBuffer
import rendering.ImmediateDrawCall
import rendering.Sprite
import rendering.renderers.specialized.SpriteRenderer
import rendering.renderers.specialized.TrailRenderer
import rendering.trails.Trail

class GbufferRenderer(private val gbuffer: GBuffer) {

  private val spriteRenderer = SpriteRenderer()
  private val trailRenderer = TrailRenderer()

  fun render(renderables: Collection<Renderable>, camera: Camera) {
    val toRender = renderables
      .filter { it.isVisible(camera) }
      .sortedBy { it.depth() }
    gbuffer.paint {
      for (instance in toRender) {
        when (instance) {
          is Sprite -> renderSprite(instance, camera)
          is Trail -> renderTrail(instance, camera)
          is ImmediateDrawCall -> renderImmediateDrawCall(instance)
        }
      }
    }
  }

  private fun renderSprite(sprite: Sprite, camera: Camera) {
    spriteRenderer.draw(sprite, camera.projectionMatrix())
  }

  private fun renderTrail(trail: Trail, camera: Camera) {
    val material = AssetsManager.peekMaterial("trail")
    trailRenderer.render(trail, material, camera.projectionMatrix())
  }

  private fun renderImmediateDrawCall(call: ImmediateDrawCall) {
    gbuffer.paint {
      call.draw()
    }
  }

}
