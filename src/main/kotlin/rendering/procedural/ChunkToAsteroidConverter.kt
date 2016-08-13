package rendering.procedural

import assets.AssetsManager
import commons.math.Random
import commons.math.Vec2
import core.Camera
import rendering.Sprite


object ChunkToAsteroidConverter {

  fun Collection<Chunk>.toAsteroids(camera: Camera, time: Float): Collection<Sprite> =
     filter { it.value > .075f }
    .filter { it.value > camera.renderingScale() * .0125 }
    .map { it.translate(Vec2.one() * -16).scale(128f) }
    .map { it.toAsteroidSprite(time) }
    .flatten()

  private fun Chunk.toAsteroidSprite(time: Float): Collection<Sprite> {
    val seed = toSeed()

    val materialName = Random.chooseRandomly(seed, "asteroid-rock-a", "asteroid-rock-b", "asteroid-rock-c" ,"asteroid-rock-d")
    val material = AssetsManager.peekMaterial(materialName)

    val sizeVariation = Random.randomFloatRange(seed, .75f, 1.25f)
    val s = value * sizeVariation

    val positionVariation = Vec2.random(seed) * 64
    val p = position + positionVariation

    val angleVariation = Random.randomPiToMinusPi(seed)
    val startingAngleVariation = Random.randomPiToMinusPi(seed + 1)
    val a = startingAngleVariation + (angleVariation * time) * .025f

    val depth = Random.randomFloatRange(seed, -1f, 1f) * .125f
    val asteroid = Sprite(material, p, s * 4f, a, depth)
    if (value > .125f) {
      val cloud = Sprite(AssetsManager.peekMaterial("asteroid-rock-dust"), p, s * 8f, startingAngleVariation, depth + .075f)
      return listOf(cloud, asteroid)
    }
    return listOf(asteroid)
  }

}