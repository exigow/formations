package assets.utilities

import java.io.File
import java.util.*


internal object AssetFinder {

  fun find(filter: (filename: String) -> Boolean) = collectFiles("data/")
    .filter { filter.invoke(it.name) }

  private fun collectFiles(directoryName: String): List<File> {
    val directory = File(directoryName)
    val result = ArrayList<File>()
    val files = directory.listFiles()
    result.addAll(files.asList())
    for (file in files)
      if (file.isDirectory)
        result.addAll(collectFiles(file.absolutePath))
    return result
  }

}