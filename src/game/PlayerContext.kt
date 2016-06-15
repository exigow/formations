package game

import java.util.*


class PlayerContext {

  val selected: MutableList<Squad> = ArrayList()
  val highlighted: MutableList<Squad> = ArrayList()

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

}