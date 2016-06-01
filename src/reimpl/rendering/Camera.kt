package rendering

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2

object Camera {

  private val ortho: OrthographicCamera = OrthographicCamera();
  private val eye = Vector2()

  init {
    ortho.setToOrtho(false, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    ortho.position.set(eye.x, eye.y, 0f)
  }

  fun projection(): Matrix4 {
    ortho.update()
    return ortho.combined
  }

}
