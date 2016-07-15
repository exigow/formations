package game

import commons.math.Vec2
import java.util.*

class Squad(val ships: MutableList<Ship> = ArrayList()) {

  fun center() = Vec2.Calculations.average(ships.map { it.position })

  companion object {

    fun singleton(ship: Ship) = Squad(mutableListOf(ship))

  }

  fun leader() = ships.first()

  fun others() = ships.drop(1);

  override fun toString() = "Squad(ships=$ships)"

}