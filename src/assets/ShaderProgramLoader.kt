package assets

import assets.utilities.AssetFinder
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import java.io.File


object ShaderProgramLoader {

  fun loadShaderPrograms(): Map<String, ShaderProgram> {
    val potentialFiles = AssetFinder.find { it.endsWith(".glsl") }
    return potentialFiles.map { it.toIdentifier() }.distinct().map {
      it to loadShader(it, potentialFiles)
    }.toMap()
  }

  private fun loadShader(id: String, files: Array<File>) = ShaderProgram(
    files.findMatching(id, "vertex")!!.loadAsString(),
    files.findMatching(id, "fragment")!!.loadAsString()
  )

  private fun File.toIdentifier() = name.substringBefore('-', name.removeSuffix(".glsl"))

  private fun Array<File>.findMatching(id: String, type: String) = this
    .filter { it.name.contains(id) }
    .filter { it.name.contains(type) }
    .firstOrNull()

  private fun File.loadAsString() = Gdx.files.internal(absolutePath).readString()

}