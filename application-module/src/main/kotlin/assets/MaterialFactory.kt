package assets

import Vec2
import assets.templates.MaterialTemplate
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import rendering.Blending
import rendering.materials.Material
import java.io.File


object MaterialFactory {

  fun load(template: MaterialTemplate) = template.toMaterial()

  // todo move this to manager to keep SRP rule
  fun loadAllIndexed() = MaterialTemplate.values.map {
    it.id to MaterialFactory.load(it)
  }.toMap()

  private fun MaterialTemplate.toMaterial(): Material {
    val magTypedFilter = typedFilterOf(magFilter)
    val minTypedFilter = typedFilterOf(minFilter)
    val typedBlending = typedBlendingOf(blending)
    val diffuse = loadTexture(textureDiffuseFilename, magTypedFilter, minTypedFilter)
    val emissive = loadTexture(textureEmissiveFilename, magTypedFilter, minTypedFilter)
    val fixedOrigin = resolveOrigin(Vec2(diffuse!!.width, diffuse!!.height))
    val illuminated: Boolean
    if (isIlluminated == null)
      illuminated = true
    else illuminated = false
    return Material(diffuse, emissive, typedBlending, fixedOrigin, illuminated)
  }

  private fun MaterialTemplate.resolveOrigin(size: Vec2) = when (origin) {
    null -> size / 2
    else -> origin
  }

  private fun typedFilterOf(stringlyValue: String?): Texture.TextureFilter = when(stringlyValue) {
    null, "linear" -> Texture.TextureFilter.Linear
    "nearest" -> Texture.TextureFilter.Nearest
    "mipmap" -> Texture.TextureFilter.MipMap
    else -> throw RuntimeException("unknown filter [$stringlyValue]")
  }

  private fun typedBlendingOf(stringlyValue: String?): Blending {
    if (stringlyValue == null)
      return Blending.TRANSPARENCY
    return when (stringlyValue) {
      "transparency" -> Blending.TRANSPARENCY
      "additive" -> Blending.ADDITIVE
      else -> throw RuntimeException("unknown blending [$stringlyValue]")
    }
  }

  private fun loadTexture(filename: String?, magFilter: Texture.TextureFilter, minFilter: Texture.TextureFilter): Texture? {
    if (filename == null)
      return null
    val absolutePath = File(filename).absolutePath
    val mipmapped = isMipmapped(magFilter, minFilter)
    val file = Gdx.files.absolute(absolutePath)
    val texture = Texture(file, mipmapped)
    texture.setFilter(minFilter, magFilter)
    val wrap = Texture.TextureWrap.MirroredRepeat
    texture.setWrap(wrap, wrap)
    return texture
  }

  private fun isMipmapped(magFilter: Texture.TextureFilter, minFilter: Texture.TextureFilter): Boolean {
    val mipmap = Texture.TextureFilter.MipMap
    return magFilter == mipmap || minFilter == mipmap
  }

}
