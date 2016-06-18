package game

import game.orders.Order
import java.util.*

class Collective(

  val squads: MutableList<Squad>,
  val orders: MutableList<Order> = ArrayList()

) {

  companion object {

    fun singleton(squad: Squad) = Collective(mutableListOf(squad))

  }

}
