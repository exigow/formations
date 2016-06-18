package game

import commons.math.Vec2
import game.orders.Order
import java.util.*

class Collective(

  val squads: MutableList<Squad>,
  val orders: MutableList<Order> = ArrayList()

) {

  fun center() = Vec2.Calculations.average(squads.flatMap { it.ships }.map { it.position })

  companion object {

    fun singleton(squad: Squad) = Collective(mutableListOf(squad))

  }

}
