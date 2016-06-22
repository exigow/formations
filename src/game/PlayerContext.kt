package game

import com.badlogic.gdx.math.Rectangle
import java.util.*


class PlayerContext {

  val selected: MutableList<Squad> = ArrayList()
  val highlighted: MutableList<Squad> = ArrayList()
  var hovered: Squad? = null
  var selectionRect: Rectangle? = null

  fun migrateHighlightToSelection() {
    selected.clear()
    selected.addAll(highlighted)
    highlighted.clear()
  }

  fun clearSelection() = selected.clear()

  fun replaceHighlightedWith(squads: List<Squad>) {
    highlighted.clear()
    highlighted.addAll(squads)
  }

  fun updateHover(squad: Squad?) {
    hovered = squad
  }

  fun isHovering() = hovered != null

  fun hasSelection() = !selected.isEmpty()

}