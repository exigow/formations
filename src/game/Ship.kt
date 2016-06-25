package game

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
    if (canAccelerateForward()) {
      val angleDamping = FastMath.pow(config.rotationSpeedDumping, delta)
      angleAcceleration *= angleDamping
    }
    angle += angleAcceleration * delta
    println(angleAcceleration)

    val dist = position.distanceTo(movementTarget)
    if (dist > config.size && canAccelerateForward())
      velocityAcceleration += config.thrusterSpeedAcceleration * delta
    val velocityDamping = FastMath.pow(config.thrusterSpeedDumping, delta)
    velocityAcceleration *= velocityDamping
    velocityAcceleration = Math.min(velocityAcceleration, config.thrusterSpeedMax)
    position += Vec2.rotated(angle) * velocityAcceleration
  }

  private fun lockAngle(a: Float, max: Float) = FastMath.clamp(a, -max, max)

  private fun canAccelerateForward() = Math.abs(calculateAngleDifferenceToTarget()) < config.accelerationAngle

  private fun calculateAngleDifferenceToTarget() = FastMath.angleDifference(angle, position.directionTo(movementTarget))

}