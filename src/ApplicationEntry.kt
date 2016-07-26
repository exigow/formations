import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.badlogic.gdx.graphics.GL20
import commons.Logger


object ApplicationEntry {

  @JvmStatic fun main(args: Array<String>) {
    Logger.APPLICATION.log("Starting.")
    val config = createConfig()
    Lwjgl3Application(LazyInitialisationAdapter(), config)
  }

  private fun createConfig(): Lwjgl3ApplicationConfiguration {
    val config = Lwjgl3ApplicationConfiguration()
    config.setWindowedMode(1600, 900)
    return config
  }

  private class LazyInitialisationAdapter : ApplicationAdapter() {

    private var frame: Main? = null

    override fun create() {
      Logger.APPLICATION.log("Initialising game.")
      frame = Main()
    }

    override fun render() {
      clearBackground()
      frame!!.onFrame()
    }

    private fun clearBackground() {
      val intensity = .075f
      Gdx.gl.glClearColor(intensity, intensity, intensity, 1f)
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

  }

}