package rendering.materials

import com.badlogic.gdx.graphics.Texture
import commons.math.Vec2


data class Material(
  val diffuse: Texture?,
  val emissive: Texture?
) {

  fun size() = Vec2(diffuse!!.width, diffuse.height)

}