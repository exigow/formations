package assets.utilities

import java.io.File
import java.io.FilenameFilter


internal object AssetFinder {

  fun find(filter: (filename: String) -> Boolean): Array<File> {
    val dataDir = File("data/")
    val pngFiles = FilenameFilter { dir, name ->
      if (dir == null)
        return@FilenameFilter false
      filter.invoke(name)
    }
    return dataDir.listFiles(pngFiles)
  }

}