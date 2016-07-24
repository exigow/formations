package assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import commons.Logger
import java.io.File
import java.io.FilenameFilter
import java.util.*

class AssetsManager {

  private val textures: MutableMap<String, Texture>  = HashMap()

  operator fun get(assetName: String): Texture {
    if (textures.containsKey(assetName))
      return textures[assetName]!!
    throw RuntimeException("Unknown asset: $assetName")
  }

  companion object {

    fun load(): AssetsManager {
      val man = AssetsManager()
      val files = findTextureFiles()
      Logger.ASSETS.log("Textures found: ${Arrays.toString(files)}")
      for (file in files) {
        val name = extractName(file)
        Logger.ASSETS.log("Loading texture: $name")
        val texture = loadTexture(file.absolutePath)
        man.textures.put(name, texture)
      }
      return man
    }

    private fun findTextureFiles(): Array<File> {
      val dataDir = File("data/")
      val pngFiles = FilenameFilter { dir, name ->
        if (dir == null)
          return@FilenameFilter false
        name.endsWith(".png")
      }
      return dataDir.listFiles(pngFiles)
    }

    private fun extractName(file: File) = file.name.removeSuffix(".png")

    private fun loadTexture(path: String): Texture {
      val tex = Texture(Gdx.files.absolute(path))
      val filter = Texture.TextureFilter.Linear
      val wrap = Texture.TextureWrap.Repeat
      tex.setFilter(filter, filter)
      tex.setWrap(wrap, wrap)
      return tex
    }

  }

}