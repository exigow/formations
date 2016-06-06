package interaction

import Camera
import com.badlogic.gdx.math.Vector2
import input.Input
import input.adapters.ButtonAdapter.State.*

class CameraMiddleClickMovementTool {

  private val movementPivot = Vector2()

  fun update() {
    when (Input.Button.MOUSE_MIDDLE.state()) {
      PRESS -> {
        movementPivot.set(Camera.position()).add(Input.getMousePositionInWorld());
        Camera.zoomTo(.925f)
      }
      HOLD -> {
        val difference = Vector2()
        difference.set(movementPivot)
        difference.sub(Input.getMousePositionInWorld())
        Camera.lookAt(difference)
      }
      RELEASE -> {
        Camera.zoomTo(1f)
      }
      WAIT -> {
      }
    }
  }


}
