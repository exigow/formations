package interaction.actions

object CameraArrowsMovementAction : Action {

  override fun bind() {
    //throw UnsupportedOperationException()
  }

  override fun unbind() {
    //throw UnsupportedOperationException()
  }

  override fun conflictsWith() = setOf(CameraMiddleClickMovementAction)

  override fun isWorking(): Boolean {
    throw UnsupportedOperationException()
  }

}