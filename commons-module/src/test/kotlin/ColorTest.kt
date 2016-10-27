import org.testng.Assert.assertEquals
import org.testng.annotations.Test

class ColorTest {

  @Test
  fun testIntegerFactory() {
    assertEquals(Color.fromInteger(255, 255, 255), Color.white)
    assertEquals(Color.fromInteger(0, 0, 0), Color.black)
    assertEquals(Color.fromInteger(255, 0, 0), Color.red)
    assertEquals(Color.fromInteger(0, 255, 0), Color.green)
    assertEquals(Color.fromInteger(0, 0, 255), Color.blue)
  }

  @Test(expectedExceptions = arrayOf(IllegalArgumentException::class), expectedExceptionsMessageRegExp = ".*requires values in range.*")
  fun throwExceptionWhenIntegerFactoryValueIsSmallerThan0() {
    Color.fromInteger(-13, 0, 0)
  }

  @Test(expectedExceptions = arrayOf(IllegalArgumentException::class), expectedExceptionsMessageRegExp = ".*requires values in range.*")
  fun throwExceptionWhenIntegerFactoryValueIsGreaterThan255() {
    Color.fromInteger(278, 0, 0)
  }

  @Test
  fun checkFloatArrayOutput() {
    assertEquals(Color.white.toFloatArray(), floatArrayOf(1f, 1f, 1f))
  }

  @Test
  fun checkByteArrayOutput() {
    assertEquals(Color.white.toByteArray(), intArrayOf(255, 255, 255))
    assertEquals(Color.black.toByteArray(), intArrayOf(0, 0, 0))
  }

  @Test
  fun testClamp() {
    assertEquals(Color.white.clamp(), Color.white)
    assertEquals(Color.black.clamp(), Color.black)
    assertEquals(Color(1.7f, -.75f, .2f).clamp(), Color(1f, 0f, .2f))
  }

  @Test
  fun testToRGB888() {
    assertEquals(Color.black.toRGB888(), 0x0)
    assertEquals(Color.white.toRGB888(), 0xffffff)
    assertEquals(Color.red.toRGB888(), 0xff0000)
    assertEquals(Color.green.toRGB888(), 0x00ff00)
    assertEquals(Color.blue.toRGB888(), 0x0000ff)
  }

  @Test
  fun testPlusOperator() {
    assertEquals(Color.red + Color.green + Color.blue, Color.white)
  }

  @Test
  fun testMinusOperator() {
    assertEquals(Color.white - Color.white, Color.black)
  }

  @Test
  fun testLuminance() {
    assertEquals(Color.white.luminance(), 1f)
    assertEquals(Color.black.luminance(), 0f)
    assertEquals(Color.red.luminance(), .2126f)
    assertEquals(Color.green.luminance(), .7152f)
    assertEquals(Color.blue.luminance(), .0722f)
  }

}