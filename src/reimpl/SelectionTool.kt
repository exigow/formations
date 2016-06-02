import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import game.Ship
import game.World
import java.util.*

class SelectionTool {

  private val startPivot = Vector2()
  private val endPivot = Vector2()
  private val list = ArrayList<Ship>()
  private val rect = Rectangle()

  fun startFrom(where: Vector2) = startPivot.set(where)

  fun endTo(where: Vector2) = endPivot.set(where)

  fun updateSelection(world: World) {
    list.clear()
    val updatedRect = Converter.convert(startPivot, endPivot);
    rect.set(updatedRect);
    val shipsInside = world.collectShips().filter { rect.contains(it.position) }
    list.addAll(shipsInside)
  }

  fun selectedShips() = list

  fun selectionRectangle()  = rect

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