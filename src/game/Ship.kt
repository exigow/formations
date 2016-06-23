package game

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.MathUtils.random
import commons.math.Vec2


class Ship(val config: UnitConfiguration) {

  var position: Vec2 = Vec2.zero();
  var angle: Float = 0f;

  companion object {

    fun randomShip(config: UnitConfiguration): Ship {
      val s = Ship(config)
      s.angle = random(0f, MathUtils.PI2)
      return s;
    }

  }

  override fun toString(): String{
    val hexHash = Integer.toHexString(hashCode())
    return "ship ($hexHash) (position=$position, angle=$angle)"
  }

}