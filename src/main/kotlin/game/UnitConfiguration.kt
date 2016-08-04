package game

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import commons.math.FastMath
import java.io.File

/**
 * @param displayedName The name of the ship, displayed when you select the unit.
 *
 * @param accelerationAngle The angle (in radians) of the forward cone of the ship that determines when a ship starts
 * accelerating towards the goal. For fighter, this is usually a high number, because you want them to speed up
 * immediately, while for a battlecruiser this is usually a low number, because the turning speed of the BC is so slow
 * that you want to be pointing more at the target before it accelerates.
 */
data class UnitConfiguration(
  val id: String,
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

  fun validate() {
    fun ensureThat(condition: Boolean) {
      if (!condition)
        throw RuntimeException("Validation error: ")
    }
    ensureThat(displayedName.isNotEmpty())
    ensureThat(accelerationAngle <= FastMath.pi)
    ensureThat(thrusterSpeedAcceleration >= 0f)
    ensureThat(thrusterSpeedMax >= 0f)
    ensureThat(thrusterSpeedDumping >= 0f && thrusterSpeedDumping <= 1f)
    ensureThat(rotationSpeedAcceleration >= 0f)
    ensureThat(rotationSpeedMax >= 0f)
    ensureThat(rotationSpeedDumping >= 0f && thrusterSpeedDumping <= 1f)
  }

  companion object {

    val configurations = jacksonObjectMapper().readValue(File("data/ships.json"), Array<UnitConfiguration>::class.java)

    fun select(id: String) = configurations.filter { it.id == id }.first()

  }

}
