package assets.templates

import assets.templates.TemplateValidator.ensureNotEmpty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File

// todo implement sprite origin
data class MaterialTemplate(
  val id: String,
  val originX: Float = 0f,
  val originY: Float = 0f,
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