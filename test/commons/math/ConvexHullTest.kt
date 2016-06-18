package commons.math

import org.testng.Assert.*
import org.testng.annotations.Test

class ConvexHullTest {

  @Test fun emptyHullIfInputIsEmpty() {
    assertEquals(ConvexHull.calculate(emptyList()), emptyList<Vec2>())
  }

  @Test fun oneElementIfInputSizeIsOne() {
    assertEquals(ConvexHull.calculate(Vec2.zero()), listOf(Vec2.zero()))
  }

  @Test fun asdasda() {
    val input = listOf(
      Vec2(31, 27),
      Vec2(84, 14),
      Vec2(126, 21),
      Vec2(132, 43),
      Vec2(103, 65),
      Vec2(78, 70),
      Vec2(49, 61),
      Vec2(15, 64)
    )
    println(input)
    val output = ConvexHull.calculate(input)
    println(output)

  }

}