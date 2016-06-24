package game

import commons.math.FastMath

/**
 * @param displayedName The name of the ship, displayed when you select the unit.
 *
 * @param accelerationAngle The angle (in radians) of the forward cone of the ship that determines when a ship starts
 * accelerating towards the goal. For fighter, this is usually a high number, because you want them to speed up
 * immediately, while for a battlecruiser this is usually a low number, because the turning speed of the BC is so slow
 * that you want to be pointing more at the target before it accelerates.
 */
data class UnitConfiguration(

  val displayedName: String,

  val accelerationAngle: Float,

  val thrusterSpeedAcceleration: Float,
  val thrusterSpeedMax: Float,
  val thrusterSpeedDamping: Float,

  val rotationSpeedAcceleration: Float,
  val rotationSpeedMax: Float,
  val rotationSpeedDumping: Float

) {

  init {
    validate()
  }

  companion object {

    fun fighterType() = UnitConfiguration(
      displayedName = "Fighter",

      accelerationAngle = 1.25f,

      thrusterSpeedAcceleration = .375f,
      thrusterSpeedMax = 4f,
      thrusterSpeedDamping = .25f,

      rotationSpeedAcceleration = 1.75f,
      rotationSpeedMax = 2.25f,
      rotationSpeedDumping = .25f
    )

  }

  fun validate() {
    fun ensure(condition: Boolean) {
      if (!condition)
        throw RuntimeException("Validation error: ")
    }
    ensure(accelerationAngle <= FastMath.pi)
    ensure(!displayedName.isEmpty())
    ensure(thrusterSpeedAcceleration >= 0f)
    ensure(thrusterSpeedMax >= 0f)
    ensure(rotationSpeedAcceleration >= 0f)
    ensure(rotationSpeedMax >= 0f)
  }

}

