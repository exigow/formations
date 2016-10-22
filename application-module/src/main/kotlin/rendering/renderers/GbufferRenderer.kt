package rendering.renderers

import assets.AssetsManager
import core.Camera
import rendering.GBuffer
import rendering.Sprite
import rendering.renderers.specialized.MaterialRenderer
import rendering.renderers.specialized.TrailsRenderer
import rendering.trails.Trail

class GbufferRenderer(private val gbuffer: GBuffer) {

  private val materialRenderer = MaterialRenderer(gbuffer)
  private val trailsRenderer = TrailsRenderer(gbuffer)

  fun render(renderables: Collection<Renderable>, camera: Camera) {
    val toRender = renderables
      .filter { it.isVisible(camera) }
      .sortedBy { it.depth() }
    for (instance in toRender) {
      when (instance) {
        is Sprite -> renderSprite(instance, camera)
        is Trail -> renderTrail(instance, camera)
      }
    }
  }

  private fun renderSprite(sprite: Sprite, camera: Camera) {
    materialRenderer.draw(sprite, camera.projectionMatrix())
  }

  private fun renderTrail(trail: Trail, camera: Camera) {
    val material = AssetsManager.peekMaterial("trail")
    trailsRenderer.render(trail, material, camera.projectionMatrix())
  }


}
