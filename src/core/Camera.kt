package core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import commons.math.FastMath
import commons.math.Vec2

class Camera {

  val ortho: OrthographicCamera = OrthographicCamera();
  private val eye = Vector3(0f, 0f, 1f)
  private val target = Vector3(eye)

  init {
    ortho.setToOrtho(false, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    //ortho.up.set(0f, 1f, 0f);
    //ortho.direction.set(0f, 0f, -1f);
  }

  fun projectionMatrix(): Matrix4 = ortho.combined.cpy()

  fun update(delta: Float) {
    target.z = FastMath.clamp(target.z, .5f, 16f)
    val planeFactor = 16f;
    val zoomFactor = 12f;
    fun limit(f: Float) = Math.min(f * delta, 1f)
    eye.x += (target.x - eye.x) * limit(planeFactor);
    eye.y += (target.y - eye.y) * limit(planeFactor);
    eye.z += (target.z - eye.z) * limit(zoomFactor);
    updateCameraPosition()
    ortho.update()
  }

  fun lookAt(where: Vec2) {
    target.x = where.x
    target.y = where.y
  }

  fun zoomTo(distance: Float) {
    target.z = distance;
  }

  fun zoomRelative(amount: Float) {
    target.z += amount
  }

  fun moveRelative(vector: Vec2) {
    target.add(vector.x, vector.y, 0f)
  }

  fun unproject(position: Vec2): Vec2 {
    val threeDimensionalPosition = Vector3(position.x, position.y, 0f)
    val result = ortho.unproject(threeDimensionalPosition)
    return Vec2(result.x, result.y)
  }

  private fun updateCameraPosition() {
    ortho.position.set(eye.x, eye.y, 0f)
    ortho.zoom = eye.z
  }

  fun position() = Vec2(target.x, target.y)

  fun mouseScreenPosition() = Vec2(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())

  fun mousePosition() = unproject(mouseScreenPosition())

  fun scaledClickRadius() = 16f * renderingScale()

  fun renderingScale() = eye.z

  fun worldVisibilityRectangle(border: Float = 0f): Rectangle {
    val w = ortho.viewportWidth + border
    val h = ortho.viewportHeight + border
    val x = eye.x - (w / 2f * eye.z)
    val y = eye.y - (h / 2f * eye.z)
    return Rectangle(x, y, w * eye.z, h * eye.z);
  }

}
