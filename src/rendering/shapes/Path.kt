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

  fun populate(density: Float, preserveCorners: Boolean = false): Path {
    val result: MutableList<Vec2> = ArrayList()
    val i = elements.iterator()
    var prev = i.next()
    var left = 0f
    while (i.hasNext()) {
      val next = i.next()
      val distance = prev.distanceTo(next)
      val direction = prev.directionTo(next)
      val directionVec = Vec2.rotated(direction)
      var passed = left
      while (passed < distance) {
        val pivot = prev + directionVec * passed
        result += pivot
        passed += density
        left += density
        if (preserveCorners)
          result += next
      }
      left -= distance
      prev = next
    }
    result += prev
    return Path(result)
  }

  fun cut(preserve: Int, remove: Int): List<Path> {
    val paths: MutableList<Path> = ArrayList()

    val i = elements.iterator()

    val aggregatedToAdd: MutableList<Vec2> = ArrayList()

    var counter = 0
    var preserving = true
    fun swap() {
      preserving = !preserving
      counter = 0
    }

    while (i.hasNext()) {
      val next = i.next()

      if (preserving)
        if (counter < preserve) {
          aggregatedToAdd += next
          counter++
        } else {
          val cpy = ArrayList<Vec2>()
          cpy.addAll(aggregatedToAdd)
          paths += Path(cpy)
          aggregatedToAdd.clear()
          swap()
        }
      else
        if (counter < remove)
          counter++
        else
          swap()
    }
    return paths
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

  fun scale(scalar: Vec2) = Path(elements.map { it * scalar })
  override fun toString(): String{
    return "Path(elements=$elements)"
  }

  companion object {

    fun asListOf(vararg elements: Vec2) = listOf(Path(elements.toList()))

  }

}