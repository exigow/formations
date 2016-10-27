data class Color(
  val red: Float,
  val green: Float,
  val blue: Float
) {

  companion object {

    val white = Color(1f, 1f, 1f)
    val veryLightGray = Color(.925f, .925f, .925f)
    val lightGray = Color(.75f, .75f, .75f)
    val gray = Color(.5f, .5f, .5f)
    val darkGray = Color(.25f, .25f, .25f)
    val veryDarkGray = Color(.075f, .075f, .075f)
    val black = Color(0f, 0f, 0f)
    val red = Color(1f, 0f, 0f)
    val green = Color(0f, 1f, 0f)
    val blue = Color(0f, 0f, 1f)
    val cyan = Color(0f, 1f, 1f)
    val teal = Color(0f, .5f, 5f)

    fun fromInteger(r: Int, g: Int, b: Int): Color {
      if (listOf(r, g, b).filter { it > 255 || it < 0}.any())
        throw IllegalArgumentException("Integer factory requires values in range: [0..255]")
      return Color(r / 255f, g / 255f, b / 255f)
    }

    fun fromRGBA8888(packed: Int) = Color(
      red = (packed and 0xff000000.toInt()).ushr(24) / 255f,
      green = (packed and 0x00ff0000).ushr(16) / 255f,
      blue = (packed and 0x0000ff00).ushr(8) / 255f
      // alpha channel is ignored
    )

    fun fromRGB888(packed: Int) = Color(
      red = (packed and 0x00ff0000).ushr(16) / 255f,
      green = (packed and 0x0000ff00).ushr(8) / 255f,
      blue = (packed and 0x000000ff) / 255f
    )

  }

  fun toFloatArray() = floatArrayOf(red, green, blue)

  fun toByteArray() = toFloatArray()
    .map { channel -> toByte(channel) }
    .toIntArray()

  fun clamp(): Color {
    fun Float.clamp() = if (this > 1f)
        1f
      else if (this < 0f)
        0f
      else this
    return Color(red.clamp(), green.clamp(), blue.clamp())
  }

  fun luminance(): Float {
    val lumColor = Color(0.2126f, 0.7152f, 0.0722f)
    return (this * lumColor).combineChannels()
  }

  fun toRGB888() = toByte(red) shl 16 or (toByte(green) shl 8) or toByte(blue)

  operator fun plus(other: Color) = copy(red + other.red, green + other.green, blue + other.blue).clamp()

  operator fun minus(other: Color) = copy(red - other.red, green - other.green, blue - other.blue).clamp()

  operator fun times(other: Color) = copy(red * other.red, green * other.green, blue * other.blue).clamp()

  fun combineChannels() = red + green + blue

  private fun toByte(channel: Float) = (channel * 255).toInt()

}