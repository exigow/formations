package assets.templates

import assets.templates.TemplateValidator.ensureNotEmpty
import assets.templates.TemplateValidator.ensurePositive
import assets.templates.TemplateValidator.ensureRange
import assets.templates.TemplateValidator.ensureThat
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import FastMath
import Vec2
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
  val brakeDistance: Float,
  val engines: List<EngineTemplate>,
  val weapons: List<WeaponTemplate>,
  val thruster: DimensionAcceleratorTemplate,
  val rotation: DimensionAcceleratorTemplate
) {

  init {
    validate()
  }

  fun validate() {
    ensureNotEmpty(id)
    ensureNotEmpty(displayedName)
    ensureThat(accelerationAngle <= FastMath.pi)
    ensurePositive(thruster.acceleration)
    ensurePositive(thruster.max)
    ensureRange(thruster.dumping, 0f..1f)
    ensurePositive(rotation.acceleration)
    ensurePositive(rotation.max)
    ensureRange(rotation.dumping, 0f..1f)
  }

  companion object {

    val values = jacksonObjectMapper().readValue(File("data/ships.json"), Array<ShipTemplate>::class.java)

    fun select(id: String) = values.filter { it.id == id }.first()

  }

  data class EngineTemplate(val relativePosition: Vec2, val trailWidth: Float)

  data class DimensionAcceleratorTemplate(val acceleration: Float, val max: Float, val dumping: Float)

  data class WeaponTemplate(val relativePosition: Vec2)

}
