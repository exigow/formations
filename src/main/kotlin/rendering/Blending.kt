package rendering

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20

enum class Blending(private val gdxSFactor: Int, private val gdxDFactor: Int) {

  TRANSPARENCY(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA),
  ADDITIVE(GL20.GL_SRC_ALPHA, GL20.GL_ONE);

  fun decorate(f: () -> Unit) {
    Gdx.gl.glEnable(GL20.GL_BLEND)
    Gdx.gl.glBlendFunc(gdxSFactor, gdxDFactor)
    f.invoke()
    Gdx.gl.glEnable(GL20.GL_BLEND)
  }

}