package game

import commons.math.Vec2
import java.util.*

class Squad(val ships: MutableSet<Ship> = HashSet()) {

  fun center() = Vec2.Calculations.average(ships.map { it.position })

  companion object {

    fun singleton(ship: Ship) = Squad(mutableSetOf(ship))

  }

}