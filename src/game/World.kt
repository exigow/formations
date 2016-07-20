package game

import com.badlogic.gdx.math.Rectangle
import commons.math.Vec2
import game.orders.MoveOrder
import java.util.*

class World {

  val collectives: MutableList<Collective> = ArrayList();

  companion object {

    fun randomWorld(): World {
      val world = World();
      //world.collectives.add(instantiate(UnitConfiguration.fighter(), Vec2(256, 0), 5))
      world.collectives.add(instantiate(UnitConfiguration.fighter(), Vec2(-256, 0), 5))
      //world.collectives.add(instantiate(UnitConfiguration.fighter(), Vec2(0, -256), 7))
      //world.collectives.add(instantiate(UnitConfiguration.carrier(), Vec2(0, 256)))
      return world
    }

    private fun instantiate(config: UnitConfiguration, where: Vec2, count: Int = 1): Collective {
      val squad = Squad()
      for (i in 1..count) {
        val ship = Ship(config)
        ship.position = where + Vec2.random() * 256
        ship.movementTarget = ship.position + Vec2.random().normalize() * 128
        ship.movementTargetAngle = ship.position.directionTo(ship.movementTarget)
        squad.ships += ship
      }
      val col = Collective.singleton(squad)
      col.orders.add(MoveOrder(squad.center(), 0f))
      return col
    }

  }

  fun findClosestShip(checkingPoint: Vec2): Ship {
    val positions = allShips().map { it.position }
    val closest = Vec2.Calculations.closest(positions, checkingPoint)
    return allShips().find { it.position == closest }!!
  }

  fun findClosestShipInMaxRadius(checkingPoint: Vec2, radius: Float): Ship? {
    val ship = findClosestShip(checkingPoint)
    if (ship.position.distanceTo(checkingPoint) < radius)
      return ship
    return null
  }

  fun findSquadOf(ship: Ship) = allSquads().find { it.ships.contains(ship) }!!

  fun findSquadsInside(rectangle: Rectangle) = collectives.flatMap { it.squads }
    .filter { it.ships.any { rectangle.contains(it.position.toVector2()) } }
    .distinct()

  fun allSquads() = collectives.flatMap { it.squads }

  fun allShips() = allSquads().flatMap { it.ships }

  fun joinToNewCollective(squads: List<Squad>): Collective {
    val result = ArrayList<Squad>()
    for (squad in squads) {
      val modified = findCollectiveOf(squad)
      modified.squads.remove(squad)
      if (modified.squads.isEmpty())
        collectives.remove(modified)
      result.add(squad)
    }
    val new = Collective(result)
    collectives.add(new)
    return new
  }

  private fun findCollectiveOf(squad: Squad) = collectives.find { it.squads.contains(squad) }!!

}