package game

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.MathUtils.random
import com.badlogic.gdx.math.Vector2


class Ship {

  val position: Vector2 = Vector2();
  var angle: Float = 0f;

  companion object {

    fun randomShip(): Ship {
      val s = Ship()
      s.angle = random(0f, MathUtils.PI2)
      return s;
    }

  }

  override fun toString(): String{
    val hexHash = Integer.toHexString(hashCode())
    return "ship ($hexHash) (position=$position, angle=$angle)"
  }

}