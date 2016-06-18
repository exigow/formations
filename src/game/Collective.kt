package game

import game.orders.Order

class Collective(

  val squads: List<Squad>,
  val orders: List<Order>

) {

  companion object {

    fun singleton(squad: Squad) = Collective(listOf(squad), emptyList())

  }

}
