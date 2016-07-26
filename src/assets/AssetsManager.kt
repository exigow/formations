package assets

import commons.Logger
import java.util.*

object AssetsManager {

  private val assets: Map<String, Any> = loadAllAssets()

  fun peekMaterial(assetName: String) = peek(assetName) as Material

  private fun peek(assetName: String): Any {
    if (assets.containsKey(assetName))
      return assets[assetName]!!
    throw RuntimeException("Unknown asset: $assetName")
  }

  fun loadAllAssets(): Map<String, Any> {
    val map = HashMap<String, Any>()
    val materials = MaterialLoader.loadMaterials()
    map.putAll(materials)
    // todo shaders
    for (entry in map)
      Logger.ASSETS.log("Loaded asset: ${entry.key} -> ${entry.value}")
    return map
  }

}