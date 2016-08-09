package commons.math

import org.apache.commons.math3.random.MersenneTwister


object Random {

  fun <T> chooseRandomly(seed: Int, vararg options: T): T {
    val chosen = Math.abs(random(seed)) % options.size
    return options[chosen]
  }

  fun random(seed: Int) = MersenneTwister(seed).nextInt()

  fun randomFloat(seed: Int) = MersenneTwister(seed).nextFloat()

  fun randomFloatNormalized(seed: Int) = (-.5f + MersenneTwister(seed).nextFloat()) * 2f

  fun randomFloatRange(seed: Int, from: Float, to: Float): Float {
    val diff = to - from
    return from + randomFloat(seed) * diff
  }

}