package commons

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2

object MathUtilities {

  fun randomVector2() = Vector2(randomSymmetry(), randomSymmetry())

  fun randomSymmetry() = MathUtils.random(-1f, 1f)

}