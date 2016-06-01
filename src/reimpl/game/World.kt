package game

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import java.util.*

class World {

  val squads: MutableList<Squad> = ArrayList();

  companion object {

    fun createTestWorld(): World {
      val world = World();
      repeat(MathUtils.random(5, 7), {
        fun randomize(max: Float) = MathUtils.random(-max, max).toFloat()
        fun randomizeVec(max: Float) = Vector2(randomize(max), randomize(max))
        val pivotPosition = randomizeVec(512f)
        val squad = Squad()
        repeat(MathUtils.random(7, 13), {
          val ship = Ship()
          ship.position.set(pivotPosition).add(randomizeVec(96f))
          squad.ships.add(ship)
        })
        world.squads.add(squad)
      })
      return world;
    }

  }

}