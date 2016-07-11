package rendering

import commons.math.FastMath
import commons.math.Vec2
import game.Ship


object ShipDebugRenderer {

  val secondAlpha = .5f
  val dotPopulation = 8f

  fun Ship.render() {
    renderBody()
    renderThrusterState()
    renderRotationState()
    renderMovementTarget()
    renderAccelerationAngle()
  }

  private fun Ship.renderBody() {
    Draw.dartFilled(position, config.size, angle, alpha = .5f)
    Draw.dart(position, config.size, angle, alpha = 1f)
  }

  private fun Ship.renderThrusterState() {
    val scale = config.size * 2 + 64
    val current = velocityAcceleration / config.thrusterSpeedMax * scale
    val expected = velocityTarget / config.thrusterSpeedMax * scale
    val max = scale
    Draw.lineDotted(position, position + Vec2.rotated(angle) * max, dotPopulation, alpha = secondAlpha)
    fun horizon(length: Float, size: Float) {
      val pivot = position + Vec2.rotated(angle) * length
      val horizontal = Vec2.rotated(angle + FastMath.pi / 2) * size
      Draw.line(pivot + horizontal, pivot - horizontal)
    }
    horizon(max, 12f)
    horizon(current, 16f)
    horizon(expected - 2f, 8f)
    horizon(expected + 2f, 8f)
  }

  private fun Ship.renderMovementTarget() {
    Draw.lineDotted(position, movementTarget, dotPopulation, alpha = secondAlpha)
    Draw.dartDotted(movementTarget, config.size, movementTargetAngle, dotPopulation, alpha = secondAlpha)
  }

  private fun Ship.renderRotationState() {
    val angleScale = .25f
    val distanceScale = config.size * 2 + 32
    val amount = config.rotationSpeedMax * angleScale
    Draw.arcDotted(position, distanceScale, angle - amount, angle + amount, dotLength = dotPopulation, alpha = secondAlpha)
    fun pivot(a: Float, thickness: Float) {
      val vec = Vec2.rotated(angle + a * angleScale)
      Draw.line(position + vec * (distanceScale - thickness), position + vec * (distanceScale + thickness))
    }
    pivot(angleAcceleration, 16f)
    pivot(config.rotationSpeedMax, 8f)
    pivot(-config.rotationSpeedMax, 8f)
  }

  private fun Ship.renderAccelerationAngle() {
    val length = config.size * 2
    val start = angle - config.accelerationAngle
    val end = angle + config.accelerationAngle
    Draw.cone(position, length, start, end, quality = 32, alpha = secondAlpha)
  }

}