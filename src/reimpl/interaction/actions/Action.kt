package interaction.actions

interface Action {

  fun bind()

  fun unbind()

  fun conflictsWith(): Set<Action>

  fun isWorking(): Boolean

}