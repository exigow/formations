package commons.math

import java.util.*

object ConvexHull {

  fun calculate(vararg input: Vec2) = calculate(input.toList())

  fun calculate(input: List<Vec2>): List<Vec2> = when (input.size) {
    0 -> emptyList()
    1 -> input
    else -> calculateHull(input)
  }

  private fun calculateHull(input: List<Vec2>): List<Vec2> {
    val inflated = input.flatMap { inflate(it, 32f) }
    val arrayInput = vectorsToFloatArray(inflated)
    val arrayConvex = com.badlogic.gdx.math.ConvexHull().computePolygon(arrayInput, false)
    return floatArrayToVectors(arrayConvex)
  }

  private fun vectorsToFloatArray(vectors: List<Vec2>): com.badlogic.gdx.utils.FloatArray {
    val points = com.badlogic.gdx.utils.FloatArray()
    for (vector in vectors) {
      points.add(vector.x)
      points.add(vector.y)
    }
    return points
  }

  private fun floatArrayToVectors(array: com.badlogic.gdx.utils.FloatArray): List<Vec2> {
    val result = ArrayList<Vec2>()
    var i = 0
    while (i < array.size) {
      val x = array[i++]
      val y = array[i++]
      result.add(Vec2(x, y))
    }
    return result
  }

  private fun inflate(vec: Vec2, scale: Float, quality: Int = 16) = (1..quality).map {
    vec + Vec2.rotated(it.toFloat() / quality * FastMath.pi * 2) * scale
  }.toList()

}