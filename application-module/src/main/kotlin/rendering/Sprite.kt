package rendering

import Vec2
import Vec2.Transformations.rotate
import Vec2.Transformations.scale
import Vec2.Transformations.translate
import assets.AssetsManager
import core.Camera
import rendering.materials.Material
import rendering.renderers.Renderable

data class Sprite (
  val material: Material,
  val position: Vec2,
  val scale: Vec2 = Vec2.one(),
  val angle: Float = 0f,
  val depth: Float = 0f,
  val alpha: Float = 1f,
  // todo blending: Color = Color.white
  val isCulled: Boolean = true
) : Renderable {

  constructor(spriteName: String, position: Vec2, scale: Vec2 = Vec2.one(), angle: Float = 0f, depth: Float = 0f, alpha: Float = 1f, isCulled: Boolean = true):
    this(AssetsManager.peekMaterial(spriteName), position, scale, angle, depth, alpha, isCulled)

  override fun depth() = depth

  override fun isVisible(camera: Camera) = !(!isInsideCamera(camera) && isCulled) // todo o kurwa

  private fun isInsideCamera(camera: Camera) = camera.worldVisibilityRectangle(512f / camera.renderingScale()).contains(position.toVector2())

  fun toVertices() = listOf(Vec2(0, 0), Vec2(1, 0), Vec2(1, 1),Vec2(0, 1))
    .scale(material.size())
    .translate(material.origin * -1f)
    .scale(scale * .25f * Vec2(1f, -1f))
    .rotate(angle)
    .translate(position)

}
