package rendering.procedural

import Vec2


data class Chunk(val position: Vec2, val value: Float) {

  fun scale(scalar: Float) = copy(position * scalar)

  fun translate(translation: Vec2) = copy(position + translation)

  fun toSeed() = hashCode() // not sure if it works, lol

}