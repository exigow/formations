package game

import com.badlogic.gdx.math.MathUtils.random
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import java.util.*

class World {

  val squads: MutableList<Squad> = ArrayList();

  companion object {

    fun randomWorld(): World {
      val world = World();
      repeat(random(5, 7), {
        fun randomize(max: Float) = random(-max, max).toFloat()
        fun randomizeVec(max: Float) = Vector2(randomize(max), randomize(max))
        val pivotPosition = randomizeVec(512f)
        val squad = Squad()
        repeat(random(7, 13), {
          val ship = Ship.randomShip()
          ship.position.set(pivotPosition).add(randomizeVec(96f))
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

  fun findClosestShip(checkingPoint: Vector2): Ship {
    val ships = findAllShips()
    val firstShip = ships.iterator().next();
    var result = firstShip
    fun distanceTo(s: Ship) = s.position.dst(checkingPoint)
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

  fun findShipsInside(rectangle: Rectangle): List<Ship> {
    fun isInside(ship: Ship) = rectangle.contains(ship.position)
    return findAllShips().filter { isInside(it) }
  }

}