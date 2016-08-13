package rendering.utils.debug

import commons.math.FastMath
import commons.math.Vec2
import game.Ship
import rendering.utils.Draw


object ShipDebugRenderer {

  val secondAlpha = .5f
  val dotPopulation = 8f

  fun Ship.render(dotScale: Float) {
    renderBody()
    renderThrusterState(dotScale)
    renderRotationState(dotScale)
    renderMovementTarget(dotScale)
    renderAccelerationAngle()
  }

  private fun Ship.renderBody() {
    Draw.dartFilled(position, config.size, angle, alpha = .25f)
    Draw.dart(position, config.size, angle, alpha = 1f)
  }

  private fun Ship.renderThrusterState(dotScale: Float) {
    val scale = config.size * 2 + 64
    val current = velocityAcceleration / config.thruster.max * scale
    val expected = velocityTarget / config.thruster.max * scale
    val max = scale
    Draw.lineDotted(position, position + Vec2.rotated(angle) * max, dotPopulation * dotScale, alpha = secondAlpha)
    fun horizon(length: Float, size: Float) {
      val pivot = position + Vec2.rotated(angle) * length
      val horizontal = Vec2.rotated(angle + FastMath.pi / 2) * size
      Draw.line(pivot + horizontal, pivot - horizontal)
    }
    val space = 2f * dotScale
    horizon(max, 12f)
    horizon(current, 16f)
    horizon(expected - space, 8f)
    horizon(expected + space, 8f)
  }

  private fun Ship.renderMovementTarget(dotScale: Float) {
    Draw.line(position, movementTarget, alpha = secondAlpha)
    Draw.dartDotted(movementTarget, config.size, movementTargetAngle, dotPopulation * dotScale, alpha = secondAlpha)
  }

  private fun Ship.renderRotationState(dotScale: Float) {
    val angleScale = config.accelerationAngle
    val distanceScale = config.size * 2 + 32
    val amount = angleScale
    Draw.arcDotted(position, distanceScale, angle - amount, angle + amount, dotLength = dotPopulation * dotScale, alpha = secondAlpha, quality = 32)
    fun pivot(a: Float, thickness: Float) {
      val vec = Vec2.rotated(angle + a * angleScale)
      Draw.line(position + vec * (distanceScale - thickness), position + vec * (distanceScale + thickness))
    }
    pivot(1f, 8f);
    pivot(-1f, 8f);
    val acc = angleAcceleration / config.accelerationAngle
    pivot(acc, 16f)
    val target = angleTarget / config.accelerationAngle
    val space = .0175f
    pivot(target - space, 12f)
    pivot(target + space, 12f)
  }

  private fun Ship.renderAccelerationAngle() {
    val length = config.size * 2
    val start = angle - config.accelerationAngle
    val end = angle + config.accelerationAngle
    Draw.cone(position, length, start, end, quality = 32, alpha = secondAlpha)
  }

}