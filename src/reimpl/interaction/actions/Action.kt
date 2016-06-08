package interaction.actions

interface Action {

  fun bind()

  fun unbind()

  fun isWorking(): Boolean

}