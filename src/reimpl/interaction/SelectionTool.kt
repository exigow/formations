package interaction

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

class SelectionTool {

  private val startPivot = Vector2()
  private val endPivot = Vector2()
  private val rect = Rectangle()

  fun startFrom(where: Vector2) = startPivot.set(where)

  fun endTo(where: Vector2) {
    endPivot.set(where)
    val updatedRect = convert(startPivot, endPivot);
    rect.set(updatedRect);
  }

  fun selectionRectangle()  = rect

  fun distanceFromStartingPoint() = startPivot.dst(endPivot)

  private companion object Converter {

    fun convert(pinPoint: Vector2, pointer: Vector2): Rectangle {
      val width = pointer.x - pinPoint.x
      val height = pointer.y - pinPoint.y
      val rect = Rectangle(pinPoint.x, pinPoint.y, width, height)
      return fixInvertedVectors(rect)
    }

    fun fixInvertedVectors(source: Rectangle): Rectangle {
      val fixed = Rectangle(source)
      if (fixed.width < 0) {
        fixed.width = Math.abs(fixed.width)
        fixed.x = fixed.getX() - fixed.width
      }
      if (fixed.height < 0) {
        fixed.height = Math.abs(fixed.height)
        fixed.y = fixed.getY() - fixed.height
      }
      return fixed
    }

  }

}