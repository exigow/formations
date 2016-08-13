package rendering.trails

import commons.math.Vec2

internal object TrailIterator {

  fun iterate(list: List<Trail.Structure>, onStructure: (struct: Trail.Structure, hVec: Vec2) -> Unit) = when(list.size) {
    1 -> onStructure.invoke(list.first(), Vec2.one())
    2 -> {
      val start = list.first()
      val end = list.last()
      val c = calcHorizon(start.position, end.position)
      onStructure.invoke(start, c)
      onStructure.invoke(end, c)
    }
    else -> {
      val startingVec = calcHorizon(list.first().position, list[1].position)
      onStructure.invoke(list.first(), startingVec)
      for (i in 1..(list.size - 2)) {
        val prev = list[i - 1]
        val current = list[i]
        val next = list[i + 1]
        val a = calcHorizon(prev.position, current.position)
        val b = calcHorizon(current.position, next.position)
        val c = ((a + b) / 2).normalize()
        onStructure.invoke(current, c)
      }
      val endingVec = calcHorizon(list[list.size - 2].position, list.last().position)
      onStructure.invoke(list.last(), endingVec)
    }
  }

  private fun calcHorizon(from: Vec2, to: Vec2) = (from - to).normalize().rotate90Left()

}