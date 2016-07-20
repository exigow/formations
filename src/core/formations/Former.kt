package core.formations

import commons.math.Vec2
import game.Ship


object Former {

  fun form(relative: Boolean, ships: List<Ship>, destination: Vec2, destinationAngle: Float) {
    val head: Ship = ships.closestTo(destination)
    head.moveTo(Place(destination, destinationAngle))
    val others = ships - head
    var left = others
    var prevLeft = head
    var prevRight = head
    var side = Side.LEFT
    while (!left.isEmpty()) {
      val prev = when (side) {
        Side.LEFT -> prevLeft
        Side.RIGHT -> prevRight
      }
      val place = calculatePlace(Place(prev.position, prev.angle), side)
      val closest = left.closestTo(place.position)
      when (side) {
        Side.LEFT -> prevLeft = closest
        Side.RIGHT -> prevRight = closest
      }
      side = side.swap()
      left -= closest
      closest.moveTo(place)
    }
  }

  private fun calculatePlace(relativeTo: Place, side: Side): Place {
    val pos = relativeTo.position + Vec2.rotated(relativeTo.angle + 2.25f * side.sign) * 32
    val angle = relativeTo.angle
    return Place(pos, angle)
  }

  private class Place(val position: Vec2, val angle: Float)

  private enum class Side(val sign: Int) {

    LEFT(-1), RIGHT(1);

    fun swap() = when (this) {
      LEFT -> RIGHT
      RIGHT -> LEFT
    }

  }

  //private fun generateSlot(relativeTo: Vec2, relativeAngle: Float, radius: Float, leftOrRight: Boolean): Vec2 {

  //}

  private fun List<Ship>.closestTo(position: Vec2) = minBy { it.position.distanceTo(position) }!!

  private fun Ship.moveTo(place: Place) {
    movementTarget = place.position
    movementTargetAngle = place.angle
  }


  /*
  fun putRelatively(who: Ship, to: Ship, relativeAngle: Float) {
      val sumedSize = (who.config.size + to.config.size) * 2
      who.movementTarget = to.position + Vec2.rotated(to.angle + relativeAngle) * sumedSize
      who.movementTargetAngle = to.angle
    }
    val iter = ships.sortBySize().iterator()
    val head = iter.next()
    head.movementTarget = destination
    head.movementTargetAngle = destinationAngle
    var prevLeftWing = head
    var prevRightWing = head
    var side = false // false = left, true = right side
    while (iter.hasNext()) {
      val angle = FastMath.pi / 2f + .5f
      val next = iter.next()
      when (side) {
        true -> {
          putRelatively(next, prevLeftWing, angle)
          prevLeftWing = next
        }
        false -> {
          putRelatively(next, prevRightWing, -angle)
          prevRightWing = next
        }
      }
      side = !side
    }
   */

}