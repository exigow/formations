package game

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.MathUtils.random
import commons.math.FastMath
import commons.math.Vec2


class Ship(val config: UnitConfiguration) {

  var position = Vec2.zero();
  var angle = 0f;
  var angleAcceleration = 0f;
  var velocityAcceleration = 0f
  var movementTarget = Vec2.zero();
  var movementTargetAngle = 0f

  fun update(delta: Float) {
    val angleDiff = calculateAngleDifferenceToTarget();

    angleAcceleration -= angleDiff * config.rotationSpeedAcceleration * delta
    angleAcceleration = lockAngle(angleAcceleration, config.rotationSpeedMax)
    val angleDamping = FastMath.pow(config.rotationSpeedDumping, delta)
    angleAcceleration *= angleDamping
    angle += angleAcceleration * delta

    val dist = position.distanceTo(movementTarget)
    if (dist > config.goalReachDistance && canAccelerateForward())
      velocityAcceleration += 1 * delta
    val velocityDamping = FastMath.pow(config.thrusterSpeedDumping, delta)
    velocityAcceleration *= velocityDamping
    velocityAcceleration = Math.min(velocityAcceleration, config.thrusterSpeedMax)
    position += Vec2.rotated(angle) * velocityAcceleration
  }

  private fun lockAngle(a: Float, max: Float) = FastMath.clamp(a, -max, max)

  private fun canAccelerateForward() = Math.abs(calculateAngleDifferenceToTarget()) < config.accelerationAngle

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