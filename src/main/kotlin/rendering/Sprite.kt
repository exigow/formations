package rendering

import commons.math.Vec2
import rendering.materials.Material
import rendering.renderers.Renderable


data class Sprite (
  val material: Material,
  val position: Vec2,
  val scale: Float = 1f,
  val angle: Float = 0f,
  val depth: Float = 0f,
  val blending: Blending = Blending.TRANSPARENCY
) : Renderable {

  override fun depth() = depth

}
