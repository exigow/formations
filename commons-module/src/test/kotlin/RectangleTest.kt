import org.testng.Assert.assertEquals
import org.testng.Assert.assertTrue
import org.testng.annotations.Test

class RectangleTest {

  @Test
  fun createValidRectangles() {
    Rectangle(0, 0, 32, 32)
    Rectangle(-32, -32, 32, 32)
  }

  @Test // todo (expectedExceptions = {RuntimeException::class.javaClass})
  fun createInvalidRectangle() {
    Rectangle(32, 32, -32, -32)
  }

  @Test
  fun testContainsResult() {
    val rect = Rectangle(-8, -8, 8, 8)
    fun assertContains(x: Int, y: Int) = assertTrue(rect.contains(Vec2(x, y)))
    assertContains(0, 0)
    assertContains(7, 7)
    assertContains(-7, -7)
    assertContains(7, -7)
    assertContains(-7, 7)
    assertContains(3, 2)
    assertContains(-5, 3)
    assertContains(0, -2)
  }

  @Test
  fun testCornersResult() {
    val rect = Rectangle(-8, -8, 8, 8)
    val corners = rect.corners()
    val iter = corners.iterator()
    assertEquals(iter.next(), Vec2(-8, -8)) // left up
    assertEquals(iter.next(), Vec2(8, -8)) // right up
    assertEquals(iter.next(), Vec2(8, 8)) // right down
    assertEquals(iter.next(), Vec2(-8, 8)) // left down
  }

  @Test
  fun testCenteredWithSizeResult() {
    val rect = Rectangle.centeredWithSize(Vec2(2, 3), Vec2(4, 4))
    assertEquals(rect.leftUpCorner, Vec2(0, 1))
    assertEquals(rect.rightDownCorner, Vec2(4, 5))
  }

}