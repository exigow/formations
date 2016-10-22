package rendering

import Vec2
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
  val isCulled: Boolean = true
) : Renderable {

  constructor(spriteName: String, position: Vec2, scale: Vec2 = Vec2.one(), angle: Float = 0f, depth: Float = 0f, isCulled: Boolean = true):
    this(AssetsManager.peekMaterial(spriteName), position, scale, angle, depth, isCulled)

  override fun depth() = depth

  override fun isVisible(camera: Camera) = !(!isInsideCamera(camera) && isCulled) // todo o kurwa

  private fun isInsideCamera(camera: Camera) = camera.worldVisibilityRectangle(512f / camera.renderingScale()).contains(position.toVector2())

}
