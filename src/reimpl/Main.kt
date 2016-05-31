import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20

class Main {

  fun onRender() {
    clearBackground()


  }

  fun clearBackground() {
    Gdx.gl.glClearColor(.25f, .25f, .25f, 1f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
  }

}