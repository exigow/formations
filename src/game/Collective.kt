package game

import commons.math.FastMath
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
    /*val set = squads.flatMap { it.ships }.toSet() // todo group
    */
    squads.forEach {

      Former.form(order.where, it.ships)

    }
    //squads.forEach { applyArrowMovementToDestination(it.ships, order.where, order.direction) }
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

  private fun applyArrowMovementToDestination(ships: List<Ship>, destination: Vec2, destinationAngle: Float) {
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
  }

  private fun List<Ship>.sortBySize(): List<Ship> = this.sortedBy { it.config.size }.reversed()

}
