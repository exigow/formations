package rendering.utils

import FastMath
import Vec2
import assets.AssetsManager
import rendering.Sprite
import rendering.renderers.Renderable

object JetExhaustRenderer {

  fun render(where: Vec2, angle: Float, size: Float, strength: Float): Collection<Renderable> {
    val a = angle + FastMath.pi
    val staticStrength = 1f - FastMath.pow(1f - strength, 2f)
    val staticStrength2 = 1f - FastMath.pow(1f - strength, 4f)
    val aura = Sprite(AssetsManager.peekMaterial("jet-aura"), where, Vec2(6f, 4f) * size * variation() * staticStrength, a, 0f)
    val core = Sprite(AssetsManager.peekMaterial("jet-core"), where, Vec2(3f, .75f) * size * variation() * staticStrength2, a, 0f)
    val rings = (1..4)
      .map {
        it ->
        val s = 1f - FastMath.pow(1f - 1f / it, 1f)
        val t = where + Vec2.rotated(a) * size * 96f
        val pos = Vec2.Calculations.lerp(where, t, 1f / 4f * it * 0.875f * strength)
        Sprite(AssetsManager.peekMaterial("jet-ring"), pos + Vec2.rotated(angle) * size * variation() * 4f, Vec2.one() * s * Vec2(s * 2f, 1.5f) * variation() * 1.5f * size * staticStrength, a, 0f)
      }
    val m = 1f - FastMath.pow(1f - strength, 8f)
    val fire = Sprite(AssetsManager.peekMaterial("jet-fire"), where, Vec2(.5f, 4f) * size * m * variation(), a, 0f)
    return listOf(fire) + aura + core + rings
  }

  private fun variation(): Vec2 = Vec2.one() + Vec2.random() * .125f

}