package rendering.procedural

import assets.AssetsManager
import Random
import Vec2
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

    val materialName = Random.chooseRandomly(seed, "asteroid-red-a", "asteroid-red-b", "asteroid-red-c" ,"asteroid-red-d")

    val sizeVariation = Random.randomFloatRange(seed, .75f, 1.25f)
    val s = value * sizeVariation

    val positionVariation = Vec2.random(seed) * 64
    val p = position + positionVariation

    val angleVariation = Random.randomPiToMinusPi(seed)
    val startingAngleVariation = Random.randomPiToMinusPi(seed + 1)
    val a = startingAngleVariation + (angleVariation * time) * .025f

    val depth = Random.randomFloatRange(seed, -64f, 64f)
    val asteroid = Sprite(materialName, p, Vec2.scaled(s) * 4f, a, depth)
    if (value > .125f) {
      val cloud = Sprite("asteroid-rock-dust", p, Vec2.scaled(s) * 8f, startingAngleVariation, depth - .075f)
      return listOf(cloud, asteroid)
    }
    return listOf(asteroid)
  }

}