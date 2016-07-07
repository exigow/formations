package game

import commons.math.FastMath
import commons.math.Vec2


class Ship(val config: UnitConfiguration) {

  var position = Vec2.zero();
  var angle = 0f;
  var angleAcceleration = 0f;
  var velocityAcceleration = 0f;
  var velocityTarget = 0f
  var movementTarget = Vec2.zero()
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

    velocityTarget = calculateTargetAcceleration()
    velocityAcceleration += (velocityTarget - velocityAcceleration) * config.thrusterSpeedAcceleration * delta
    if (!canAccelerateForward()) {
      val velocityDamping = FastMath.pow(config.thrusterSpeedDumping, delta)
      velocityAcceleration *= velocityDamping
    }

    position += Vec2.rotated(angle) * velocityAcceleration

    angle = FastMath.loopAngle(angle)
  }

  private fun calculateTargetAcceleration(): Float {
    val dist = position.distanceTo(movementTarget)
    val brake = config.brakeDistance // todo trzeba to uzaleznic od aktualnej predkosci, bo rozpedzony krążownik za bardzo zapierdala i omija cel
    if (!canAccelerateForward())
      return 0f
    if (dist > brake)
      return config.thrusterSpeedMax
    val normDist = 1f - (brake - dist) / brake
    val curved = FastMath.pow(normDist, 2f)
    return config.thrusterSpeedMax * curved
  }

  private fun lockAngle(a: Float, max: Float) = FastMath.clamp(a, -max, max)

  private fun canAccelerateForward() = Math.abs(calculateAngleDifferenceToTarget()) < config.accelerationAngle

  private fun calculateAngleDifferenceToTarget() = FastMath.angleDifference(angle, position.directionTo(movementTarget))

}