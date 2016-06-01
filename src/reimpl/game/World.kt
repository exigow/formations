package game

import com.badlogic.gdx.math.MathUtils.random
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

}