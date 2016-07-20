package game

import commons.math.Vec2
import core.formations.Former
import game.orders.AttackOrder
import game.orders.MoveOrder
import game.orders.Order
import java.util.*

class Collective(

  val squads: MutableList<Squad>,
  val orders: MutableList<Order> = ArrayList()

) {

  fun update() {
    if (orders.isEmpty())
      return
    val order = orders.first()
    if (order is MoveOrder)
      performMoveOrder(order)
    if (order is AttackOrder)
      performAttackOrder(order)
  }

  fun performMoveOrder(order: MoveOrder) {
    squads.forEach {
      Former.form(it.ships, order.where, order.direction)
    }
  }

  // todo private fun groupByDistance(input: List<Squad>, effectiveDistance: Float): List<List<Squad>> { }
  // its like "join by colliding circles", when center of a circle is squad center

  fun performAttackOrder(order: AttackOrder) {
      println("piff paff")
  }

  fun center() = Vec2.Calculations.average(squads.flatMap { it.ships }.map { it.position })

  companion object {

    fun singleton(squad: Squad) = Collective(mutableListOf(squad))

  }

}
