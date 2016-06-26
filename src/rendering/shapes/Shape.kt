package rendering.shapes

import commons.math.Vec2

class Shape(val paths: List<Path>) {

  fun scale(scalar: Vec2): Shape = Shape(paths.map { it.scale(scalar) })

  override fun toString() = "paths: $paths"

  companion object {

    fun singleton(path: Path): Shape = Shape(listOf(path))

  }

}