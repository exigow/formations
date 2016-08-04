package assets

import com.badlogic.gdx.graphics.glutils.ShaderProgram
import commons.Logger
import rendering.materials.Material
import java.util.*

object AssetsManager {

  private val assets: Map<String, Any> = loadAllAssets()

  fun peekMaterial(assetName: String) = peek(assetName) as Material

  fun peekShader(assetName: String) = peek(assetName) as ShaderProgram

  private fun peek(assetName: String): Any {
    if (assets.containsKey(assetName))
      return assets[assetName]!!
    throw RuntimeException("Unknown asset: $assetName")
  }

  private fun loadAllAssets(): Map<String, Any> {
    val map = HashMap<String, Any>()
    map.safePutAll(MaterialFactory.loadAllIndexed())
    map.safePutAll(ShaderProgramLoader.loadShaderPrograms())
    for (entry in map)
      Logger.ASSETS.log("Loaded asset: ${entry.key} -> ${entry.value}")
    return map
  }

  private fun MutableMap<String, Any>.safePutAll(other: Map<String, Any>) {
    for (entry in other) {
      if (containsKey(entry.key))
        throw RuntimeException("asset with name ${entry.key} exists")
      put(entry.key, entry.value)
    }
  }

}