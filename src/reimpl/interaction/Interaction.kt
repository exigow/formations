package interaction

import game.World

object Interaction {

  /*private val selectionTool = SelectionRectangle()
  private val highlightedSquads = ArrayList<Squad>()
  private val selectedSquads = ArrayList<Squad>()
  private var singleSelectionLock = true
  private val pointerRadius = 20f*/

  fun initialise(world: World) {
    /*val key = Input.Mouse.LEFT;
    val pointer = Input.Mouse.position();
    val hoveringSquad = findPointerHoveringSquad(pointer, world);

    key.registerOnPress {
      selectionTool.startFrom(pointer)
    }
    key.registerOnPressedTick {
      selectionTool.endTo(pointer)
      if (selectionTool.distanceFromStartingPoint() > pointerRadius)
        singleSelectionLock = false
      if (singleSelectionLock == false) {
        val squadsInsideSelection = world.findSquadsInside(selectionTool.selectionRectangle())
        highlightedSquads.clear()
        highlightedSquads.addAll(squadsInsideSelection)
        Renderer.renderRectangle(selectionTool.selectionRectangle())
      }
    }*/
    /*key.registerOnRelease {
      if (singleSelectionLock == false) {
        flushList(highlightedSquads, selectedSquads)
        singleSelectionLock = true
      } else {
        selectedSquads.clear()
        if (hoveringSquad != null)
          selectedSquads.add(hoveringSquad);
      }
    }*/

    /*when (select.state()) {
      PRESS -> {
        selectionTool.startFrom(pointer)
      }
      HOLD -> {
        selectionTool.endTo(pointer)
        if (selectionTool.distanceFromStartingPoint() > pointerRadius)
          singleSelectionLock = false
        if (singleSelectionLock == false) {
          val squadsInsideSelection = world.findSquadsInside(selectionTool.selectionRectangle())
          highlightedSquads.clear()
          highlightedSquads.addAll(squadsInsideSelection)
          Renderer.renderRectangle(selectionTool.selectionRectangle())
        }
      }
      RELEASE -> {
        if (singleSelectionLock == false) {
          flushList(highlightedSquads, selectedSquads)
          singleSelectionLock = true
        } else {
          selectedSquads.clear()
          if (hoveringSquad != null)
            selectedSquads.add(hoveringSquad);
        }
      }
      WAIT -> {
      }
    }*/
    /*renderSquadsShips(highlightedSquads, 18f)
    renderSquadsShips(selectedSquads, 16f)
    if (hoveringSquad != null)
      renderSquadsShips(listOf(hoveringSquad), pointerRadius)*/
  }

  /*private fun <T> flushList(from: MutableList<T>, to: MutableList<T>) {
    to.clear()
    to.addAll(from)
    from.clear()
  }

  private fun findPointerHoveringSquad(pointer: Vector2, world: World): Squad? {
    val ship = world.findClosestShip(pointer)
    if (pointer.dst(ship.position) < pointerRadius)
      return world.findSquad(ship)
    return null;
  }

  private fun renderSquadsShips(squads: List<Squad>, radius: Float) {
    for (squad in squads)
      for (ship in squad.ships)
        Renderer.renderCircle(ship.position, radius)
  }*/

}