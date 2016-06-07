package interaction.actions

import java.util.*

object ActionsRegistrar {

  private val actions = HashSet<Action>()

  fun addAction(action: Action) = actions.add(action)

  fun bindAll() {
    actions.forEach { it.bind() }
  }

}