package assets

import assets.templates.MaterialTemplate
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
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
    val diffuse = loadTexture(textureDiffuseFilename, magTypedFilter, minTypedFilter)
    val emissive = loadTexture(textureEmissiveFilename, magTypedFilter, minTypedFilter)
    return Material(diffuse, emissive)
  }

  private fun typedFilterOf(stringlyValue: String): Texture.TextureFilter = when(stringlyValue) {
    "nearest" -> Texture.TextureFilter.Nearest
    "linear" -> Texture.TextureFilter.Linear
    "mipmap" -> Texture.TextureFilter.MipMap
    else -> throw RuntimeException("unknown filter [$stringlyValue]")
  }

  private fun loadTexture(filename: String?, magFilter: Texture.TextureFilter, minFilter: Texture.TextureFilter): Texture? {
    if (filename == null)
      return null;
    val absolutePath = File(filename).absolutePath
    val mipmapped = isMipmapped(magFilter, minFilter)
    val file = Gdx.files.absolute(absolutePath)
    val texture = Texture(file, mipmapped)
    texture.setFilter(minFilter, magFilter)
    val wrap = Texture.TextureWrap.Repeat
    texture.setWrap(wrap, wrap)
    return texture
  }

  private fun isMipmapped(magFilter: Texture.TextureFilter, minFilter: Texture.TextureFilter): Boolean {
    val mipmap = Texture.TextureFilter.MipMap
    return magFilter == mipmap || minFilter == mipmap
  }

}