package core.formations

import commons.math.Vec2
import game.Ship


object Former {

  fun form(destination: Vec2, ships: List<Ship>) {
    fun Ship.byDistance() = position.distanceTo(destination)
    val head: Ship = ships.minBy { it.byDistance() }!!
    val others = ships - head
    var prev = head
    for (other in others.sortedBy { it.byDistance() }) {
      other.movementTarget = prev.position
      prev = other
    }
    head.movementTarget = destination
  }

}