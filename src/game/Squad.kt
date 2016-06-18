package game

import commons.math.Vec2
import game.orders.Order
import java.util.*

class Squad {

  val ships: MutableSet<Ship> = HashSet()
  val orders: MutableList<Order> = ArrayList()

  fun center() = Vec2.Calculations.average(ships.map { it.position })

}