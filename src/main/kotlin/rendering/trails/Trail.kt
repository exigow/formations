package rendering.trails

import Vec2
import rendering.renderers.Renderable
import java.util.*


class Trail(initialPosition: Vec2, val width: Float, val emissionRange: Float) : Renderable {

  override fun depth() = 0f

  val list: LinkedList<Structure> = LinkedList()

  init {
    emitInstantly(initialPosition)
    emitInstantly(initialPosition)
  }

  fun isDead() = list.all { it.life <= 0f }

  private fun emitInstantly(where: Vec2) = list.add(Structure(where, width))

  fun emit(target: Vec2, initialLife: Float) {
    val lastButOne = list[list.size - 2]
    if (lastButOne.position.distanceTo(target) < emissionRange) {
      list.last.position = target
      list.last.life = initialLife
    } else
      list.add(Structure(target, width))
  }

  fun update(delta: Float) {
    list.forEach {
      if (it.life > 0f)
        it.life -= delta * .25f
    }
    if (list[1].life < 0f && list.size > 2)
      list.pop()
  }

  data class Structure (
    var position: Vec2,
    var width: Float,
    var life: Float = 1f
  )

}