package rendering.renderers

import assets.AssetsManager
import core.Camera
import rendering.GBuffer
import rendering.Sprite
import rendering.renderers.specialized.MaterialRenderer
import rendering.renderers.specialized.TrailsRenderer
import rendering.trails.Trail

class GbufferRenderer(private val gbuffer: GBuffer) {

  private val immediateRenderer = MaterialRenderer(gbuffer)
  private val trailsRenderer = TrailsRenderer(gbuffer)

  fun render(renderables: Collection<Renderable>, camera: Camera) {
    val sorted = renderables.sortedBy { it.depth() }
    for (instance in sorted) {
      when (instance) {
        is Sprite -> renderSprite(instance, camera)
        is Trail -> renderTrail(instance, camera)
      }
    }
  }

  private fun renderSprite(sprite: Sprite, camera: Camera) {
    if (!sprite.isVisible(camera) && sprite.isCulled)
      return
    immediateRenderer.draw(sprite.material, sprite.position, sprite.depth, sprite.angle, sprite.scale, camera.projectionMatrix())
  }

  private fun renderTrail(trail: Trail, camera: Camera) {
    val material = AssetsManager.peekMaterial("trail")
    trailsRenderer.render(trail, material, camera.projectionMatrix())
  }

  private fun Sprite.isVisible(camera: Camera) = camera.worldVisibilityRectangle(512f / camera.renderingScale()).contains(position.toVector2())

}
