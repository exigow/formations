package game

import commons.math.FastMath
import commons.math.Vec2


class Ship(val config: UnitConfiguration) {

  var position = Vec2.zero();
  var angle = 0f;
  var angleAcceleration = 0f;
  var angleTarget = 0f;
  var velocityAcceleration = 0f;
  var velocityTarget = 0f
  var movementTarget = Vec2.zero()
  var movementTargetAngle = 0f
  var formationVelocityFixMultiplier = 1f

  fun update(delta: Float) {
    val fixAmount = 1f - FastMath.pow(1f - calcNormalizedDistanceToTarget(16f), 2f);
    val angleDiff = calculateAngleDifferenceToTarget()

    angleAcceleration += angleDiff * config.rotationSpeedAcceleration * delta * fixAmount
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

    val fixVec = (movementTarget - position) * .025f
    position += Vec2.Calculations.lerp(Vec2.rotated(angle) * velocityAcceleration, fixVec, 1f - fixAmount);

    val fixAngle = FastMath.angleDifference(angle, movementTargetAngle) * (1f - fixAmount)
    angle -= fixAngle * .025f
    angleAcceleration *= fixAmount

    angle = FastMath.loopAngle(angle)
  }

  private fun calculateTargetAcceleration(): Float {
    val brake = config.brakeDistance * velocityAcceleration
    if (!canAccelerateForward())
      return 0f
    val dist = calcDistanceToTarget();
    if (dist > brake)
      return config.thrusterSpeedMax
    val normDist = 1f - (brake - dist) / brake
    val curved = FastMath.pow(normDist, 8f)
    return config.thrusterSpeedMax * curved
  }

  private fun lockAngle(a: Float, max: Float) = FastMath.clamp(a, -max, max)

  private fun canAccelerateForward() = Math.abs(calculateAngleDifferenceToTarget()) < config.accelerationAngle

  private fun calculateAngleDifferenceToTarget() = -FastMath.angleDifference(angle, position.directionTo(movementTarget))

  private fun calcDistanceToTarget() = position.distanceTo(movementTarget)

  private fun calcNormalizedDistanceToTarget(clampTo: Float) = Math.min(position.distanceTo(movementTarget) / clampTo, 1f)

  override fun toString() = "Ship(config=$config)"

}