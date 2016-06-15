package commons.math

import org.testng.Assert.assertEquals
import org.testng.Assert.assertTrue
import org.testng.annotations.Test

class Vec2Test {

  @Test fun testPlusOperator() {
    assertEquals(Vec2(1f, 2f) + Vec2(3f, 4f), Vec2(4f, 6f))
  }

  @Test fun testMinusOperator() {
    assertEquals(Vec2(1f, 2f) - Vec2(3f, 4f), Vec2(-2f, -2f))
  }
  
  @Test fun testTimesOperator() {
    assertEquals(Vec2(1f, 2f) * Vec2(3f, 4f), Vec2(3f, 8f))
    assertEquals(Vec2(1f, 2f) * 3f, Vec2(3f, 6f))
  }

  @Test fun testDivOperator() {
    assertEquals(Vec2(1f, 2f) / Vec2(5f, 4f), Vec2(.2f, .5f))
    assertEquals(Vec2(2f, 4f) / 2f, Vec2(1f, 2f))
  }

  @Test fun testLength() {
    assertEquals(Vec2.zero().length(), 0f)
    assertEquals(Vec2(1f, 0f).length(), 1f);
    assertEquals(Vec2(3f, 4f).length(), 5f);
  }

  @Test fun testDistanceTo() {
    assertEquals(Vec2.zero().distanceTo(Vec2(3f, 4f)), 5f)
  }

  @Test fun testNormalization() {
    assertEquals(Vec2(1f, 0f).normalize(), Vec2(1f, 0f))
    assertEquals(Vec2(.0542f, 0f).normalize(), Vec2(1f, 0f))
    assertEquals(Vec2(102145313f, 0f).normalize(), Vec2(1f, 0f))
    assertEquals(Vec2(0f, .5f).normalize(), Vec2(0f, 1f))
    assertEquals(Vec2.one().normalize(), Vec2(.70710677f, .70710677f))
  }

  @Test fun expectZeroAsResultOfZeroNormalization() {
    assertEquals(Vec2.zero().normalize(), Vec2.zero())
  }

  @Test fun testIsZero() {
    assertTrue(Vec2.zero().isZero())
  }

  @Test fun testStaticFactories() {
    assertEquals(Vec2.zero(), Vec2(0f, 0f))
    assertEquals(Vec2.one(), Vec2(1f, 1f))
  }

}