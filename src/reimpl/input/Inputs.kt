package input

import com.badlogic.gdx.Input


object Inputs {

  val mouseLeft = MouseButton(Input.Buttons.LEFT)
  val mouseRight = MouseButton(Input.Buttons.RIGHT)

  fun updateStates() {
    mouseLeft.update()
    mouseRight.update()
  }

}