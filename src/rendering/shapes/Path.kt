package rendering.shapes

import commons.math.Vec2

class Path(val positions: List<Vec2>) {

  fun scale(scalar: Vec2) = Path(positions.map  { v -> v * scalar})

  override fun toString() = "$positions"

}
