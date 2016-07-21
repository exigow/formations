package rendering.trails

import commons.Logger
import commons.math.Vec2
import java.util.*


class TrailsBuffer {

  val trails = ArrayList<Trail>()

  fun update(delta: Float) {
    trails.forEach { it.update(delta) }
    trails.removeAll{ it.isDead() }
  }

  class Trail(initialPosition: Vec2) {

    val list: LinkedList<Structure> = LinkedList()

    init {
      emitInstantly(initialPosition)
      emitInstantly(initialPosition)
    }

    fun isDead() = list.all { it.life <= 0f }

    private fun emitInstantly(where: Vec2) = list.add(Structure(where))

    fun emit(target: Vec2, maxDistance: Float) {
      val lastButOne = list[list.size - 2]
      if (lastButOne.position.distanceTo(target) < maxDistance) {
        list.last.position = target
        list.last.life = 1f
      } else
        list.add(Structure(target))
    }

    fun update(delta: Float) {
      list.forEach {
        if (it.life > 0f)
          it.life -= delta * .25f
      }
      if (list.first.life < 0f && list.size > 2)
        list.pop()
    }

  }

  fun registerTrail(initialPosition: Vec2): Trail {
    val trail = Trail(initialPosition)
    trails.add(trail)
    Logger.TRAILS.log("Registering new trail on $initialPosition, global size is now ${trails.size}")
    return trail;
  }

  data class Structure (
    var position: Vec2,
    var life: Float = 1f
  )

}