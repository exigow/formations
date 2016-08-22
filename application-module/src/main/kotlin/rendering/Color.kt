package rendering

data class Color(val red: Float, val green: Float, val blue: Float) {

  companion object {

    val white = Color(1f, 1f, 1f)
    val veryLightGray = Color(.925f, .925f, .925f)
    val lightGray = Color(.75f, .75f, .75f)
    val gray = Color(.5f, .5f, .5f)
    val darkGray = Color(.25f, .25f, .25f)
    val veryDarkGray = Color(.075f, .075f, .075f)
    val black = Color(0f, 0f, 0f)
    val fullRed = Color(1f, 0f, 0f)
    val fullGreen = Color(0f, 1f, 0f)
    val fullBlue = Color(0f, 0f, 1f)

    fun fromGdx(gdxColor: com.badlogic.gdx.graphics.Color) = Color(gdxColor.r, gdxColor.g, gdxColor.b)

    fun fromInt(value: Int) = fromGdx(com.badlogic.gdx.graphics.Color(value))

  }

  fun toFloatArray() = floatArrayOf(red, green, blue)

}