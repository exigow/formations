package rendering

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

object Camera {

  private val ortho: OrthographicCamera = OrthographicCamera();
  private val eye = Vector2()
  private val eyeTarget = Vector2()
  private val worldMouse = Vector2()

  init {
    ortho.setToOrtho(false, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
  }

  fun matrix(): Matrix4 = ortho.combined

  fun update(delta: Float) {
    val factor = .015f;
    eye.x += (eyeTarget.x - eye.x) * factor * delta;
    eye.y += (eyeTarget.y - eye.y) * factor * delta;
    updateCameraPosition()
    ortho.update()
    updateWorldMouse()
  }

  fun unprojectedWorldMouse(): Vector2 {
    return worldMouse;
  }

  private fun updateWorldMouse() {
    val mouseWindow = Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
    val result = ortho.unproject(mouseWindow)
    worldMouse.set(result.x, result.y)
  }

  private fun updateCameraPosition() {
    ortho.position.set(eye.x, eye.y, 0f)
  }

}
