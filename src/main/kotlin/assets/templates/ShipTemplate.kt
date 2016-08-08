package assets.templates

import assets.templates.TemplateValidator.ensureNotEmpty
import assets.templates.TemplateValidator.ensurePositive
import assets.templates.TemplateValidator.ensureRange
import assets.templates.TemplateValidator.ensureThat
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import commons.math.FastMath
import commons.math.Vec2
import java.io.File

/**
 * @param displayedName The name of the ship, displayed when you select the unit.
 *
 * @param accelerationAngle The angle (in radians) of the forward cone of the ship that determines when a ship starts
 * accelerating towards the goal. For fighter, this is usually a high number, because you want them to speed up
 * immediately, while for a battlecruiser this is usually a low number, because the turning speed of the BC is so slow
 * that you want to be pointing more at the target before it accelerates.
 */
data class ShipTemplate(
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
  val trailDistance: Float, // todo multiple vectors
  val engines: List<EngineTemplate>
) {

  init {
    validate()
  }

  fun validate() {
    ensureNotEmpty(id)
    ensureNotEmpty(displayedName)
    ensureThat(accelerationAngle <= FastMath.pi)
    ensurePositive(thrusterSpeedAcceleration)
    ensurePositive(thrusterSpeedMax)
    ensureRange(thrusterSpeedDumping, 0f..1f)
    ensurePositive(rotationSpeedAcceleration)
    ensurePositive(rotationSpeedMax)
    ensureRange(rotationSpeedDumping, 0f..1f)
  }

  companion object {

    val values = jacksonObjectMapper().readValue(File("data/ships.json"), Array<ShipTemplate>::class.java)

    fun select(id: String) = values.filter { it.id == id }.first()

  }

  data class EngineTemplate(val relativePosition: Vec2, val trailWidth: Float)

}
