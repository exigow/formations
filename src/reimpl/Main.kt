import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import game.Ship
import game.Squad
import game.World

class Main {

  val world = createTestWorld()

  init {
    for (squad in world.squads) {
      for (unit in squad.ships) {
        println("" + unit + " -> " + unit.position)
      }
    }
  }

  fun onRender() {
    clearBackground()
  }

  fun clearBackground() {
    Gdx.gl.glClearColor(.25f, .25f, .25f, 1f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
  }


  private fun createTestWorld(): World {
    val world = World();
    repeat(4, {
      fun randomize(max: Float) = MathUtils.random(-max, max).toFloat()
      fun randomizeVec(max: Float) = Vector2(randomize(max), randomize(max))
      val pivotPosition = randomizeVec(256f)
      val squad = Squad()
      repeat(3, {
        val ship = Ship()
        ship.position.set(pivotPosition).add(randomizeVec(64f))
        squad.ships.add(ship)
      })
      world.squads.add(squad)
    })
    return world;
  }


}