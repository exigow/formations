import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

object Camera {

  private val ortho: OrthographicCamera = OrthographicCamera();
  private val eye = Vector3(0f, 0f, 1f)
  private val eyeTarget = Vector3(eye)

  init {
    ortho.setToOrtho(false, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
  }

  fun projectionMatrix(): Matrix4 = ortho.combined

  fun update(delta: Float) {
    val planeFactor = .125f;
    val zoomFactor = .25f;
    eye.x += (eyeTarget.x - eye.x) * planeFactor * delta;
    eye.y += (eyeTarget.y - eye.y) * planeFactor * delta;
    eye.z += (eyeTarget.z - eye.z) * zoomFactor * delta;
    updateCameraPosition()
    ortho.update()
  }

  fun lookAt(where: Vector2) {
    eyeTarget.x = where.x
    eyeTarget.y = where.y
  }

  fun zoomTo(distance: Float) {
    eyeTarget.z = distance;
  }

  fun zoomRelative(amount: Float) {
    eyeTarget.z += amount
  }

  fun moveRelative(vector: Vector2) {
    eyeTarget.add(vector.x, vector.y, 0f)
  }

  fun unproject(position: Vector2): Vector2 {
    val threeDimensionalPosition = Vector3(position.x, position.y, 0f)
    val result = ortho.unproject(threeDimensionalPosition)
    return Vector2(result.x, result.y)
  }

  private fun updateCameraPosition() {
    ortho.position.set(eye.x, eye.y, 0f)
    ortho.zoom = eye.z
  }

  fun position() = Vector2(eyeTarget.x, eyeTarget.y)

}
