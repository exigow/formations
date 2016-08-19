import org.testng.Assert.assertEquals
import org.testng.Assert.assertTrue
import org.testng.annotations.Test

class Vec2Test {

  @Test fun testPlusOperator() {
    assertEquals(Vec2(1, 2) + Vec2(3, 4), Vec2(4, 6))
  }

  @Test fun testMinusOperator() {
    assertEquals(Vec2(1, 2) - Vec2(3, 4), Vec2(-2, -2))
  }
  
  @Test fun testTimesOperator() {
    assertEquals(Vec2(1, 2) * Vec2(3, 4), Vec2(3, 8))
    assertEquals(Vec2(1, 2) * 3, Vec2(3, 6))
  }

  @Test fun testDivOperator() {
    assertEquals(Vec2(1, 2) / Vec2(5, 4), Vec2(.2f, .5f))
    assertEquals(Vec2(2, 4) / 2, Vec2(1, 2))
  }

  @Test fun testLength() {
    assertEquals(Vec2.zero().length(), 0f)
    assertEquals(Vec2(1, 0).length(), 1f)
    assertEquals(Vec2(3, 4).length(), 5f)
  }

  @Test fun testDistanceTo() {
    assertEquals(Vec2.zero().distanceTo(Vec2(3, 4)), 5f)
  }

  @Test fun testNormalization() {
    assertEquals(Vec2(1, 0).normalize(), Vec2(1, 0))
    assertEquals(Vec2(.0542f, 0).normalize(), Vec2(1, 0))
    assertEquals(Vec2(102145313, 0).normalize(), Vec2(1, 0))
    assertEquals(Vec2(0, .5f).normalize(), Vec2(0, 1))
    assertEquals(Vec2.one().normalize(), Vec2(.70710677f, .70710677f))
  }

  @Test fun expectZeroAsResultOfZeroNormalization() {
    assertEquals(Vec2.zero().normalize(), Vec2.zero())
  }

  @Test fun testIsZero() {
    assertTrue(Vec2.zero().isZero())
  }

  @Test fun testStaticFactories() {
    assertEquals(Vec2.zero(), Vec2(0, 0))
    assertEquals(Vec2.one(), Vec2(1, 1))
  }

}