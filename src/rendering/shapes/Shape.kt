package rendering.shapes

import commons.math.Vec2

class Shape(val paths: List<Path>) {

  fun scale(scalar: Vec2) = applyToVectors({v: Vec2 -> v * scalar})

  fun translate(translation: Vec2) = applyToVectors({v: Vec2 -> v + translation})

  private inline fun applyToVectors(operation: (Vec2) -> Vec2) = Shape(paths.map { Path(it.elements.map { operation.invoke(it) }) })

  companion object {

    fun singleton(elements: List<Vec2>) = singleton(Path(elements))

    fun singleton(path: Path) = Shape(listOf(path))

    fun singleton(vararg elements: Vec2) = singleton(Path(elements.toList()))

    fun of(vararg paths: Path) = Shape(paths.toList())

  }

}