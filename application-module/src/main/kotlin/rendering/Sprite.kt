package rendering

import Vec2
import rendering.materials.Material
import rendering.renderers.Renderable


data class Sprite (
  val material: Material,
  val position: Vec2,
  val scale: Float = 1f,
  val angle: Float = 0f,
  val depth: Float = 0f,
  val isCulled: Boolean = true
) : Renderable {

  override fun depth() = depth

}
