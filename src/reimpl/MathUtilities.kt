import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2

object MathUtilities {

  fun randomVector2() = Vector2(randomSymmetry(), randomSymmetry())

  fun randomSymmetry() = MathUtils.random(-1f, 1f)

  fun cos(x: Float) = Math.cos(x.toDouble()).toFloat()

  fun sin(x: Float) = Math.sin(x.toDouble()).toFloat()

  fun lerp(from: Float, to: Float, percent: Float): Float {
    return from * (1 - percent) + to * percent;
  }

}