package game

import assets.AssetsManager
import assets.templates.ShipTemplate
import com.badlogic.gdx.math.Matrix4
import commons.math.FastMath
import commons.math.Vec2
import rendering.materials.MaterialRenderer
import rendering.trails.Trail
import rendering.trails.TrailsRenderer


class Ship(val config: ShipTemplate, initialPosition: Vec2) {

  var position = initialPosition;
  var angle = 0f;
  var angleAcceleration = 0f;
  var angleTarget = 0f;
  var velocityAcceleration = 0f;
  var velocityTarget = 0f
  var movementTarget = Vec2.zero()
  var movementTargetAngle = 0f
  val engines = config.engines.map { Engine(it, this) }.toList()

  fun update(delta: Float) {
    val fixAmount = 1f - FastMath.pow(1f - calcNormalizedDistanceToTarget(16f), 2f);
    val angleDiff = calculateAngleDifferenceToTarget()

    angleAcceleration += angleDiff * config.rotation.acceleration * delta * fixAmount
    angleAcceleration = lockAngle(angleAcceleration, config.rotation.max)
    if (canAccelerateForward()) {
      val angleDamping = FastMath.pow(config.rotation.dumping, delta)
      angleAcceleration *= angleDamping
    }
    angle += angleAcceleration * delta

    velocityTarget = calculateTargetAcceleration()
    velocityAcceleration += (velocityTarget - velocityAcceleration) * config.thruster.acceleration * delta
    if (!canAccelerateForward()) {
      val velocityDamping = FastMath.pow(config.thruster.dumping, delta)
      velocityAcceleration *= velocityDamping
    }

    val fixVec = (movementTarget - position) * .025f
    position += Vec2.Calculations.lerp(Vec2.rotated(angle) * velocityAcceleration, fixVec, 1f - fixAmount);

    val fixAngle = FastMath.angleDifference(angle, movementTargetAngle) * (1f - fixAmount)
    angle -= fixAngle * .025f
    angleAcceleration *= fixAmount

    angle = FastMath.loopAngle(angle)

    engines.forEach {
      val life = Math.min(velocityAcceleration * 8 + .025f, 1f)
      it.trail.emit(it.absolutePosition(), life)
      it.trail.update(delta)
    }
  }

  fun render(materialRenderer: MaterialRenderer, trailsRenderer: TrailsRenderer, matrix4: Matrix4) {
    engines.forEach {
      trailsRenderer.render(it.trail, AssetsManager.peekMaterial("trail"), matrix4)
    }
    materialRenderer.draw(AssetsManager.peekMaterial(config.hullName), position, angle, 1f, matrix4)
  }

  private fun calculateTargetAcceleration(): Float {
    val brake = config.brakeDistance * velocityAcceleration
    if (!canAccelerateForward())
      return 0f
    val dist = calcDistanceToTarget();
    if (dist > brake)
      return config.thruster.max
    val normDist = 1f - (brake - dist) / brake
    val curved = FastMath.pow(normDist, 8f)
    return config.thruster.max * curved
  }

  private fun lockAngle(a: Float, max: Float) = FastMath.clamp(a, -max, max)

  private fun canAccelerateForward() = Math.abs(calculateAngleDifferenceToTarget()) < config.accelerationAngle

  private fun calculateAngleDifferenceToTarget() = -FastMath.angleDifference(angle, position.directionTo(movementTarget))

  private fun calcDistanceToTarget() = position.distanceTo(movementTarget)

  private fun calcNormalizedDistanceToTarget(clampTo: Float) = Math.min(position.distanceTo(movementTarget) / clampTo, 1f)

  override fun toString() = "Ship[${hashCode()}](config=$config)"

  class Engine(private val template: ShipTemplate.EngineTemplate, private val ship: Ship) {

    val trail = Trail(absolutePosition(), template.trailWidth, 16f)

    fun absolutePosition() = ship.position + template.relativePosition.rotate(ship.angle)

  }

}