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

  val hullName: String,

  val size: Float,

  val accelerationAngle: Float,

  val thrusterSpeedAcceleration: Float,
  val thrusterSpeedMax: Float,
  val thrusterSpeedDumping: Float,

  val rotationSpeedAcceleration: Float,
  val rotationSpeedMax: Float,
  val rotationSpeedDumping: Float,

  val brakeDistance: Float,

  val trailDistance: Float // todo multiple vectors

) {

  init {
    validate()
  }

  companion object {

    fun fighter() = UnitConfiguration(
      displayedName = "Fighter",

      hullName = "interceptor",

      size = 16f,

      accelerationAngle = 2.25f,

      thrusterSpeedAcceleration = .875f,
      thrusterSpeedMax = 4f,
      thrusterSpeedDumping = .875f,

      rotationSpeedAcceleration = 1.75f,
      rotationSpeedMax = 2.25f,
      rotationSpeedDumping = .25f,

      brakeDistance = 96f,

      trailDistance = -18f
    )

    fun bomber() = UnitConfiguration(
      displayedName = "Bomber",

      hullName = "bomber",

      size = 24f,

      accelerationAngle = 2.05f,

      thrusterSpeedAcceleration = .75f,
      thrusterSpeedMax = 3.5f,
      thrusterSpeedDumping = .875f,

      rotationSpeedAcceleration = 1.625f,
      rotationSpeedMax = 1.95f,
      rotationSpeedDumping = .275f,

      brakeDistance = 96f,

      trailDistance = -17f
    )

    fun carrier() = UnitConfiguration(
      displayedName = "Carrier",

      hullName = "carrier",

      size = 96f,

      accelerationAngle = .375f,

      thrusterSpeedAcceleration = .125f,
      thrusterSpeedMax = 2f,
      thrusterSpeedDumping = .925f,

      rotationSpeedAcceleration = 0.125f,
      rotationSpeedMax = .375f,
      rotationSpeedDumping = .375f,

      brakeDistance = 512f,

      trailDistance = -138f
    )

  }

  fun validate() {
    fun ensure(condition: Boolean) {
      if (!condition)
        throw RuntimeException("Validation error: ")
    }
    ensure(!displayedName.isEmpty())

    ensure(accelerationAngle <= FastMath.pi)

    ensure(thrusterSpeedAcceleration >= 0f)
    ensure(thrusterSpeedMax >= 0f)
    ensure(thrusterSpeedDumping >= 0f && thrusterSpeedDumping <= 1f)

    ensure(rotationSpeedAcceleration >= 0f)
    ensure(rotationSpeedMax >= 0f)
    ensure(rotationSpeedDumping >= 0f && thrusterSpeedDumping <= 1f)
  }

  override fun toString() = displayedName

}

