package game

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.MathUtils.random
import commons.math.FastMath
import commons.math.Vec2


class Ship(val config: UnitConfiguration) {

  var position = Vec2.zero();
  var angle = 0f;
  var angleAcceleration = 0f;
  var movementTarget = Vec2.zero();

  fun update(delta: Float) {
    val angleDiff = calculateAngleDifferenceToTarget();

    angleAcceleration -= angleDiff * config.rotationSpeedAcceleration * delta
    angleAcceleration = lockAngle(angleAcceleration, config.rotationSpeedMax)
    val damping = FastMath.pow(config.rotationSpeedDumping, delta)
    angleAcceleration *= damping
    angle += angleAcceleration * delta

    if (canAccelerateForward()) {
      println("yes")
      position += Vec2.rotated(angle)
    } else
      println("no")
  }

  private fun lockAngle(a: Float, max: Float) = Math.max(Math.min(a, max), -max)

  private fun canAccelerateForward(): Boolean {
    val angdiff = calculateAngleDifferenceToTarget()
    if (Math.abs(angdiff) < config.accelerationAngle)
      return true
    return false
  }

  private fun calculateAngleDifferenceToTarget() = FastMath.angleDifference(angle, position.directionTo(movementTarget))

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