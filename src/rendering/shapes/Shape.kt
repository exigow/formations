package rendering.shapes

import commons.math.Vec2

class Shape(val paths: List<Path>) {

  fun scale(scalar: Vec2) = Shape(paths.map { Path(it.elements.map { it * scalar }) })

  override fun toString() = "paths: ${paths.map { it.elements }}"

  companion object {

    fun singleton(elements: List<Vec2>): Shape = Shape(listOf(Path(elements)))

  }

}