package rendering.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20

object Blender {

  fun enableAdditive(f: () -> Unit) {
    enable()
    additiveFunc()
    f.invoke()
    disable()
  }

  fun enableTransparency(f: () -> Unit) {
    enable()
    transparencyFunc()
    f.invoke()
    disable()
  }

  private fun additiveFunc() = Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE)

  private fun transparencyFunc() = Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)

  private fun enable() = Gdx.gl.glEnable(GL20.GL_BLEND)

  private fun disable() = Gdx.gl.glEnable(GL20.GL_BLEND)

}
