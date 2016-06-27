package rendering.shapes

import commons.math.Vec2

class Shape(val paths: List<Path>) {

  fun scale(scalar: Vec2): Shape = TODO()

  fun translate(translation: Vec2): Shape = TODO()

  companion object {

    fun singleton(elements: List<Vec2>) = singleton(Path(elements))

    fun singleton(path: Path) = Shape(listOf(path))

    fun singleton(vararg elements: Vec2) = singleton(Path(elements.toList()))

    fun of(vararg paths: Path) = Shape(paths.toList())

  }

}