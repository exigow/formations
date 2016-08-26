

data class Rectangle(val leftUpCorner: Vec2, val rightDownCorner: Vec2) {

  constructor(leftUpX: Int, leftUpY: Int, rightDownX: Int, rightDownY: Int): this(Vec2(leftUpX, leftUpY), Vec2(rightDownX, rightDownY))

  init {
    validate()
  }

  fun contains(position: Vec2): Boolean {
    val insideVertical = leftUpCorner.x < position.x && rightDownCorner.x > position.x
    val insideHorizontal = leftUpCorner.y < position.y && rightDownCorner.y > position.y
    return insideVertical and insideHorizontal
  }

  fun corners(): Array<Vec2> = arrayOf(
    leftUpCorner,
    Vec2(rightDownCorner.x, leftUpCorner.y),
    rightDownCorner,
    Vec2(leftUpCorner.x, rightDownCorner.y)
  )

  private fun validate() {
    fun invalidX() = leftUpCorner.x > rightDownCorner.x
    fun invalidY() = leftUpCorner.y > rightDownCorner.y
    if (invalidX() or invalidY())
      throw RuntimeException("Invalid rectangle: $this")
  }

  companion object {

    fun fromPositionedSize(position: Vec2, size: Vec2): Rectangle {
      val halfSize = size / 2
      return Rectangle(position - halfSize, position + halfSize)
    }

  }

}
