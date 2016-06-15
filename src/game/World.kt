package game

import com.badlogic.gdx.math.MathUtils.random
import com.badlogic.gdx.math.Rectangle
import commons.math.Vec2
import java.util.*

class World {

  val squads: MutableList<Squad> = ArrayList();

  companion object {

    fun randomWorld(): World {
      val world = World();
      repeat(random(5, 7), {
        val pivotPosition = Vec2.random() * 512f
        val squad = Squad()
        repeat(random(3, 7), {
          val ship = Ship.randomShip()
          ship.position = pivotPosition + (Vec2.random() * 96f)
          squad.ships.add(ship)
        })
        world.squads.add(squad)
      })
      return world;
    }

  }

  fun findAllShips(): List<Ship> {
    val result = ArrayList<Ship>()
    for (squad: Squad in squads)
      for (ship: Ship in squad.ships)
        result.add(ship)
    return result
  }

  fun findClosestShip(checkingPoint: Vec2): Ship {
    val ships = findAllShips()
    val firstShip = ships.iterator().next();
    var result = firstShip
    fun distanceTo(s: Ship) = s.position.distanceTo(checkingPoint)
    var distance = distanceTo(firstShip)
    for (ship: Ship in ships) {
      val newDistance = distanceTo(ship)
      if (newDistance < distance) {
        distance = newDistance
        result = ship
      }
    }
    return result
  }

  fun findClosestShipInMaxRadius(checkingPoint: Vec2, radius: Float): Ship? {
    val ship = findClosestShip(checkingPoint);
    if (ship.position.distanceTo(checkingPoint) < radius)
      return ship
    return null
  }

  fun findShipsInside(rectangle: Rectangle): List<Ship> {
    fun isInside(ship: Ship) = rectangle.contains(ship.position.toVector2())
    return findAllShips().filter { isInside(it) }
  }

  fun findSquad(ship: Ship): Squad {
    for (squad: Squad in squads)
      if (squad.ships.contains(ship))
        return squad
    throw RuntimeException()
  }

  fun findSquadsInside(rectangle: Rectangle): List<Squad> {
    return squads.filter { isInside(it, rectangle) }.distinct()
  }

  private fun isInside(squad: Squad, rectangle: Rectangle): Boolean {
    for (ship in squad.ships)
      if (hasShipInside(rectangle, ship))
        return true
    return false
  }


  private fun hasShipInside(rectangle: Rectangle, ship: Ship): Boolean {
    return rectangle.contains(ship.position.toVector2())
  }

}