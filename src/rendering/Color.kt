package rendering

enum class Color(val r: Float, val g: Float, val b: Float) {

  WHITE(1f, 1f, 1f),
  LIGHT_GRAY(.75f, .75f, .75f),
  GRAY(.5f, .5f, .5f),
  DARK_GRAY(.25f, .25f, .25f),
  BLACK(0f, 0f, 0f),

  FULL_RED(1f, 0f, 0f),
  FULL_GREEN(0f, 1f, 0f),
  FULL_BLUE(0f, 0f, 1f)

}