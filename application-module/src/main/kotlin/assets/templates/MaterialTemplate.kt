package assets.templates

import assets.templates.TemplateValidator.ensureNotEmpty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import Vec2
import java.io.File

data class MaterialTemplate(
  val id: String,
  val origin: Vec2?,
  val textureDiffuseFilename: String?,
  val textureEmissiveFilename: String?,
  val magFilter: String?,
  val minFilter: String?,
  val blending: String?,
  val isIlluminated: Boolean?
) {

  init {
    validate()
  }

  fun validate() {
    ensureNotEmpty(id)
  }

  companion object {

    val values = jacksonObjectMapper().readValue(File("data/materials.json"), Array<MaterialTemplate>::class.java)

  }

}