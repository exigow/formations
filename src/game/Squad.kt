package game

import commons.math.Vec2
import java.util.*

class Squad(val ships: MutableList<Ship> = ArrayList()) {

  fun center() = Vec2.Calculations.average(ships.map { it.position })

  override fun toString() = "Squad(ships=$ships)"

}