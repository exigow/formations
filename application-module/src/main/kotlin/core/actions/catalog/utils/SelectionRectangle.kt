package core.actions.catalog.utils

import com.badlogic.gdx.math.Rectangle
import Vec2

class SelectionRectangle {

  private var startPivot = Vec2.zero()
  private var endPivot = Vec2.zero()

  fun updatePivots(from: Vec2, to: Vec2) {
    startPivot = from
    endPivot = to
  }

  fun selectionRectangle() = convert(startPivot, endPivot)

  private companion object {

    fun convert(start: Vec2, end: Vec2): Rectangle {
      val width = end.x - start.x
      val height = end.y - start.y
      val rect = Rectangle(start.x, start.y, width, height)
      return fixInvertedVectors(rect)
    }

    fun fixInvertedVectors(source: Rectangle): Rectangle {
      val fixed = Rectangle(source)
      if (fixed.width < 0) {
        fixed.width = Math.abs(fixed.width)
        fixed.x = fixed.x - fixed.width
      }
      if (fixed.height < 0) {
        fixed.height = Math.abs(fixed.height)
        fixed.y = fixed.y - fixed.height
      }
      return fixed
    }

  }

}