package game

import java.util.*


class PlayerContext {

  val selected: MutableList<Squad> = ArrayList() // todo private
  val highlighted: MutableList<Squad> = ArrayList()  // todo private
  var hovered: Squad? = null  // todo private

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

  fun hasSelection() = !selected.isEmpty()

}