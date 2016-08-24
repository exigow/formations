package ui

import Vec2
import rendering.paths.Path
import rendering.utils.Draw

class Widget(private val from: Vec2, private val to: Vec2) {

  fun draw() {
    val path = Path.of(from, Vec2(to.x, from.y), to, Vec2(from.x, to.y)).loop().populate(16f).slice()
    Draw.paths(path)
  }

  enum class State {

    RESTING, HOVERED, ACTIVE

  }

}