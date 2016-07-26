package rendering.materials

import com.badlogic.gdx.graphics.Texture


data class Material(
  val diffuse: Texture?,
  val emissive: Texture?
)