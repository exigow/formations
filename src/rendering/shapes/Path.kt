package rendering.shapes

import commons.math.Vec2
import java.util.*

class Path(val elements: List<Vec2>) {

  fun subdivide(): Path {
    val result: MutableList<Vec2> = ArrayList()
    val i = elements.iterator()
    val first = i.next()
    var prev = first
    fun center(toWho: Vec2) = (prev + toWho) / 2f
    while (i.hasNext()) {
      val next = i.next()
      result += prev
      result += center(next)
      prev = next
    }
    result += center(first)
    return Path(result)
  }

  fun populate(density: Float): Path {
    val result: MutableList<Vec2> = ArrayList()

    val i = elements.iterator()
    var prev = i.next()

    while (i.hasNext()) {
      val next = i.next()

      val distance = prev.distanceTo(next)
      val direction = prev.directionTo(next)
      val directionVec = Vec2.rotated(direction)

      var passed = 0f

      while (passed < distance) {
        val pivot = prev + directionVec * passed
        result += pivot
        passed += density
      }

      prev = next
    }

    return Path(result)
  }

  fun length(): Float {
    val i = elements.iterator()
    var prev = i.next()
    var result = 0f
    while (i.hasNext()) {
      val next = i.next()
      result += prev.distanceTo(next)
      prev = next
    }
    return result
  }

}