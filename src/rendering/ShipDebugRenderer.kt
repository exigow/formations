package rendering

import commons.math.FastMath
import commons.math.Vec2
import game.Ship


object ShipDebugRenderer {

  fun Ship.render() {
    renderBody()
    //renderThrusterState()
    //renderRotationState()
    //renderMovementTarget()
    //renderAccelerationAngle()
  }

  private fun Ship.renderBody() {
    Draw.dartFilled(position, config.size, angle, alpha = .125f)
    Draw.dart(position, config.size, angle, alpha = 1f)
  }

  private fun Ship.renderThrusterState() {
    val scale = 64f
    val max = config.thrusterSpeedMax * scale
    val current = velocityAcceleration * scale
    val expected = velocityTarget * scale
    Draw.lineDotted(position, position + Vec2.rotated(angle) * max, 8f)
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
    Draw.lineDotted(position, movementTarget, 16f)
    Draw.dartDotted(movementTarget, config.size, movementTargetAngle, 8f, alpha = .5f)
  }

  private fun Ship.renderRotationState() {
    val angleScale = .25f
    val distanceScale = 128f
    val amount = config.rotationSpeedMax * angleScale
    Draw.arc(position, distanceScale, angle - amount, angle + amount)
    fun pivot(a: Float, thickness: Float) {
      val vec = Vec2.rotated(angle + a * angleScale)
      Draw.line(position + vec * (distanceScale - thickness), position + vec * (distanceScale + thickness))
    }
    pivot(angleAcceleration, 16f)
    pivot(config.rotationSpeedMax, 8f)
    pivot(-config.rotationSpeedMax, 8f)
  }

  private fun Ship.renderAccelerationAngle() {
    val length = 96f
    val start = angle - config.accelerationAngle
    val end = angle + config.accelerationAngle
    Draw.cone(position, length, start, end, quality = 32)
  }

}