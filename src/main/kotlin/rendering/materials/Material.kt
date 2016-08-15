package rendering.materials

import com.badlogic.gdx.graphics.Texture
import commons.math.Vec2
import rendering.Blending


data class Material(
  val diffuse: Texture?,
  val emissive: Texture?,
  val blending: Blending
) {

  fun size() = Vec2(diffuse!!.width, diffuse.height)

}