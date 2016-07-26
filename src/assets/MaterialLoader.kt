package assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import java.io.File


object MaterialLoader {

  fun loadMaterials(): Map<String, Material> {
    val potentialFiles = AssetFinder.find { it.endsWith(".png") }
    return potentialFiles.map { it.toIdentifier() }.map {
      it to loadMaterial(it, potentialFiles)
    }.toMap()
  }

  private fun loadMaterial(id: String, files: Array<File>) = Material(
      diffuse = files.findMatching(id, "diffuse")?.loadAsTexture(),
      emissive = files.findMatching(id, "emissive")?.loadAsTexture()
    )

  private fun Array<File>.findMatching(id: String, type: String) = this
    .filter { it.name.contains(id) }
    .filter { it.name.contains(type) }
    .firstOrNull()

  private fun File.toIdentifier() = name.substringBefore('-', name.removeSuffix(".png"))

  private fun File.loadAsTexture(): Texture {
    val tex = Texture(Gdx.files.absolute(absolutePath))
    val filter = Texture.TextureFilter.Linear
    val wrap = Texture.TextureWrap.Repeat
    tex.setFilter(filter, filter)
    tex.setWrap(wrap, wrap)
    return tex
  }

}