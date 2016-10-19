package rendering.materials

import com.badlogic.gdx.graphics.Texture
import Vec2
import rendering.Blending


data class Material(
  val diffuse: Texture?,
  val emissive: Texture?,
  val blending: Blending,
  val origin: Vec2,
  val isIlluminated: Boolean = true
) {

  fun size() = Vec2(diffuse!!.width, diffuse.height)

}