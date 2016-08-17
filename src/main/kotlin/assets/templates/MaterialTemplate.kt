package assets.templates

import assets.templates.TemplateValidator.ensureNotEmpty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import commons.math.Vec2
import java.io.File

data class MaterialTemplate(
  val id: String,
  val origin: Vec2?,
  val textureDiffuseFilename: String?,
  val textureEmissiveFilename: String?,
  val magFilter: String,
  val minFilter: String,
  val blending: String?
) {

  init {
    validate()
  }

  fun validate() {
    ensureNotEmpty(id)
    ensureNotEmpty(magFilter)
    ensureNotEmpty(minFilter)
  }

  companion object {

    val values = jacksonObjectMapper().readValue(File("data/materials.json"), Array<MaterialTemplate>::class.java)

  }

}