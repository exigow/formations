package interaction

import game.Squad
import game.World
import input.Input
import java.util.*

object NewInteraction {

  val possibleActions = ArrayList<Action>()

  init {
    possibleActions.addAll(EntryAction().possibleNextAction())
  }

  fun propagate(world: World) {
    val availableActions = possibleActions.filter { it.executionCondition(world) }
    println("available actions: $availableActions")
  }

  fun pointerIsHoveringButton(): Boolean {
    return true // todo
  }

  fun pointerIsHoveringSquad(world: World): Boolean {
    return findHoveringSquad(world) != null // todo
  }

  private fun findHoveringSquad(world: World): Squad? {
    val pointer = Input.getMousePositionInWorld()
    val ship = world.findClosestShip(pointer)
    if (pointer.dst(ship.position) < 20f)
      return world.findSquad(ship)
    return null;
  }

  fun hasSelectedSquads(): Boolean {
    return true // todo
  }

  class SelectSingleSquad : Action() {

    override fun executionCondition(world: World) = pointerIsHoveringSquad(world)

    override fun possibleNextAction() = listOf(EntryAction())

    override fun execute() = println("selecting squad")

  }

  class ClickBackground : Action() {

    override fun executionCondition(world: World) = !pointerIsHoveringSquad(world)

    override fun possibleNextAction() = listOf(EntryAction())

    override fun execute() = println("background clicked")

  }

  /* class ClickButton : Action {

     override fun execute() = println("button clicked")

     override fun possibleNextAction() = listOf(EntryAction())

     override fun executionCondition() = pointerIsHoveringButton()

   }
 */

  class EntryAction : Action() {

    override fun executionCondition(world: World) = true

    override fun possibleNextAction() = listOf(
      //EntryAction(),
      //ClickButton(),
      SelectSingleSquad(), ClickBackground()
    )

    override fun execute() {
      println("do nothing")
    }

  }

  abstract class Action {

    abstract fun executionCondition(world: World): Boolean

    abstract fun possibleNextAction(): List<Action>

    abstract fun execute()

    override fun toString(): String {
      val name = javaClass.simpleName
      return "$name"
    }

  }


  /*class MoveSquads : Action {

    override fun execute() = println("move squads")

    override fun executionCondition() = hasSelectedSquads() && !pointerIsHoveringSquad()

    override fun possibleNextAction() = emptyList<Action>()

  }*/

}


