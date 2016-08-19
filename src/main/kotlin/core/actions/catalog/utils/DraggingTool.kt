package core.actions.catalog.utils

import commons.Logger
import Vec2

class DraggingTool {

  private var pivot = Vec2.zero()
  private var lock = false

  fun pinTo(where: Vec2) {
    pivot = where
    lock = false
  }

  fun update(where: Vec2, maxDistance: Float) {
    val dragLength = pivot.distanceTo(where)
    if (dragLength > maxDistance && !lock) {
      Logger.ACTION.log("Is dragging.")
      lock = true
    }
  }

  fun reset() {
    lock = false
  }

  fun startingPosition() = pivot

  fun isDragging() = lock

}