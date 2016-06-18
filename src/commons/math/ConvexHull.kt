package commons.math

import com.badlogic.gdx.utils.FloatArray
import java.util.*

object ConvexHull {

  fun calculate(vararg input: Vec2) = calculate(input.toList())

  fun calculate(input: List<Vec2>): List<Vec2> = when (input.size) {
    0 -> emptyList()
    1 -> input
    else -> calculateHull(input)
  }

  private fun calculateHull(input: List<Vec2>): List<Vec2> {
    val arrayInput = vectorsToFloatArray(input)
    val arrayConvex = com.badlogic.gdx.math.ConvexHull().computePolygon(arrayInput, false)
    return floatArrayToVectors(arrayConvex)
  }

  private fun vectorsToFloatArray(vectors: List<Vec2>): FloatArray {
    val points = FloatArray()
    for (vector in vectors) {
      points.add(vector.x)
      points.add(vector.y)
    }
    return points
  }

  private fun floatArrayToVectors(array: FloatArray): List<Vec2> {
    val result = ArrayList<Vec2>()
    var i = 0
    while (i < array.size) {
      val x = array[i++]
      val y = array[i++]
      result.add(Vec2(x, y))
    }
    return result
  }

}