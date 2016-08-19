package rendering

import Vec2
import rendering.materials.Material
import rendering.renderers.Renderable


data class Sprite (
  val material: Material,
  val position: Vec2,
  val scale: Float = 1f,
  val angle: Float = 0f,
  val depth: Float = 0f
) : Renderable {

  override fun depth() = depth

}
